package com.example.fingerprintfix;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    private Handler mHandler = new Handler();

    public FingerprintHandler(Context context) {

        this.context = context;
    }

    public void starAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an Auth Error." + errString, false);
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication Failed", false);
        super.onAuthenticationFailed();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error" + helpString, false);
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now reset the timer", true);
        super.onAuthenticationSucceeded(result);
    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerPrintImage);
        paraLabel.setText(s);
        if (b == false) {
            paraLabel.setTextColor((ContextCompat.getColor(context, R.color.colorAccent)));

        } else {
            paraLabel.setTextColor((ContextCompat.getColor(context, R.color.colorPrimary)));
            imageView.setImageResource((R.mipmap.done));
            Intent intent = new Intent(context, test.class);
            context.startActivity(intent);
        }
    }
}

