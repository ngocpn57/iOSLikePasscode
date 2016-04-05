package com.ngocbeo1121.iospasscode;

/**
 * Created by ngocbeo1121 on 3/29/16.
 */
public interface IOSPasscodeViewCallback {

    /**
     * Called when the passcode is fully entered
     * @param passcodeView The targeted IOSPasscodeView object
     * @return whether the entered passcode is correct
     */
    boolean onCompleted(IOSPasscodeView passcodeView);

    /**
     * Called when a digit button has been pressed
     * @param passcodeView The targeted IOSPasscodeView object
     * @param digit the pressed digit
     * @param appended whether the digit was appended to the passcode or not
     */
    void onDigit(IOSPasscodeView passcodeView, int digit, boolean appended);

    /**
     * Called when the "Delete" button has been pressed and a digit has been deleted from the passcode
     * @param passcodeView The targeted IOSPasscodeView object
     */
    void onDelete(IOSPasscodeView passcodeView);

    /**
     * Called when the "Cancel" button has been pressed
     * @param passcodeView The targeted IOSPasscodeView object
     */
    void onCancel(IOSPasscodeView passcodeView);

}
