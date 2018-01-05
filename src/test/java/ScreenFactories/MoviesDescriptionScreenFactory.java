package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MoviesDescriptionScreenFactory {

    @AndroidFindBy(id = "tvTitleFilm")
    public MobileElement titleElement;
}