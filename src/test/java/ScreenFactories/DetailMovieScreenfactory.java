package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class DetailMovieScreenfactory {

    @AndroidFindBy(id = "ivShadowInterested")
    public MobileElement shadowInterestedButton;

    @AndroidFindBy(id = "tvTitleFilm")
    public MobileElement movieTitle;

}
