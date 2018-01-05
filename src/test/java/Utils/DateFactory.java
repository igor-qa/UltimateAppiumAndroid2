package Utils;

import net.sf.cglib.core.Local;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by borisgurtovyy on 10/5/17.
 */
public class DateFactory {

    public String currentDayPlusXDays(int days){
        LocalDate today = LocalDate.now();
        LocalDate next2Week = today.plus(days, ChronoUnit.DAYS);
        return String.valueOf(next2Week.getDayOfMonth());
    }

    public static String getActualDayOfMonth() {
        return String.valueOf(LocalDate.now().getDayOfMonth());
    }

    public static String getActualDayOfWeek() {
        return String.valueOf(LocalDate.now().getDayOfWeek());
    }

    public static String getActualMonth() {
        return String.valueOf(LocalDate.now().getMonth());
    }

}
