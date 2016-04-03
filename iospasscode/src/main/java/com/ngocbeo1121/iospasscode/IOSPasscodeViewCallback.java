package com.ngocbeo1121.iospasscode;

/**
 * Created by ngocbeo1121 on 3/29/16.
 */
public interface IOSPasscodeViewCallback {

    void onCompleted(IOSPasscodeView passcodeView, boolean isCorrect);
    void onDigit(IOSPasscodeView passcodeView, int digit, boolean appended);
    void onDelete(IOSPasscodeView passcodeView);
    void onCancel(IOSPasscodeView passcodeView);

}
