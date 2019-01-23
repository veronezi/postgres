package todo.ft;

public class Services implements ServicesProvider {
    @Override
    public String getDbHost() {
        return System.getProperty("db_host", "localhost");
    }

    @Override
    public Integer getDbPort() {
        return Integer.valueOf(System.getProperty("db_port", "5432"));
    }
}
