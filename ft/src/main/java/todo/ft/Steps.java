package todo.ft;

import cucumber.api.java8.En;
import lombok.extern.java.Log;

@Log
public class Steps implements En {

    @SuppressWarnings("unchecked")
    public Steps() {
        Given("the system is live", () -> {

        });

        Given("the database is ready", () -> {

        });

        Given("I execute the template '(.*)' (\\d+) times", (String sqlTemplate, Integer times) -> {

        });

    }
}
