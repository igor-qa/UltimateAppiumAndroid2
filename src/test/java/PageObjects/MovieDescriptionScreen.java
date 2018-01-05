package PageObjects;

import ScreenFactories.MoviesDescriptionScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class MovieDescriptionScreen extends BaseTest {

    public MoviesDescriptionScreenFactory moviesDescriptionScreenFactory = new MoviesDescriptionScreenFactory();

    public MovieDescriptionScreen() {

        PageFactory.initElements(new AppiumFieldDecorator(driver), moviesDescriptionScreenFactory);
        waitForElementToLoad(moviesDescriptionScreenFactory.titleElement);
    }

    public String getTitle() {

        return moviesDescriptionScreenFactory.titleElement.getText();
    }
}