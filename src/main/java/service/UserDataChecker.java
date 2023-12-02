package service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataChecker {

    private static final String ADMIN_EMAIL = "admin@gmail.com";

    public static int checkData(HashMap<String, String> map){
        String password = map.get("psw");
        String passwordRepeat = map.get("psw-repeat");
        String email = map.get("email");
        if (!checkEmail(email)) return 1;
        if (!password.equals(passwordRepeat)) return 2;
        if (!checkPasswordStrength(password)) return 3;
        return 0;
    }

    public static boolean checkPasswordStrength(String password){
        if (password == null) return false;
        if (password.length() < 8) return false;
        Pattern numPattern = Pattern.compile(".*[0-9].*");
        Pattern alphaPattern = Pattern.compile(".*[A-Za-zА-Яа-я].*");
        Matcher numMatcher = numPattern.matcher(password);
        Matcher alphaMatcher = alphaPattern.matcher(password);
        return numMatcher.find() && alphaMatcher.find();
    }

    public static boolean checkEmail(String email){
        Pattern emailPattern = Pattern.compile("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }

    public static boolean isAdmin(String email){
        return email.equals(ADMIN_EMAIL);
    }
}