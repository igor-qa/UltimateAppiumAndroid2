package PageObjects;

import ScreenFactories.EditGenderScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class EditGenderScreen extends BaseTest{

    private EditGenderScreenFactory editGenderScreenFactory = new EditGenderScreenFactory();

    public EditGenderScreen(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), editGenderScreenFactory);
    }

    public void fromMaleToFemale() {
        editGenderScreenFactory.femaleRadioButton.click();
    }

    public void fromFemaleToMale(){
        editGenderScreenFactory.maleRadioButton.click();
    }

    public ProfileScreen clickOnOkButtonAfterGenderChange() {
        editGenderScreenFactory.genderOkButton.click();
        return new ProfileScreen();
    }

}
