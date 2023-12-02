package service;

import jakarta.servlet.http.Cookie;

public class UserIdentifier {

    public static int getUsercode(Cookie[] cookies){
        if (cookies == null) return 0;
        for(Cookie cookie : cookies){
            if (cookie.getName().equals("usercode")){
                return Integer.parseInt(cookie.getValue());
            }
        }
        return 0;
    }

    public static String getRole(Cookie[] cookies){
        if (cookies == null) return "";
        for(Cookie cookie : cookies){
            if (cookie.getName().equals("userrole")){
                return cookie.getValue();
            }
        }
        return "";
    }
}
