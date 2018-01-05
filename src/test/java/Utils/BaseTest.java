package Utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
//import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.testng.annotations.Listeners;

import static java.util.regex.Pattern.CASE_INSENSITIVE;


@Listeners({Utils.Listeners.class})
public class BaseTest {
    protected static AppiumDriver driver;

    private final TestSetup setupType;

    protected static Properties prop = new Properties();

    public TestSetup getSetupType() {
        return setupType;
    }

    protected static boolean elementIsNotPresent(By by) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            return driver.findElements(by).isEmpty();
        } finally {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    protected void waitForElementToLoad(MobileElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    protected static boolean waitForAttributeToBeVisible(By by, String attribute, String textToWait) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        return wait.until(ExpectedConditions.attributeToBe(by, attribute, textToWait));
    }

    private void killUiAutomatorServer() throws IOException, InterruptedException {
        setupType.execAdbCommand("uninstall io.appium.uiautomator2.server");
        setupType.execAdbCommand("uninstall io.appium.uiautomator2.server.test");
    }

    public BaseTest() {
        setupType = TestSetupProvider.getInstance().getTestSetup();
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException, InterruptedException {
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/user.properties");
        prop.load(fileInputStream);

        killUiAutomatorServer();

        File apkFile = findFileByMask(System.getProperty("user.dir"), ".*\\.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S7 edge");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, "true");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "us.moviemates");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Activities.SplashActivity");
        capabilities.setCapability("autoGrantPermissions", "true"); //grant permission to system dialogues such as location
        capabilities.setCapability(MobileCapabilityType.APP, apkFile.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 30);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");

        setupType.setUp(capabilities);

        String appiumEndpoint = setupType.getAppiumEndpoint();

        System.out.println(".......Connecting to Appium on " + appiumEndpoint);
        driver = new AndroidDriver(new URL(appiumEndpoint), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println(".......Starting Appium driver");
    }

    private File findFileByMask(String directory, String pattern) {
        File directoryFile = new File(directory);
        Pattern filterPattern = Pattern.compile(pattern, CASE_INSENSITIVE);
        File[] files = directoryFile.listFiles(pathname -> filterPattern.matcher(pathname.getName()).matches());
        if (files == null) {
            throw new SetUpException("Failed to find files at " + directory);
        }
        if (files.length == 0) {
            throw new SetUpException("Didn't found file matching " + pattern + " at " + directory);
        }
        return files[0];
    }

    @AfterSuite
    public void tearDown() throws IOException, InterruptedException {
        System.out.println(".......Stopping Appium driver");
        driver.quit();

        setupType.tearDown();
    }
}
