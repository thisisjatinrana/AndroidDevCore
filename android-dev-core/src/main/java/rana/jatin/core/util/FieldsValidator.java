package rana.jatin.core.util;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * CommonUtil class for validating data and input fields
 */

public class FieldsValidator {

    // Regular Expression
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    public static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d][A-Za-z\\d!@#$%^&*()_+]{7,19}$";
    public static final String AT_LEAST_ONE_DIGIT_AND_UPPER_ALPHA_REGEX ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
    public static final String AT_LEAST_ONE_SYMBOL_AND_UPPER_ALPHA_REGEX ="^(?=.*[$@$!%*#?&])(?=.*[a-z])(?=.*[A-Z]).{6,20}$";

    // Error Messages
    public static final String REQUIRED_MSG = "Required";

    public FieldsValidator()
    {
    }
    // call this method when you need to check email validation
    public  boolean isEmailAddress(EditText editText, boolean required, String errorMsg) {
        return isValid(editText, EMAIL_REGEX, errorMsg, required);
    }

    // call this method when you need to check phone number validation
    public  boolean isPhoneNumber(EditText editText, boolean required,String errorMsg) {
        return isValid(editText, PHONE_REGEX, errorMsg, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public  boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
        // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public  boolean hasText(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);

            return false;
        }
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public  boolean hasText(EditText editText,String errMsg) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(errMsg);

            return false;
        }
        return true;
    }

    public  boolean isTextWithInRange(EditText editText, int minLimit, int maxLimit,String error) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        if (text.length() >= minLimit && text.length() <= maxLimit) {
            return true;
        }
        else {
            editText.setError(error);
            return false;
        }
    }

    public  boolean isMatching(EditText editText1, EditText editText2,String errorMsg)
    {
        if(editText1.getText().toString().equalsIgnoreCase(editText2.getText().toString()))
        {
            return true;
        }
        else {
            editText2.setError(errorMsg);
            return false;
        }
    }

    // return true if the input field is valid, based on the parameter passed
    public  boolean haveAtLeastOneDigitAndUpperAlpha(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
        return true;
    }


    // return true if the input field is valid, based on the parameter passed
    public  boolean haveAtLeastOneSymbolAndUpperAlpha(EditText editText, String regex, String errMsg, boolean required) {
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
        return true;
    }


    private synchronized void clearError(final EditText view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setError(null);
            }
        }, 1000);
    }

    public synchronized boolean validateUsernameWithoutSpace(EditText view,
                                                             String message) {
        if (view.getText().toString().toString().contains(" ")) {
            if (message == null || message.isEmpty())
                message = "This input does not fit the requiered pattern.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateUsernameSpace(EditText view, String message) {
        if (view.getText().toString().toString().trim().equalsIgnoreCase("")) {
            if (message == null || message.isEmpty())
                message = "This field should not be empty.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }
}