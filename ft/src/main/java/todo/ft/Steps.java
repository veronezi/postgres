package todo.ft;

import cucumber.api.java8.En;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

@Log
public class Steps implements En {

    @SneakyThrows
    private Connection getConnection() {
        return DriverManager.getConnection("jdbc:postgresql://"
                        + ServiceGetter.SERVICES.getDbHost() + ":"
                        + ServiceGetter.SERVICES.getDbPort() + "/todo",
                "todo_user", "todo_pass");
    }

    @SuppressWarnings("unchecked")
    public Steps() {
        Given("the system is live", () -> {
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
                }
            }
        });

        Given("I drop the '(.+)' table", (String string) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE IF EXISTS " + string);
            }
        });

        Given("I execute the '(.+)' update", (String string) -> {
            try (val connection = getConnection()) {
                val statement = connection.createStatement();
                statement.executeUpdate(string);
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


    }
}
