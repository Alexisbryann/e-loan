package com.example.e_loan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextView mCountry_extension;
    private EditText mPhoneNumber;
    private Button mSendOTP;
    private ProgressBar mProgressBar;
    private TextView mInformationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mCountry_extension = findViewById(R.id.text_view_254);
        mPhoneNumber = findViewById(R.id.editTextPhone);
        mSendOTP = findViewById(R.id.button_send_otp);
        mProgressBar = findViewById(R.id.progressBar);
        mInformationText = findViewById(R.id.text_view_information_text);

        mSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String countryExtension = mCountry_extension.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                String completeNumber = countryExtension + phoneNumber;

                if (countryExtension.isEmpty() || phoneNumber.isEmpty()){
                    mInformationText.setText(R.string.information_text1);
                    mInformationText.setVisibility(View.VISIBLE);
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mSendOTP.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            SignUp.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Intent otpIntent = new Intent(SignUp.this,OtpConfirmationActivity.class);
                startActivity(otpIntent);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mCurrentUser != null){
            Intent homeIntent = new Intent(SignUp.this,SignUp.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        }
    }

}