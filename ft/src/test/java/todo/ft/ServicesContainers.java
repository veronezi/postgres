package todo.ft;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.io.FileUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Slf4j
public class ServicesContainers implements ServicesProvider {

    private static final Duration TIMEOUT = Duration.ofSeconds(120);

    private DockerComposeContainer stack;
    private static final File DC_FILE;
    private static final File DC_FILE_RECOVERY;

    static {
        try {
            DC_FILE = File.createTempFile("docker-compose_db_", ".yaml");
            FileUtils.copyInputStreamToFile(ServicesContainers.class.getResourceAsStream("/dc-tests.yaml"), DC_FILE);

            DC_FILE_RECOVERY = File.createTempFile("docker-compose_db_recovery_", ".yaml");
            FileUtils.copyInputStreamToFile(ServicesContainers.class.getResourceAsStream("/dc-tests-recovery.yaml"), DC_FILE_RECOVERY);
        } catch (IOException e) {
            throw new TestException(e);
        }
    }

    private DockerComposeContainer createStack(boolean recovery) {
        var result = new DockerComposeContainer(recovery ? DC_FILE_RECOVERY : DC_FILE)
                .withPull(false)
                .withExposedService(
                        "db_1",
                        5432,
                        Wait.forListeningPort().withStartupTimeout(TIMEOUT)
                )
                .withExposedService(
                        "minio_1",
                        9000,
                        Wait.forListeningPort().withStartupTimeout(TIMEOUT)
                );
        if (Boolean.getBoolean("verbose")) {
            result = result.withTailChildContainers(true);
        }
        return result;
    }

    @Override
    public String getMinioHost() {
        return this.stack.getServiceHost("minio_1", 9000);
    }

    @Override
    public Integer getMinioPort() {
        return this.stack.getServicePort("minio_1", 9000);
    }

    @Override
    public String getDbHost() {
        return this.stack.getServiceHost("db_1", 5432);
    }

    @Override
    public Integer getDbPort() {
        return this.stack.getServicePort("db_1", 5432);
    }

    @Override
    public boolean isOnline() {
        return this.stack != null;
    }

    private void startCommon(boolean recovery) {
        this.stack = createStack(recovery);
        this.stack.start();
    }

    @Override
    public void start() {
        startCommon(false);
    }

    @Override
    public void startRecovery() {
        startCommon(true);
    }

    @Override
    public void stop() {
        if (this.stack != null) {
            this.stack.stop();
            this.stack = null;
        }
    }
}
