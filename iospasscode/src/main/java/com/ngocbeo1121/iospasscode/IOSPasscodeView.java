package com.ngocbeo1121.iospasscode;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ngocbeo1121 on 3/28/16.
 */
public class IOSPasscodeView extends LinearLayout {

    public static final String TAG = "IOSPasscodeView";

    public static final int DEFAULT_PASSCODE_LENGTH = 4;
    public static final String DEFAULT_PASSCODE_TEXT = "Enter passcode";
    public static final String DEFAULT_CANCEL_TEXT = "Cancel";
    public static final String DEFAULT_DELETE_TEXT = "Delete";
    public static final boolean DEFAULT_VIBRATE_ENABLED = false;
    public static final int DEFAULT_VIBRATE_DURATION = 500;

    public static final int[] PASSCODE_BUTTON_STATE_NORMAL_RESOURCES = {
            R.drawable.btn_passcode_normal_0,
            R.drawable.btn_passcode_normal_1,
            R.drawable.btn_passcode_normal_2,
            R.drawable.btn_passcode_normal_3,
            R.drawable.btn_passcode_normal_4,
            R.drawable.btn_passcode_normal_5,
            R.drawable.btn_passcode_normal_6,
            R.drawable.btn_passcode_normal_7,
            R.drawable.btn_passcode_normal_8,
            R.drawable.btn_passcode_normal_9
    };

    /*public static final int[] PASSCODE_BUTTON_STATE_PRESSED_RESOURCES = {
            R.drawable.btn_passcode_pressed_0,
            R.drawable.btn_passcode_pressed_1,
            R.drawable.btn_passcode_pressed_2,
            R.drawable.btn_passcode_pressed_3,
            R.drawable.btn_passcode_pressed_4,
            R.drawable.btn_passcode_pressed_5,
            R.drawable.btn_passcode_pressed_6,
            R.drawable.btn_passcode_pressed_7,
            R.drawable.btn_passcode_pressed_8,
            R.drawable.btn_passcode_pressed_9
    };*/

    TextView enterPasscodeTextView;
    LinearLayout passcodeLayout;
    ImageView passcode0, passcode1, passcode2, passcode3;
    ImageView passcodeButton0, passcodeButton1, passcodeButton2, passcodeButton3, passcodeButton4, passcodeButton5, passcodeButton6, passcodeButton7, passcodeButton8, passcodeButton9;
    TextView deleteTextView;

    ImageView[] passcodeImages;
    ImageView[] passcodeButtonImageViews;
    TransitionDrawable[] passcodeButtonDrawables;

    int passcodeLength;
    String enterPasscodeText;
    String deleteText;
    String cancelText;
    boolean vibrateOnIncorrect;
    int vibrateDuration;

    boolean isPasscodeEntered = false;
    StringBuilder passcodeBuilder;

    // Callback
    IOSPasscodeViewCallback callback;


    public IOSPasscodeView(Context context) {
        super(context);
        initViews();
    }

