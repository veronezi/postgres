package todo.ft;

import cucumber.api.java8.En;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Log
public class Steps implements En {

    @SneakyThrows
    private Connection getConnection() {
        val conn = DriverManager.getConnection("jdbc:postgresql://"
                        + ServiceGetter.SERVICES.getDbHost() + ":"
                        + ServiceGetter.SERVICES.getDbPort() + "/todo",
                "todo_user", "todo_pass");
        conn.setAutoCommit(false);
        return conn;
    }

    private void retry(Runnable runnable) {
        int i = 60;
        Throwable caught = null;
        while (i-- > 0) {
            try {
                runnable.run();
                caught = null;
                break;
            } catch (Throwable e) {
                caught = e;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    // no-op
                }
            }
        }
        if (caught != null) {
            throw new TestException(caught);
        }
    }

    @SuppressWarnings("unchecked")
    public Steps() {
        Given("the system is live", () -> {
            if (!ServiceGetter.SERVICES.isOnline()) {
                ServiceGetter.SERVICES.start();
            }
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                val resultSet = statement.executeQuery("SELECT 1");
                resultSet.next();
                resultSet.getInt(1);
            }
        });

        Given("I execute the '(.*)' update template (\\d+) times", (String sqlTemplate, Integer times) -> {
            try (val connection = getConnection()) {
                val stat = connection.createStatement();
                for (int i = 0; i < times; i++) {
                    stat.executeUpdate(String.format(sqlTemplate, i));
                    connection.commit();
                }
            }
        });

        Given("I drop the '(.+)' table", (String string) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE IF EXISTS " + string);
                connection.commit();
            }
        });

        Given("I execute the '(.+)' update", (String string) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                statement.executeUpdate(string);
                connection.commit();
            }
        });

        Then("the count query '(.+)' returns (\\d+) rows", (String query, Integer expected) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                val resultSet = statement.executeQuery(query);
                resultSet.next();
                assertThat(resultSet.getInt(1)).isEqualTo(expected);
            }
        });

        Given("I stop the services", ServiceGetter.SERVICES::stop);

        Given("I start the services on recovery mode", ServiceGetter.SERVICES::startRecovery);

        Given("I wait for the first base backup to be ready", () -> retry(() -> {
            val base = Paths.get(System.getProperty("backupDir"), "base", "base.tar").toFile();
            if (!base.exists()) {
                throw new TestException(base.getAbsolutePath() + " not yet created");
            }
        }));

        Given("I execute the '(.+)' query", (String string) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                statement.execute(string);
                connection.commit();
            }
        });
    }
}
