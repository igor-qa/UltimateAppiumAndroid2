package PageObjects;

import ScreenFactories.EditNameScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class EditNameScreen extends BaseTest {


    public EditNameScreenFactory editNameScreenFactory = new EditNameScreenFactory();

    public EditNameScreen() {

        PageFactory.initElements(new AppiumFieldDecorator(driver), editNameScreenFactory);

        waitForElementToLoad(editNameScreenFactory.nameTextField);

        waitForElementToLoad(editNameScreenFactory.okButtonAfterNameChanging);
    }

    public void setNameField(String newName) {
        editNameScreenFactory.nameTextField.clear();
        editNameScreenFactory.nameTextField.sendKeys(newName);
        driver.hideKeyboard();
    }

    public ProfileScreen clickOnOkButtonAfterNameChanging() {
        editNameScreenFactory.okButtonAfterNameChanging.click();
        return new ProfileScreen();
    }
}