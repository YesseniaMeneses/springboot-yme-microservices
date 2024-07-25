package com.yme.movementsservice.validation;

import com.yme.movementsservice.constant.ErrorMessages;
import com.yme.movementsservice.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Validations for dates.
 */
public class Date {

    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_RANGE_FORMAT = "yyyyMMdd,yyyyMMdd";
    public static final String REPORT_DATE_FORMAT = "yyyy/MM/dd";

    /**
     * Convert string to date.
     *
     * @param date date in String format.
     * @return date in Date format.
     */
    public static java.util.Date stringToDate(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(date);
        } catch (ParseException ex) {
            throw new BusinessException(ErrorMessages.ERROR_DATE_FORMAT.concat(DATE_FORMAT));
        }
    }

    /**
     * Convert date to string.
     *
     * @param date date in Date format.
     * @return date in String format.
     */
    public static String dateToString(java.util.Date date) {
        return new SimpleDateFormat(REPORT_DATE_FORMAT, Locale.ENGLISH).format(date);
    }

    /**
     * Convert range to an array
     *
     * @param range startDate and endDate in String format.
     * @return array with two elements.
     */
    public static String[] validateRangeDate(String range) {
        if (range.contains(",") && range.length() == 17) {
            String[] dates = range.split(",");
            if (dates.length == 2) {
                return dates;
            }
        }
        throw new BusinessException(ErrorMessages.ERROR_DATE_RANGE_FORMAT.concat(DATE_RANGE_FORMAT));
    }

}
