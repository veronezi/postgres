package todo.ft;

public interface ServicesProvider {
    String getDbHost();
    Integer getDbPort();
    boolean isOnline();
    void start();
    void stop();
    void startRecovery();
}
