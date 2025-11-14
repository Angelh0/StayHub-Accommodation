package com.Angelh0.stayhub.exception.SearchException;

import java.time.LocalDate;

public class DateValidate {

    public static void validateCheckIn(LocalDate checkIn, LocalDate checkOut) {
        LocalDate currentDay = LocalDate.now();

        if (checkIn.isBefore(currentDay)) {
            throw new IllegalArgumentException("El check-In debe ser posterior a la fecha actual " + currentDay);
        }

        if (checkOut.isBefore(checkIn)) {
            throw  new IllegalArgumentException("El check-Out debe ser posterior al check-In");
        }
    }
}