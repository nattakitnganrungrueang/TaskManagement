package com.taskManagement.utils;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static Date calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime expiryDateTime = LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
        return Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
