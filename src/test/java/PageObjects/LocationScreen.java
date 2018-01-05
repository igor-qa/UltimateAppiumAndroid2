package PageObjects;

import ScreenFactories.LocationScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class LocationScreen extends BaseTest {

    private LocationScreenFactory locationScreenFactory = new LocationScreenFactory();

    public LocationScreen() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), locationScreenFactory);

        waitForElementToLoad(locationScreenFactory.locationTextField);
        waitForElementToLoad(locationScreenFactory.okButton);
    }

    public void setLocationField(String location) {
        locationScreenFactory.locationTextField.click();
        locationScreenFactory.locationTextField.clear();
        locationScreenFactory.locationTextField.sendKeys(location);
        driver.hideKeyboard();
    }

    public ProfileScreen clickOkButton() {
        locationScreenFactory.okButton.click();
        return new ProfileScreen();
    }
}
