package com.example.e_loan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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
    private Button mPayFee;
    private DarajaApiClient mApiClient;
    private Button mOkButton;
    private ImageView mCancel;
    private EditText mEnterPhone;
    private TextView mAmt;
    private ProgressDialog mProgressDialog;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mApiClient = new DarajaApiClient();

        mProgressBar = findViewById(R.id.progressBar3);
        mCongratulations = findViewById(R.id.text_view_congratulations);
        mTermsAndConditions = findViewById(R.id.card_view_terms_and_conditions);
        mPayFee = findViewById(R.id.button_pay_fee);
        mCheckLimit = findViewById(R.id.button_check_limit);

//        admob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mCheckLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCongratulations.setVisibility(View.INVISIBLE);
                mTermsAndConditions.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                setProgressValue(progress);
            }
        });

//        mPayFee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchDialog();
//            }
//        });
        mPayFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPayFee.setEnabled(false);
                Toast.makeText(getApplicationContext(),"You currently do not qualify for a loan",Toast.LENGTH_LONG).show();
//                Intent payIntent = new Intent(HomeActivity.this, PayActivity.class);
//                startActivity(payIntent);
            }
        });
    }

//    private void launchDialog() {
//
//        int width = (int)(getResources().getDisplayMetrics().widthPixels);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
//
//
//            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//            final AlertDialog alertDialog = alertBuilder.create();
//            alertDialog.show();
//            alertDialog.getWindow().setLayout(width,height);
//            final LayoutInflater inflater = this.getLayoutInflater();
//            final View alertView = inflater.inflate(R.layout.activity_pay,null);
//            alertDialog.getWindow().setContentView(alertView);
//
//        mOkButton = findViewById(R.id.button_OK);
//        mCancel = findViewById(R.id.image_view_cancel);
//        mAmt = findViewById(R.id.text_amount);
//
//        mCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.dismiss();
//                }
//            });
//
//            mOkButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    alertDialog.dismiss();
//                    mEnterPhone = findViewById(R.id.edt_enter_phone);
//                    String phone_number = mEnterPhone.getText().toString();
//                    String amount = mAmt.getText().toString();
//                    performSTKPush(phone_number,amount);
//                };
//            });
//    }
//    public void getAccessToken() {
//        mApiClient.setGetAccessToken(true);
//        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
//            @Override
//            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
//
//                if (response.isSuccessful()) {
//                    mApiClient.setAuthToken(response.body().accessToken);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
//
//            }
//        });
//    }

//    private void processPayment() {
//
//        performSTKPush(phone_number,amount);
//
//    }

//    private void performSTKPush(String phone_number, String amount) {
//        mProgressDialog.setMessage("Processing your request");
//        mProgressDialog.setTitle("Please Wait...");
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.show();
//        String timestamp = Utils.getTimestamp();
//        STKPush stkPush = new STKPush(
//                BUSINESS_SHORT_CODE,
//                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
//                timestamp,
//                TRANSACTION_TYPE,
//                String.valueOf(amount),
//                Utils.sanitizePhoneNumber(phone_number),
//                PARTYB,
//                Utils.sanitizePhoneNumber(phone_number),
//                CALLBACKURL,
//                "MPESA Android Test", //Account reference
//                "Testing"  //Transaction description
//        );
//
//        mApiClient.setGetAccessToken(false);
//
//        //Sending the data to the Mpesa API, remember to remove the logging when in production.
//        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
//            @Override
//            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
//                mProgressDialog.dismiss();
//                try {
//                    if (response.isSuccessful()) {
//                        Timber.d("post submitted to API. %s", response.body());
//                    } else {
//                        Timber.e("Response %s", response.errorBody().string());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
//                mProgressDialog.dismiss();
//                Timber.e(t);
//            }
//        });
//    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

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