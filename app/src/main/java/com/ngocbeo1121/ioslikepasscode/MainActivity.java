package com.ngocbeo1121.ioslikepasscode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ngocbeo1121.iospasscode.IOSPasscodeView;
import com.ngocbeo1121.iospasscode.IOSPasscodeViewCallback;


/**
 * Created by ngocbeo1121 on 3/28/16.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    IOSPasscodeView passcodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passcodeView = (IOSPasscodeView) findViewById(R.id.passcodeView);

        passcodeView.setCallback(new IOSPasscodeViewCallback() {
            @Override
            public boolean onCompleted(final IOSPasscodeView passcodeView) {
                boolean isCorrect = passcodeView.getPasscode().equals("1234");
                Toast.makeText(MainActivity.this, "passcode: " + passcodeView.getPasscode() + ", isCorrect = " + isCorrect, Toast.LENGTH_SHORT).show();
                return isCorrect;
            }

            @Override
            public void onDigit(IOSPasscodeView passcodeView, int digit, boolean appended) {

            }

            @Override
            public void onDelete(IOSPasscodeView passcodeView) {

            }

            @Override
            public void onCancel(IOSPasscodeView passcodeView) {

            }
        });
    }

}
