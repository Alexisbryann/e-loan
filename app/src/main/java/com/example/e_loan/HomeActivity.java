package com.example.e_loan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    int progress = 0;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private ProgressBar mProgressBar;
    private TextView mCongratulations;
    private Button mCheckLimit;
    private Handler hdlr = new Handler();
    private CardView mTermsAndConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mCheckLimit = findViewById(R.id.button_check_limit);
        mProgressBar = findViewById(R.id.progressBar3);
        mCongratulations = findViewById(R.id.text_view_congratulations);
        mTermsAndConditions = findViewById(R.id.card_view_terms_and_conditions);

        mCheckLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                setProgressValue(progress);
            }
        });
    }
    private void setProgressValue(final int progress) {
        // set the progress
        mProgressBar.setProgress(progress);
        // thread is used to change the progress value
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                hdlr.post(new Runnable() {
                    public void run() {
                        if (progress == 30) {
                            mCongratulations.setVisibility(View.VISIBLE);
                            mTermsAndConditions.setVisibility(View.VISIBLE);

                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }
    @Override
    public void onStart() {
        super.onStart();

        if (mCurrentUser == null){
            Intent loginIntent = new Intent(HomeActivity.this,SignUp.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
    }
}