package com.example.greetingapp;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.greetingapp.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            userName = savedInstanceState.getString("userName", "");
            updateGreeting();
        }

        binding.btnGreet.setOnClickListener(v -> showNameDialog());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("userName", userName);
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите ваше имя");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_name, null);
        builder.setView(dialogView);

        TextInputEditText etName = dialogView.findViewById(R.id.et_name);
        Button btnPositive = dialogView.findViewById(R.id.btn_positive);
        Button btnNegative = dialogView.findViewById(R.id.btn_negative);

        btnPositive.setEnabled(false);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnPositive.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        AlertDialog dialog = builder.create();

        btnPositive.setOnClickListener(v -> {
            userName = etName.getText().toString().trim();
            if (!userName.isEmpty()) {
                updateGreeting();
                dialog.dismiss();
            }
        });

        btnNegative.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateGreeting() {
        if (!userName.isEmpty()) {
            SpannableString spannable = new SpannableString("Привет, " + userName + "!");

            int textColor;
            if (isDarkTheme()) {
                textColor = ContextCompat.getColor(this, R.color.white);
            } else {
                textColor = ContextCompat.getColor(this, R.color.black);
            }

            spannable.setSpan(
                    new ForegroundColorSpan(textColor),
                    0, spannable.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            spannable.setSpan(
                    new StyleSpan(Typeface.ITALIC),
                    0, 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            spannable.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    7, 8 + userName.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            binding.tvGreeting.setText(spannable);
        } else {
            binding.tvGreeting.setText("");
        }
    }

    private boolean isDarkTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}