package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

public class EditBirthdayScreenFactory {
    @AndroidFindBy(id = "android:id/numberpicker_input")
    public List<MobileElement> listOfCurrentData;
    @AndroidFindBy(className = "android.widget.Button")
    public List<MobileElement> listOfPreviousData;
    @AndroidFindBy(id = "android:id/button1")
    public MobileElement okButtonAfterBirthdayChanging;
}
