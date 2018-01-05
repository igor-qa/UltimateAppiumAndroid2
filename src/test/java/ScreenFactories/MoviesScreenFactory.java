package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

/**
 * Created by borisgurtovyy on 10/9/17.
 */
public class MoviesScreenFactory {

    @AndroidFindBy(id = "btnHamburger")
    public MobileElement profileButton;

    @AndroidFindBy(id = "ivPosterFilm")
    public MobileElement moviePoster;

    @AndroidFindBy(id = "tbButtonInterested")
    public List<MobileElement> listOfInterestedButton;

    @AndroidFindBy(id = "rlItemFilm")
    public List<MobileElement> listofmovieButton;

    @AndroidFindBy (id = "tvTitle")
    public MobileElement movieTitle;

    @AndroidFindBy (id = "rlItemFilm")
    public List<MobileElement> allMovies;

    @AndroidFindBy (id = "iv_movie_date_active_select")
    public MobileElement movieDateActiveCheckMark;

    @AndroidFindBy (id = "tbButtonInterested")
    public MobileElement interestedButton;

    @AndroidFindBy(id = "tv_date_picker_day")
    public MobileElement displayedDayOfMonth;

    @AndroidFindBy(id = "tv_date_picker_day_name")
    public MobileElement displayedDayOfWeek;

    @AndroidFindBy(id = "tv_date_picker_month_name")
    public MobileElement displayedMonth;

    @AndroidFindBy(id = "tv_date_picker_day")
    public List<MobileElement> allDaysInsideOneDate;

    @AndroidFindBy(id = "rl_date_picker_item")
    public List<MobileElement> oneDateList;

    @AndroidFindBy(id = "date_list")
    public MobileElement allDatesContainer;
}



