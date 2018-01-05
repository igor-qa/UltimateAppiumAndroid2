package AcceptanceTests;

import PageObjects.*;
import PageObjects.EditGenderScreen;
import PageObjects.MoviesScreen;
import PageObjects.EditNameScreen;
import PageObjects.ProfileScreen;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Networking.ServerManager;


public class UserProfile extends GoogleLogin {

    protected static String oldName, oldGender, oldBirthday, oldLocation;

    @DataProvider(name = "changeValidNames")
    public Object[][] createDataForValidChangeNameTest() {
        return new Object[][]{
                {"Boris"},
                //{"Igor"}
        };
    }

    @DataProvider(name = "oneCharNames")
    public Object[][] createDataForOneCharNameTest() {
        return new Object[][]{
                {"a"},
                {"b"}
        };
    }

    @DataProvider(name = "changeLocations")
    public Object[][] createDataForLocationTest() {
        return new Object[][]{
                {"Sunnyvale, CA"},
                {"Milpitas, CA"}
        };
    }

    @DataProvider(name = "genders")
    public Object[][] createDataForChangeGender() {
        return new Object[][]{
                {"Female", "Male"},
                {"Female", "Male"}
        };
    }

    @DataProvider(name = "characterLimit")
    public Object[][] createDataForCharacterLimitt() {
        return new Object[][]{
                {"TomTomTomTomTomTomTomTomTomTomTomTomTomTomTomTomTom"},
                {"RamRamRamRamRamRamRamRamRamRamRamRamRamRamRamRamRa"}
        };
    }

    @DataProvider(name = "changeValidBirthdays")
    public Object[][] createDataForValidChangeBirthdayTest() {
        return new Object[][]{
                {"December 15, 1975"},
                {"January 17, 1978"}
        };
    }

    @Test(groups = "acceptance", dataProvider = "changeValidNames")
    public void changeName(String[] validNames) {
        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen profileScreen = moviesScreen.clickOnProfileButton();
        oldName = profileScreen.getNameField();

        EditNameScreen editNameScreen = profileScreen.clickOnEditName();
        String newName = validNames[0];
        editNameScreen.setNameField(newName);
        ProfileScreen newProfileScreen = editNameScreen.clickOnOkButtonAfterNameChanging();

        Assert.assertEquals(newProfileScreen.getNameField(), newName);
    }

    @Test(groups = "acceptance", dataProvider = "oneCharNames")
    public void changeNameWithOneChar(String[] oneChar) {

        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen previousProfileScreen = moviesScreen.clickOnProfileButton();
        EditNameScreen editNameScreen = previousProfileScreen.clickOnEditName();

        String newName = oneChar[0];

        editNameScreen.setNameField(newName);
        ProfileScreen newProfileScreen = editNameScreen.clickOnOkButtonAfterNameChanging();

        Assert.assertEquals(previousProfileScreen.getNameField(), newProfileScreen.getNameField());
    }


    @Test(dataProvider = "characterLimit")
    public void changeNameWith51Char (String[]testingCharLimit){
        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen profileScreen2 = moviesScreen.clickOnProfileButton();
        EditNameScreen editNameScreen = profileScreen2.clickOnEditName();

        String newNameCharLimit = testingCharLimit[0];
        String cutStringTo50Char = newNameCharLimit.substring(0, 50);


        //The profile name cannot be longer than 50 characters.
        editNameScreen.setNameField(newNameCharLimit);
        ProfileScreen newProfileScreen = editNameScreen.clickOnOkButtonAfterNameChanging();

        Assert.assertEquals(newProfileScreen.getNameField(), cutStringTo50Char);

    }


    @Test(groups = "acceptance", dataProvider = "changeLocations")
    public void changeLocation (String[]validLocations){
        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen profileScreen = moviesScreen.clickOnProfileButton();
        oldLocation = profileScreen.getLocationField();
        LocationScreen locationScreen = profileScreen.clickOnEditLocation();

        String location = validLocations[0];
        locationScreen.setLocationField(location);
        locationScreen.clickOkButton();
        profileScreen.waitForLocationServerUpdate(location);
        Assert.assertEquals(profileScreen.getLocationField(), location);
    }

    @Test(groups = "acceptance", dataProvider = "genders")
    public void changeGender (String gender1, String gender2){
        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen profileScreen = moviesScreen.clickOnProfileButton();
        oldGender = profileScreen.getGender();
        String gender = profileScreen.getGender();
        EditGenderScreen editGender = profileScreen.clickOnEditGender();

        if (gender.equals(gender1)) {
            editGender.fromFemaleToMale();
            ProfileScreen newProfileScreen = editGender.clickOnOkButtonAfterGenderChange();
            Assert.assertEquals(newProfileScreen.getGender(), gender2);
        } else {
            editGender.fromMaleToFemale();
            ProfileScreen newProfileScreen = editGender.clickOnOkButtonAfterGenderChange();
            Assert.assertEquals(newProfileScreen.getGender(), gender1);
        }
    }

    @Test(groups = "acceptance", dataProvider = "changeValidBirthdays")
    public void changeBirthday(String[] newBirthdayData) throws ParseException {
        String newBirthday = newBirthdayData[0];
        MoviesScreen moviesScreen = new MoviesScreen();
        ProfileScreen previousProfileScreen = moviesScreen.clickOnProfileButton();

        String getBirthday = previousProfileScreen.getBirthdayField();
        DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat inputDateFormat = new SimpleDateFormat("MMMMM dd, yyyy");

        Date parsedDate = inputDateFormat.parse(getBirthday);
        oldBirthday = outputDateFormat.format(parsedDate);

        EditBirthdayScreen birthdayScreen = previousProfileScreen.clickOnEditBirthday();
        birthdayScreen.changeBirthdayData(newBirthday);
        ProfileScreen newProfileScreen = birthdayScreen.clickOnOkButtonAfterChangeingBirthdayData();
        Assert.assertEquals(newProfileScreen.getBirthdayField(), newBirthday);
    }

    @AfterTest(groups = "acceptance")
    public void cleanUpUserProfileTests() throws IOException {
        ServerManager serverManager = new ServerManager();
        serverManager.updateUserProfile(oldName,oldGender,oldBirthday,oldLocation);
    }

}


