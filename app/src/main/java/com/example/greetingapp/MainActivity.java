package com.example.greetingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.greetingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showNameDialog();
    }

    private void showNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите имя");
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_name, null);
        final EditText etName = dialogView.findViewById(R.id.etName);

        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    String formattedText = "<i>Привет</i>, <b>" + name + "</b>!";
                    binding.tvGreeting.setText(android.text.Html.fromHtml(formattedText, android.text.Html.FROM_HTML_MODE_LEGACY));
                } else {
                    Toast.makeText(MainActivity.this, "Имя не может быть пустым!", Toast.LENGTH_SHORT).show();
                    showNameDialog();
                }
            }
        });

        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        etName.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEmpty = TextUtils.isEmpty(s.toString().trim());
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(!isEmpty);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }
}
