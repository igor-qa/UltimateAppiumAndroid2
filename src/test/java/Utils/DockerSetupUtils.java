package Utils;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DockerSetupUtils {
    static final String CONTAINER_APK_PATH = "/opt/app-debug.apk";
    static final String VM_NAME = "appium-test-machine";     // VirtualBox VM that hosts docker
    static final String CONTAINER_NAME = "container-appium"; // actual docker Appium container

    private DockerSetupUtils() {

    }

    static void execContainerCommand(String command) throws IOException, InterruptedException {
        execVmCommand("docker exec -t " + CONTAINER_NAME + " " + command);
    }

    static void execVmCommand(String command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("docker-machine", "ssh", VM_NAME, command)
                // .inheritIO() // uncomment to see command output
                .start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Warning: exit code " + exitCode + " (" + command + ")");
        }
    }

    static void copyToContainer(String hostPath, String targetContainerPath) throws IOException, InterruptedException {
        // WARNING: only works for /Users host directory and its subdirectories (only these dirs are shared to VBox VM)
        String command = String.format("docker cp \"%s\" " + CONTAINER_NAME + ":%s", hostPath, targetContainerPath);
        execVmCommand(command);
    }

    static String getContainerIp() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("docker-machine ip " + VM_NAME);
        process.waitFor();

        Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Scanner scanner = new Scanner(process.getInputStream());
        if (!scanner.hasNext(ipPattern)) {
            throw new SetUpException("Failed to get Appium server IP (Docker VM IP)");
        }
        return scanner.next(ipPattern);
    }

}
