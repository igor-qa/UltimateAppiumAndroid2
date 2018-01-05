package AcceptanceTests;

import Networking.ServerManager;
import PageObjects.DetailMovieScreen;
import PageObjects.MovieDescriptionScreen;
import PageObjects.MoviesScreen;
import Utils.BaseTest;
import Utils.DateFactory;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * Created by borisgurtovyy on 11/2/17.
 */
public class Movies extends GoogleLogin {

    @DataProvider(name = "changeMovieIndex")
    public Object [][] createDataForMovieIndexing() {
        return new Object[][] {
                {1},
                {3}
        };
    }

    @DataProvider(name = "clickingInterestedButton")
    public Object[][] clickOnInterestedButton() {
        return new Object[][]{
                {1},
                {2}
        };
    }

    @AfterTest(groups ="interestedMovies")
    public void cleanInterestedMovies() throws InterruptedException, ParseException, IOException {
        ServerManager serverManager = new ServerManager();
        serverManager.cleanMovies();
    }

    @Test(groups = "acceptance")
    public void swipeCalendarForXDays() throws InterruptedException {
        MoviesScreen moviesScreen = new MoviesScreen();
        int days = 13; // add data provider for this parameter with following values -- 5, 10, and separate test for 14
        String expectedLastDate = new DateFactory().currentDayPlusXDays(days);
        long startTime = System.currentTimeMillis();
        int timeout = 40; // in seconds
        // Scroll until element found or timeout reached
        while (!moviesScreen.getLastCalendarDay().equals(expectedLastDate) && (System.currentTimeMillis() - startTime) / 1000 < timeout) {
            moviesScreen.swipeFromFirstToLastDayInCal(expectedLastDate);
        }
        String lastCalDay = moviesScreen.getLastCalendarDay();
        Assert.assertTrue(lastCalDay.equals(expectedLastDate));
    }


    @Test(groups = "acceptance", dataProvider = "changeMovieIndex")
    public void markMovieInterested(int[] movieIndexes) {
        MoviesScreen moviesScreen = new MoviesScreen();
        int movieIndex = movieIndexes[0];
        moviesScreen.clickInterested(movieIndex);
        Assert.assertTrue(moviesScreen.isCheckMarkDisplayed());

    }

    @Test(groups ={"acceptance", "interestedMovies"}, dataProvider = "clickingInterestedButton")
    public void activateColorOfInterestedButton(int[] interestedButton) {

        MoviesScreen moviesScreen = new MoviesScreen();

        int interestedInMovie = interestedButton[0];

        moviesScreen.clickOnInterestedButton(interestedInMovie);


        DetailMovieScreen detailMovieScreen = moviesScreen.clickOnMovieButton(interestedInMovie);

        Assert.assertTrue(detailMovieScreen.interestedButtonOrange());

    }

    @Test(groups = "acceptance")
    public void userLandedOnMoviesScreenAfterSignIn() {
        new MoviesScreen();
        Assert.assertTrue(MoviesScreen.getListOfMainNavTabs().get(0).isSelected());
    }

    @Test(groups = "acceptance")
    public void highlightedDateMatchesActualDate() {
        new MoviesScreen();
        Assert.assertTrue(DateFactory.getActualDayOfMonth().equalsIgnoreCase(MoviesScreen.getDisplayedDayOfMonth()));
        Assert.assertTrue(DateFactory.getActualDayOfWeek().equalsIgnoreCase(MoviesScreen.getDisplayedDayOfWeek()));
        Assert.assertTrue(DateFactory.getActualMonth().contains(MoviesScreen.getDisplayedMonth()));
    }

    @Test(groups = "acceptance")
    public void chooseRandomMovie(){
        MoviesScreen moviesScreen = new MoviesScreen();

        int randomNumber = 2;

        String currentName1 = moviesScreen.getCurrentMovieName(randomNumber).toUpperCase();
        if (currentName1.length() > 27 ) {
            currentName1 = currentName1.substring(0, 26);
        }

        MovieDescriptionScreen movieDescriptionScreen = moviesScreen.chooseRandomMovie(randomNumber);

        String currentName2 = movieDescriptionScreen.getTitle().toUpperCase();
        if (currentName2.length() > 27 ) {
            currentName2 = currentName2.substring(0, 26);
        }

        System.out.println("Title from movie lists: " + currentName1);
        System.out.println("Title from movie lists: " + currentName2);

        Assert.assertEquals(currentName1, currentName2);
    }
}
