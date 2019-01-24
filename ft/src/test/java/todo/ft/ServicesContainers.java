package todo.ft;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Slf4j
public class ServicesContainers implements ServicesProvider {

    private static final Duration TIMEOUT = Duration.ofSeconds(120);

    private final DockerComposeContainer stack;

    public ServicesContainers() {
        if (Boolean.getBoolean("external_services")) {
            this.stack = null;
            return;
        }
        final File dcFile;
        try {
            dcFile = File.createTempFile("docker-compose", ".yaml");
            FileUtils.copyInputStreamToFile(ServicesContainers.class.getResourceAsStream("/dc-tests.yaml"), dcFile);
        } catch (IOException e) {
            throw new TestException(e);
        }
        this.stack = new DockerComposeContainer(dcFile)
                .withPull(false)
                .withExposedService(
                        "db_1",
                        5432,
                        Wait.forListeningPort().withStartupTimeout(TIMEOUT)
                );
        if (Boolean.getBoolean("verbose")) {
            this.stack.withLogConsumer("db_1", new org.testcontainers.containers.output.Slf4jLogConsumer(log));
        }
        this.stack.start();
    }

    @Override
    public String getDbHost() {
        return this.stack.getServiceHost("db_1", 5432);
    }

    @Override
    public Integer getDbPort() {
        return this.stack.getServicePort("db_1", 5432);
    }
}
