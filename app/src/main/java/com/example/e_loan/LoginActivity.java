package com.example.e_loan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.e_loan.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_login);

        mFragment = LoginFragment.getInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_holder,mFragment)
                .commit();
    }
}