    public IOSPasscodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IOSPasscodeView,
                0, 0);

        try {
            passcodeLength = a.getInt(R.styleable.IOSPasscodeView_passcodeLength, DEFAULT_PASSCODE_LENGTH);
            enterPasscodeText = a.getString(R.styleable.IOSPasscodeView_enterPasscodeText);
            deleteText = a.getString(R.styleable.IOSPasscodeView_deleteText);
            cancelText = a.getString(R.styleable.IOSPasscodeView_cancelText);
            vibrateOnIncorrect = a.getBoolean(R.styleable.IOSPasscodeView_vibrateOnIncorrect, DEFAULT_VIBRATE_ENABLED);
            vibrateDuration = a.getInt(R.styleable.IOSPasscodeView_vibrateDuration, DEFAULT_VIBRATE_DURATION);
        } finally {
            a.recycle();
        }

        if (enterPasscodeText == null){
            enterPasscodeText = DEFAULT_PASSCODE_TEXT;
        }
        if (deleteText == null){
            deleteText = DEFAULT_DELETE_TEXT;
        }
        if (cancelText == null){
            cancelText = DEFAULT_CANCEL_TEXT;
        }


        initViews();
    }


    void initViews(){
        setOrientation(VERTICAL);

        inflate(getContext(), R.layout.layout_passcode, this);

        enterPasscodeTextView = (TextView) findViewById(R.id.text_enter_passcode);

        passcodeLayout = (LinearLayout) findViewById(R.id.layout_passcode);

        passcode0 = (ImageView) passcodeLayout.findViewById(R.id.img_passcode_0);
        passcode1 = (ImageView) passcodeLayout.findViewById(R.id.img_passcode_1);
        passcode2 = (ImageView) passcodeLayout.findViewById(R.id.img_passcode_2);
        passcode3 = (ImageView) passcodeLayout.findViewById(R.id.img_passcode_3);

        passcodeButton0 = (ImageView) findViewById(R.id.button_passcode_0);
        passcodeButton1 = (ImageView) findViewById(R.id.button_passcode_1);
        passcodeButton2 = (ImageView) findViewById(R.id.button_passcode_2);
        passcodeButton3 = (ImageView) findViewById(R.id.button_passcode_3);
        passcodeButton4 = (ImageView) findViewById(R.id.button_passcode_4);
        passcodeButton5 = (ImageView) findViewById(R.id.button_passcode_5);
        passcodeButton6 = (ImageView) findViewById(R.id.button_passcode_6);
        passcodeButton7 = (ImageView) findViewById(R.id.button_passcode_7);
        passcodeButton8 = (ImageView) findViewById(R.id.button_passcode_8);
        passcodeButton9 = (ImageView) findViewById(R.id.button_passcode_9);

        deleteTextView = (TextView) findViewById(R.id.text_delete);

        initialSetup();
    }


    void initialSetup(){
        passcodeImages = new ImageView[]{ passcode0, passcode1, passcode2, passcode3 };
        passcodeButtonImageViews = new ImageView[]{ passcodeButton0, passcodeButton1, passcodeButton2, passcodeButton3, passcodeButton4, passcodeButton5, passcodeButton6, passcodeButton7, passcodeButton8, passcodeButton9 };

        passcodeButtonDrawables = new TransitionDrawable[10];
        final Drawable pressedDrawable = getResources().getDrawable(R.drawable.btn_passcode_pressed);
        for (int i = 0; i < 10; i++){
            TransitionDrawable drawable = new TransitionDrawable(new Drawable[]{
                    getResources().getDrawable(PASSCODE_BUTTON_STATE_NORMAL_RESOURCES[i]),
                    pressedDrawable
            });

            passcodeButtonDrawables[i] = drawable;
        }


        // Enter passcode text
        enterPasscodeTextView.setText(enterPasscodeText);
        deleteTextView.setText(cancelText);

        // Passcode buttons handle touch
        for (int i = 0; i < 10; i++){
            final int digit = i;
            ImageView passcodeButton = passcodeButtonImageViews[i];

            final TransitionDrawable drawable = passcodeButtonDrawables[i];
            passcodeButton.setImageDrawable(drawable);

            passcodeButton.setOnTouchListener(new View.OnTouchListener() {

                boolean isPressed = false;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {

                        case MotionEvent.ACTION_DOWN:
                            //v.setPressed(true);
                            isPressed = true;
                            appendPasscodeDigit(digit);
                            drawable.startTransition(0);
                            // Start action ...
                            break;
                        case MotionEvent.ACTION_UP:
                            onPasscodeButtonTouchUp(v);
                        case MotionEvent.ACTION_OUTSIDE:
                        case MotionEvent.ACTION_CANCEL:
                            //v.setPressed(false);
                            isPressed = false;
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isPressed){
                                        drawable.reverseTransition(250);
                                    }
                                    else {
                                        Log.d(TAG, "hehe");
                                    }
                                }
                            }, 50);
                            // Stop action ...
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                    }

                    return true;
                }
            });
        }

        // Delete / cancel text handle click
        deleteTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePasscodeChar();
            }
        });

        // Passcode content
        passcodeBuilder = new StringBuilder(4);
    }


    void onPasscodeButtonTouchUp(View v){
        if (passcodeBuilder.length() == DEFAULT_PASSCODE_LENGTH){
            if (!isPasscodeEntered){
                onPasscodeEntered();
                isPasscodeEntered = true;
            }
        }
        else {
            isPasscodeEntered = false;
        }
    }


    void appendPasscodeDigit(int digit){
        boolean appended = false;
        if (passcodeBuilder.length() < DEFAULT_PASSCODE_LENGTH) {
            passcodeBuilder.append(digit);
            updateUI();
            appended = true;
        }

        if (callback != null){
            callback.onDigit(this, digit, appended);
        }
    }


    void deletePasscodeChar(){
        if (passcodeBuilder.length() > 0){
            passcodeBuilder.deleteCharAt(passcodeBuilder.length() - 1);
            updateUI();

            if (callback != null){
                callback.onDelete(this);
            }
        }
        else {
            if (callback != null){
                callback.onCancel(this);
            }
        }
        isPasscodeEntered = false;
    }


    void onPasscodeEntered(){
        if (callback != null){
            if (!callback.onCompleted(this)){
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearAndShakePasscodeLayout();
                    }
                }, 150);
            }
        }
    }


    public void clearAndShakePasscodeLayout(){
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.incorrect_shake);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setTarget(passcodeLayout);
        animatorSet.start();

        if (vibrateOnIncorrect) {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(vibrateDuration);
        }

        postDelayed(new Runnable() {
            @Override
            public void run() {
                setPasscode(null);
            }
        }, 280);

    }


    void updateUI(){
        int length = passcodeBuilder.length();
        for (int i = 0; i < DEFAULT_PASSCODE_LENGTH; i++){
            if (i < length){
                passcodeImages[i].setImageResource(R.drawable.passcode_filled);
            }
            else {
                passcodeImages[i].setImageResource(R.drawable.passcode);
            }
        }

        if (length == 0){
            deleteTextView.setText(cancelText);
        }
        else {
            deleteTextView.setText(deleteText);
        }
    }


    public void setCallback(IOSPasscodeViewCallback callback){
        this.callback = callback;
    }


    public String getPasscode(){
        return passcodeBuilder.toString();
    }

    public void setPasscode(String passcode){
        passcodeBuilder.delete(0, passcodeBuilder.length());
        if (passcode != null){
            passcodeBuilder.append(passcode);
        }
        updateUI();
    }

    public void clearPasscode(){
        setPasscode(null);
    }


    public String getEnterPasscodeText() {
        return enterPasscodeText;
    }

    public void setEnterPasscodeText(String enterPasscodeText) {
        this.enterPasscodeText = enterPasscodeText;
        enterPasscodeTextView.setText(enterPasscodeText);
    }


    public String getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(String deleteText) {
        this.deleteText = deleteText;
        updateUI();
    }


    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
        updateUI();
    }


    public boolean isVibrateOnIncorrect(){
        return vibrateOnIncorrect;
    }

    public void setVibrateOnIncorrect(boolean vibrateOnIncorrect){
        this.vibrateOnIncorrect = vibrateOnIncorrect;
    }

    
    public int getVibrateDuration() {
        return vibrateDuration;
    }

    public void setVibrateDuration(int vibrateDuration) {
        this.vibrateDuration = vibrateDuration;
    }
}
