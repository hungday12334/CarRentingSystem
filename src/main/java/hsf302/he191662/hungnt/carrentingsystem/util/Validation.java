package hsf302.he191662.hungnt.carrentingsystem.util;

import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    public static boolean isValidVietnamPhone(String phone) {
        String phoneRegex = "^(0|\\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5|8|9]|9[0-9])\\d{7}$";
        return phone != null && phone.matches(phoneRegex);
    }
}
