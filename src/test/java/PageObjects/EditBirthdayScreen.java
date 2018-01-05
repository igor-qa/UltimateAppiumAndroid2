package PageObjects;

import ScreenFactories.EditBirthdayScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EditBirthdayScreen extends BaseTest{
    public EditBirthdayScreenFactory birthdayScreenFactory = new EditBirthdayScreenFactory();
    public EditBirthdayScreen() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), birthdayScreenFactory);
        waitForElementToLoad(birthdayScreenFactory.listOfCurrentData.get(1));
    }
    public void changeBirthdayData(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(str, formatter);
        String year = String.valueOf(date.getYear());
        String day = String.valueOf(date.getDayOfMonth());
        String month = date.getMonth().toString().substring(0, 1).toUpperCase() + date.getMonth().toString().substring(1, 3).toLowerCase();
        String dataForStorage;
        for (MobileElement currentData : birthdayScreenFactory.listOfCurrentData) {
            for (MobileElement previousData : birthdayScreenFactory.listOfPreviousData) {
                dataForStorage = currentData.getText();
                while (!dataForStorage.equals(month) && !dataForStorage.equals(day) && !dataForStorage.equals(year)) {
                    TouchAction touchAction = new TouchAction(driver);
                    touchAction.press(currentData).waitAction(2800).moveTo(previousData).release().perform();
                    dataForStorage = currentData.getText();
                }
            }
        }
    }
    public ProfileScreen clickOnOkButtonAfterChangeingBirthdayData() {
        birthdayScreenFactory.okButtonAfterBirthdayChanging.click();
        return new ProfileScreen();
    }
}
