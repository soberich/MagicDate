package soberich.magicdate7.model.repo.local;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * Created by soberich on 10/10/17.
 */

public final class DateConverter {

    private static final String DATE_PATTERN_FORMAT = "d MMM yyyy";

    private DateConverter() {}

    @TypeConverter
    public static LocalDate toLocalDate(String str) {
        return LocalDate.parse(str, DateTimeFormat.forPattern(DATE_PATTERN_FORMAT));
    }

    @TypeConverter
    public static String toString(LocalDate localDate) {
        return localDate.toString(DATE_PATTERN_FORMAT);
    }
}
