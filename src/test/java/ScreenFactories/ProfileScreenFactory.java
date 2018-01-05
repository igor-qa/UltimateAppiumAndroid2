package ScreenFactories;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class ProfileScreenFactory {

    @AndroidFindBy(id = "tvNameValue")
    public MobileElement nameFieldElement;

    @AndroidFindBy(id = "ivName")
    public MobileElement nameEditElement;

    @AndroidFindBy(id = "tvLocationValue")
    public MobileElement locationEditElement;

    @AndroidFindBy(id = "tvGenderValue")
    public MobileElement genderFieldElement;

    @AndroidFindBy(id = "ivGender")
    public MobileElement genderEditElement;

    @AndroidFindBy(id = "tvBirthValue")
    public MobileElement birthdayFieldElement;

    @AndroidFindBy(id = "ivBirth")
    public MobileElement birthdayEditElement;


}
