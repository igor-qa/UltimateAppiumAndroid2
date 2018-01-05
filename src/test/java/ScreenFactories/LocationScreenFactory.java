package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LocationScreenFactory {

    @AndroidFindBy(id = "etEnterPosition")
    public MobileElement locationTextField;

    @AndroidFindBy(id = "android:id/button1")
    public MobileElement okButton;
}
