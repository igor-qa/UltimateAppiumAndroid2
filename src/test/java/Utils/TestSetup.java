package Utils;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

import static Utils.DockerSetupUtils.*;

public enum TestSetup {
    LOCAL_SETUP {
        @Override
        void execAdbCommand(String command) throws IOException, InterruptedException {
            Process process = Runtime.getRuntime().exec("adb " + command);
            process.waitFor();
        }

        @Override
        void setUp(DesiredCapabilities capabilities) {
            try {
                AppiumServer.startAppiumServer();
            } catch (IOException | InterruptedException e) {
                throw new SetUpException("Failed to initialize the Appium server", e);
            }
        }

        @Override
        String getAppiumEndpoint() {
            return "http://127.0.0.1:4723/wd/hub";
        }

        @Override
        void tearDown() {
            try {
                AppiumServer.stopAppiumServer();
            } catch (InterruptedException | IOException e) {
                throw new SetUpException("Failed to shutdown the Appium server", e);
            }
        }

        @Override
        String getAdbLogs() {
            return "adb logcat -d MainActivity:V";
        }
    },
    DOCKER_SETUP {
        @Override
        void execAdbCommand(String command) throws IOException, InterruptedException {
            execContainerCommand("adb " + command);
        }

        @Override
        void setUp(DesiredCapabilities capabilities) {
            Object packagePath = capabilities.getCapability(MobileCapabilityType.APP);
            if (packagePath != null) {
                String normalizedPath = packagePath.toString();

                try {
                    copyToContainer(normalizedPath, DockerSetupUtils.CONTAINER_APK_PATH);
                    capabilities.setCapability(MobileCapabilityType.APP, DockerSetupUtils.CONTAINER_APK_PATH);
                } catch (IOException | InterruptedException e) {
                    throw new SetUpException("Failed to copy APK to the Appium container", e);
                }
            }
        }

        @Override
        String getAppiumEndpoint() {
            try {
                return String.format("http://%s:4723/wd/hub", getContainerIp());
            } catch (IOException | InterruptedException e) {
                throw new SetUpException("Failed to determine Appium endpoint on the container", e);
            }
        }

        @Override
        void tearDown() {
            // no-op
        }

        @Override
        String getAdbLogs() {
            return "docker-machine ssh " + DockerSetupUtils.VM_NAME + " docker exec -t " + DockerSetupUtils.CONTAINER_NAME + " adb logcat -d MainActivity:V";
        }


    };

    /**
     * Execute ADB command on all connected nodes
     *
     * @param command command to execute
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the current thread is interrupted} by another
     *         thread while it is waiting for command to finish
     */
    abstract void execAdbCommand(String command) throws IOException, InterruptedException;

    /**
     * Called before creating a driver session
     * @param capabilities capabilities for a test session
     */
    abstract void setUp(DesiredCapabilities capabilities);

    /**
     * @return Appium endpoint to use (IP/wd/hub)
     */
    abstract String getAppiumEndpoint();

    /**
     * Called after a test is finished and a driver session is shut down
     */
    abstract void tearDown();


    /**
        called to get command for gathering adb logs
     */
    abstract String getAdbLogs();

    static TestSetup getFromSystemProperty() {
        String testSetup = System.getProperty("TestSetup", "LOCAL_SETUP");
        try {
            return TestSetup.valueOf(testSetup);
        } catch (IllegalArgumentException e) {
            throw new SetUpException("Unknown TestSetup value: " + testSetup);
        }
    }
}
