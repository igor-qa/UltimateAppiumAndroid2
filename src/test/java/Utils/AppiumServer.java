package Utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;


class AppiumServer {
    private static AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();

    static void startAppiumServer() throws IOException, InterruptedException {
        service.start();
    }

    static void stopAppiumServer() throws InterruptedException, IOException {
        service.stop();
    }

}
