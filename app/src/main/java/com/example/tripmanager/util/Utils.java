package com.example.tripmanager.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Utils {
    public static String getDateTime(long time, Context context) {
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return dateFormat.format(date);
    }

    // check validate editText
    public static Boolean checkValidate(List<EditText> editTextList) {
        boolean isReturn = true;
        for (int i = 0; i < editTextList.size(); i++) {
            if (TextUtils.isEmpty(editTextList.get(i).getText())) {
                editTextList.get(i).setError("Không được bỏ trống trường này");
                isReturn = false;
            }
        }
        return isReturn;
    }
}
