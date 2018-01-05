package PageObjects;

import ScreenFactories.ProfileScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class ProfileScreen extends BaseTest {

    private static ProfileScreenFactory profileScreenFactory = new ProfileScreenFactory();

    public ProfileScreen() {

        PageFactory.initElements(new AppiumFieldDecorator(driver), profileScreenFactory);

        waitForElementToLoad(profileScreenFactory.nameFieldElement);
        waitForElementToLoad(profileScreenFactory.nameEditElement);
        waitForElementToLoad(profileScreenFactory.locationEditElement);
    }

    public String getNameField() {
        return profileScreenFactory.nameFieldElement.getText();
    }

    public EditNameScreen clickOnEditName() {
        profileScreenFactory.nameEditElement.click();
        return new EditNameScreen();
    }

    public LocationScreen clickOnEditLocation() {
        profileScreenFactory.locationEditElement.click();
        return new LocationScreen();
    }

    public void waitForLocationServerUpdate(String location) {
        waitForAttributeToBeVisible(By.id("tvLocationValue"), "text", location);
    }

    public String getLocationField() {
        return profileScreenFactory.locationEditElement.getText();
    }

    public EditGenderScreen clickOnEditGender() {
        profileScreenFactory.genderEditElement.click();
        return new EditGenderScreen();
    }

    public String getGender() {
        return profileScreenFactory.genderFieldElement.getText();
    }

    public EditBirthdayScreen clickOnEditBirthday() {
        profileScreenFactory.birthdayEditElement.click();
        return new EditBirthdayScreen();
    }

    public String getBirthdayField(){
        return profileScreenFactory.birthdayFieldElement.getText();
    }


}
