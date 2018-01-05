package PageObjects;

import ScreenFactories.DetailMovieScreenfactory;
import ScreenFactories.MoviesScreenFactory;
import Utils.BaseTest;
import io.appium.java_client.TouchAction;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import java.util.concurrent.TimeUnit;

public class DetailMovieScreen extends BaseTest {

    private DetailMovieScreenfactory detailMovieScreenfactory = new DetailMovieScreenfactory();

    public DetailMovieScreen() {

        PageFactory.initElements(new AppiumFieldDecorator(driver), detailMovieScreenfactory);

        waitForElementToLoad(detailMovieScreenfactory.movieTitle);

    }
    public boolean interestedButtonOrange(){
        return detailMovieScreenfactory.shadowInterestedButton.isDisplayed();
    }
}
