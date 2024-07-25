package com.yme.movementsservice.validation;

import com.yme.movementsservice.constant.ErrorMessages;
import com.yme.movementsservice.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

class DateTest {

    @Test
    void stringToDate() {
        java.util.Date date = Date.stringToDate("20240101");
        Assertions.assertNotNull(date);
    }

    @Test
    void dateToString() {
        String strDate = Date.dateToString(new java.util.Date());
        Assertions.assertNotNull(strDate);
    }

    @Test
    void givenIncorrectDateWhenStringToDateShouldThrowErrorERROR_DATE_FORMAT() {
        BusinessException exception = assertThrows(BusinessException.class, () ->
                Date.stringToDate("2024/01/01"));
        Assertions.assertEquals(ErrorMessages.ERROR_DATE_FORMAT.concat(Date.DATE_FORMAT), exception.getMessage());
    }

    @Test
    void validateRangeDate() {
        String[] range = Date.validateRangeDate("20240101,20241231");
        Assertions.assertEquals("20240101", range[0]);
        Assertions.assertEquals("20241231", range[1]);
    }

    @Test
    void givenIncorrectDateRangeWhenvalidateRangeDateShouldThrowErrorERROR_DATE_RANGE_FORMAT() {
        BusinessException exception = assertThrows(BusinessException.class, () ->
                Date.validateRangeDate("20240101-20241231"));
        Assertions.assertEquals(ErrorMessages.ERROR_DATE_RANGE_FORMAT.concat("yyyyMMdd,yyyyMMdd"), exception.getMessage());

        exception = assertThrows(BusinessException.class, () ->
                Date.validateRangeDate("20240101"));
        Assertions.assertEquals(ErrorMessages.ERROR_DATE_RANGE_FORMAT.concat("yyyyMMdd,yyyyMMdd"), exception.getMessage());

        exception = assertThrows(BusinessException.class, () ->
                Date.validateRangeDate("20240101|20241231,20241231"));
        Assertions.assertEquals(ErrorMessages.ERROR_DATE_RANGE_FORMAT.concat("yyyyMMdd,yyyyMMdd"), exception.getMessage());
    }
}
