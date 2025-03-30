package com.hourcode.greetingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button myBtn;
    TextView title;
    TextView greetingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edittext);
        myBtn = findViewById(R.id.btn);
        title = findViewById(R.id.title);
        greetingText = findViewById(R.id.greetingText);

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = editText.getText().toString().trim();

                if (inputName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get current hour
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                // Determine greeting
                String timeGreeting;
                if (hour >= 6 && hour < 12) {
                    timeGreeting = "Good morning";
                } else if (hour >= 12 && hour < 18) {
                    timeGreeting = "Good afternoon";
                } else if (hour >= 18 && hour < 21) {
                    timeGreeting = "Good evening";
                } else {
                    timeGreeting = "Good night";
                }

                // Combine message
                String message = timeGreeting + ", " + inputName + "!";

                // Set greeting text
                //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                greetingText.setText(message);
            }
        });
    }
}