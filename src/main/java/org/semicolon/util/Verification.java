package org.semicolon.util;

public class Verification {
    public static boolean verify(String password) {
        return password.matches("[A-Z][a-zA-Z]{7,}[0-9]{2,4}[@!#$%^&*()_-|+?-}{]");
    }

}
