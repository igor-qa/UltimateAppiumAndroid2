package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class EditGenderScreenFactory {

    @AndroidFindBy(id = "rbMale")
    public MobileElement maleRadioButton;

    @AndroidFindBy(id = "rbFemale")
    public  MobileElement femaleRadioButton;

    @AndroidFindBy(id = "android:id/button1")
    public MobileElement genderOkButton;
}
