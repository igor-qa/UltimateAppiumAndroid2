package PageObjects;

import ScreenFactories.MoviesScreenFactory;
import Utils.BaseTest;
import Utils.Scroll;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by borisgurtovyy on 10/2/17.
 */
import java.util.List;



public class MoviesScreen extends BaseTest {

    public static MoviesScreenFactory moviesScreenFactory = new MoviesScreenFactory();

    public MoviesScreen() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), moviesScreenFactory);
        waitForElementToLoad(moviesScreenFactory.profileButton);
        waitForElementToLoad(moviesScreenFactory.moviePoster);
        waitForElementToLoad(moviesScreenFactory.movieTitle);
    }

    public ProfileScreen clickOnProfileButton() {
        moviesScreenFactory.profileButton.click();
        return new ProfileScreen();
    }

    public void clickOnInterestedButton(int i){
        moviesScreenFactory.listOfInterestedButton.get(i).click();

    }

    public  DetailMovieScreen clickOnMovieButton(int i){
        moviesScreenFactory.listofmovieButton.get(i).click();
        return new DetailMovieScreen();

    }
    public void clickInterested(int buttonIndex) {
        List<MobileElement> interestedButtonsList = driver.findElementsById("tbButtonInterested");
        interestedButtonsList.get(buttonIndex).click();
    }

    public String getMovieTitle(int movieIndex) {
        List<MobileElement> movieTitlesList = driver.findElementsById("tvTitle");
        return movieTitlesList.get(movieIndex + 3).getText();
    }

    public String getCurrentMovieName(int r) {
        List<MobileElement> listOfMovies = moviesScreenFactory.allMovies;
        MobileElement currentMovie = (MobileElement) listOfMovies.get(r);
        String name = currentMovie.findElement(By.id("tvTitle")).getText();
        return name;
    }
    public MovieDescriptionScreen chooseRandomMovie(int r){
        List<MobileElement> listOfMovies = moviesScreenFactory.allMovies;
        MobileElement currentMovie = listOfMovies.get(r);

        currentMovie.click();

        return new MovieDescriptionScreen();
    }

    public boolean isCheckMarkDisplayed() {
        return driver.findElementById("iv_movie_date_active_select").isDisplayed();
    }

    public static List<MobileElement> getListOfMainNavTabs() {
        return (List<MobileElement>) driver.findElementsByClassName("android.support.v7.app.ActionBar$Tab");
    }

    public static String getDisplayedDayOfMonth() {
        return moviesScreenFactory.displayedDayOfMonth.getText();
    }

    public static String getDisplayedDayOfWeek() {
        return moviesScreenFactory.displayedDayOfWeek.getText();
    }

    public static String getDisplayedMonth() {
        return moviesScreenFactory.displayedMonth.getText();
    }

    public String getLastCalendarDay(){
        return moviesScreenFactory.allDaysInsideOneDate.get(moviesScreenFactory.allDaysInsideOneDate.size() - 1).getText();
    }

    public boolean swipeFromFirstToLastDayInCal(String expectedDay) throws InterruptedException {
        MobileElement container = moviesScreenFactory.allDatesContainer;
        boolean foundExpectedDay = false;
        MobileElement myCalendarDay = null;
        for(int i = 0; i<moviesScreenFactory.allDaysInsideOneDate.size() - 1; i++){
            myCalendarDay = moviesScreenFactory.oneDateList.get(i);
            if(myCalendarDay.getText().equals(expectedDay)) {
                foundExpectedDay = true;
                break;
            }
        }
        if(!foundExpectedDay) {
            myCalendarDay = moviesScreenFactory.oneDateList.get(moviesScreenFactory.oneDateList.size() - 1);
        }
        Scroll scroll = new Scroll();
        return scroll.scrollToElement(container, myCalendarDay, SwipeElementDirection.LEFT, 15);
    }
}


