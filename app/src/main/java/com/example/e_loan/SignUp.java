package com.example.e_loan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private TextView mCountry_extension;
    private EditText mPhoneNumber;
    private Button mSendOTP;
    private ProgressBar mProgressBar;
    private TextView mInformationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mCountry_extension = findViewById(R.id.text_view_254);
        mPhoneNumber = findViewById(R.id.editTextPhone);
        mSendOTP = findViewById(R.id.button_send_otp);
        mProgressBar = findViewById(R.id.progressBar);
        mInformationText = findViewById(R.id.text_view_information_text);

        mSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerification();
            }
        });
    }

    private void sendVerification() {
        String countryExtension = mCountry_extension.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String completeNumber = countryExtension + phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                completeNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }
}