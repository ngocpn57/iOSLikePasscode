package com.ngocbeo1121.ioslikepasscode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ngocbeo1121.iospasscode.IOSPasscodeView;
import com.ngocbeo1121.iospasscode.IOSPasscodeViewCallback;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by ngocbeo1121 on 3/28/16.
 */
public class MainActivity extends AppCompatActivity implements IOSPasscodeViewCallback {

    public static final String TAG = "MainActivity";

    @Bind(R.id.passcodeView)
    IOSPasscodeView passcodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        passcodeView.setCallback(this);
    }

    @Override
    public void onCompleted(IOSPasscodeView passcodeView, boolean isCorrect) {
        Toast.makeText(this, "passcode: " + passcodeView.getPasscode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDigit(IOSPasscodeView passcodeView, int digit, boolean appended) {
        Log.d(TAG, "digit " + digit);
    }

    @Override
    public void onDelete(IOSPasscodeView passcodeView) {
        Log.d(TAG, "delete");
    }

    @Override
    public void onCancel(IOSPasscodeView passcodeView) {
        Log.d(TAG, "cancel");
    }
}
