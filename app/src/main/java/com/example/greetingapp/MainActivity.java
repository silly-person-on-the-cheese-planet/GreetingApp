package com.example.greetingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.greetingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView tvGreeting;
    private static final String GREETING_KEY = "greeting_text";
    private String currentGreeting = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvGreeting = binding.tvGreeting;

        if (savedInstanceState != null) {
            currentGreeting = savedInstanceState.getString(GREETING_KEY, "");
            tvGreeting.setText(currentGreeting);
        }

        binding.btnGreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNameDialog();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GREETING_KEY, currentGreeting);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentGreeting = savedInstanceState.getString(GREETING_KEY, "");
        tvGreeting.setText(currentGreeting);
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите имя");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_name, null);
        builder.setView(dialogView);

        final EditText etName = dialogView.findViewById(R.id.etName);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            if (!name.isEmpty()) {
                currentGreeting = "Привет, " + name + "!";
                tvGreeting.setText(currentGreeting);
            } else {
                Toast.makeText(MainActivity.this, "Пожалуйста, введите имя", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}