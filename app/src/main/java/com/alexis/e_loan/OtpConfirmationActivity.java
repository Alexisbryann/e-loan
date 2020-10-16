package com.alexis.e_loan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpConfirmationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mAuthVerificationId;
    private FirebaseUser mCurrentUser;
    private EditText mOtpVerification;
    private Button mVerifyOtp;
    private ProgressBar mProgressBar;
    private TextView mInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verifcation);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        mOtpVerification = findViewById(R.id.edit_text_enter_otp);
        mVerifyOtp = findViewById(R.id.button_verify_otp);
        mProgressBar = findViewById(R.id.progressBar2);
        mInformation = findViewById(R.id.text_view_otp_information);

        mVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Otp = mOtpVerification.getText().toString();

                if (Otp.isEmpty()) {
                    mInformation.setVisibility(View.VISIBLE);
                    mInformation.setText(R.string.otp_code);
                } else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, Otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpConfirmationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
                            // Sign in success, update UI with the signed-in user's information
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                mInformation.setVisibility(View.VISIBLE);
                                mInformation.setText(R.string.otp_verification_failed);
                            }
                        }
                        mVerifyOtp.setEnabled(true);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();

        if (mCurrentUser != null){
           sendUserToHome();
        }
    }
public void sendUserToHome(){
    Intent homeIntent = new Intent(OtpConfirmationActivity.this, HomeActivity.class);
    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(homeIntent);
    finish();
}
}
