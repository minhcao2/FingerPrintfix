package com.example.fingerprintfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView headLabel;
    private TextView paraLabel;
    private ImageView fingerPrint;

    private FingerprintManager fingerprintManager;

    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headLabel = findViewById(R.id.headLabel);
        paraLabel = findViewById(R.id.paraLabel);
        fingerPrint = findViewById(R.id.fingerPrintImage);
        paraLabel.setText("test");

        // 1. Android version should be greater or equal to Marshmellow
        // 2. Device has FingerPrint Scanner or not
        // 3. Have permission to use fingerprint scanner in app
        // 4. Lock screen is secured with atleast 1 type of lock
        // At least 1 fingerprint is registered
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService((KEYGUARD_SERVICE));
            if (!(fingerprintManager.isHardwareDetected())) {
                paraLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED) {
                paraLabel.setText("Permission is not granted to use Fingerprint");
            } else if (!keyguardManager.isKeyguardSecure()) {
                paraLabel.setText("Add Lock to your phone in Settings");
            } else if (fingerprintManager.hasEnrolledFingerprints()) {
                paraLabel.setText("You should add at least one Fingerprint");
            } else {
                paraLabel.setText("Place your finger on Scanner to Access to the app");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.starAuth(fingerprintManager, null);


            }

        }
    }

}