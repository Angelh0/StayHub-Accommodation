package com.Angelh0.stayhub.exception.SearchException;

import java.time.LocalDate;

public class DateValidate {

    public static void validateCheckIn(LocalDate checkIn, LocalDate checkOut) {
        LocalDate currentDay = LocalDate.now();

        if (checkIn.isBefore(currentDay)) {
            throw new DateValid("El check-In debe ser posterior a la fecha actual " + currentDay);
        }

        if (checkOut.isBefore(checkIn)) {
            throw  new DateValid("El check-Out debe ser posterior al check-In");
        }

        if (checkIn.isAfter(currentDay.plusYears(1))) {
            throw new DateValid("El checkin no puede ser superior a 1 año desde la fecha actual");
        }

        if (checkOut.isAfter(currentDay.plusYears(1).plusDays(1))) {
            throw new DateValid("El checkOut no puede ser superior a 1 año y 1 dia desde la fecha actual");
        }
    }
}