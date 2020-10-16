package com.alexis.e_loan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alexis.e_loan.Model.STKPush;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.alexis.e_loan.Constants.BUSINESS_SHORT_CODE;
import static com.alexis.e_loan.Constants.CALLBACKURL;
import static com.alexis.e_loan.Constants.PARTYB;
import static com.alexis.e_loan.Constants.PASSKEY;
import static com.alexis.e_loan.Constants.TRANSACTION_TYPE;

public class PayActivity extends AppCompatActivity {


    private DarajaApiClient mApiClient;
    private Button mOkButton;
    private EditText mEnterPhone;
    private TextView mAmt;
    private ProgressBar mProgressBar5;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        mApiClient = new DarajaApiClient();
        mOkButton = findViewById(R.id.button_OK);
        mAmt = findViewById(R.id.text_amount);
        mProgressBar5 = findViewById(R.id.progressBar5);
        mError = findViewById(R.id.text_view_error_message);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mError.setVisibility(View.INVISIBLE);
                mEnterPhone = findViewById(R.id.edt_enter_phone);
                String phone_number = mEnterPhone.getText().toString();
                String amount = mAmt.getText().toString();
                performSTKPush(phone_number,amount);
            };
        });

    }
//    private void launchDialog() {
//        int width = (int)(getResources().getDisplayMetrics().widthPixels);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
//
//
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//        final AlertDialog alertDialog = alertBuilder.create();
//        alertDialog.show();
//        alertDialog.getWindow().setLayout(width,height);
//        final LayoutInflater inflater = this.getLayoutInflater();
//        final View alertView = inflater.inflate(R.layout.activity_pay,null);
//        alertDialog.getWindow().setContentView(alertView);
//
//        mOkButton = findViewById(R.id.button_OK);
//        mCancel = findViewById(R.id.image_view_cancel);
//        mAmt = findViewById(R.id.text_amount);
//
//        mCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//        mOkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                    alertDialog.dismiss();
//                mEnterPhone = findViewById(R.id.edt_enter_phone);
//                String phone_number = mEnterPhone.getText().toString();
//                String amount = mAmt.getText().toString();
//                performSTKPush(phone_number,amount);
//            };
//        });
//    }
    private void performSTKPush(String phone_number, String amount) {

        mProgressBar5.setVisibility(View.VISIBLE);
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "E-loan registration fee", //Account reference
                "Registration"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressBar5.setVisibility(View.INVISIBLE);
                try {
                    if (response.isSuccessful()) {
                        mError.setVisibility(View.VISIBLE);
                        mError.setText(R.string.success);
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        mError.setVisibility(View.VISIBLE);
                        mError.setText(R.string.error);
                        Log.e("TAG", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressBar5.setVisibility(View.INVISIBLE);
                Timber.e(t);
            }
        });
    }


}