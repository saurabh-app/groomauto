package com.example.hp.groomauto.util;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 4/9/2018.
 */

public class Validation {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isvalidNumber(CharSequence target){
        String PATERN = "^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{1}\\s[0-9]{4}$";
        Pattern pattern = Pattern.compile(PATERN);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }
    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
