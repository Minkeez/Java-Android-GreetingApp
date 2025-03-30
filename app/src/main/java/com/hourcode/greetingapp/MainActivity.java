package com.hourcode.greetingapp;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button myBtn;
    TextView title;
    TextView greetingText;
    RecyclerView greetingRecycler;
    GreetingAdapter adapter;
    Button clearHistoryBtn;

    List<String> greetingList;
    SharedPreferences prefs;
    static final String PREF_KEY = "greeting_history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI bindings
        editText = findViewById(R.id.edittext);
        myBtn = findViewById(R.id.btn);
        title = findViewById(R.id.title);
        greetingText = findViewById(R.id.greetingText);
        greetingRecycler = findViewById(R.id.greetingHistoryRecyclerView);
        clearHistoryBtn = findViewById(R.id.clearHistoryBtn);

        // Set up SharedPreferences
        prefs = getSharedPreferences("GreetingAppPrefs", Context.MODE_PRIVATE);

        // Load saved greeting history
        greetingList = loadGreetings();

        adapter = new GreetingAdapter(greetingList);
        greetingRecycler.setLayoutManager(new LinearLayoutManager(this));
        greetingRecycler.setAdapter(adapter);

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = editText.getText().toString().trim();

                if (inputName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Time-based greeting
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
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

                // Timestamp
                String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.getTime());

                // Final greeting
                String message = timeGreeting + ", " + inputName + "! (" + time + ")";
                greetingText.setText(message);

                // Add to history
                greetingList.add(0, message); // Add to top
                adapter.notifyItemInserted(0);
                saveGreetings(greetingList);
            }
        });

        clearHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greetingList.clear();
                adapter.notifyDataSetChanged();
                prefs.edit().remove(PREF_KEY).apply();
                Toast.makeText(MainActivity.this, "Greeting history cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Save list to SharedPreferences as JSON
    private void saveGreetings(List<String> list) {
        JSONArray jsonArray = new JSONArray(list);
        prefs.edit().putString(PREF_KEY, jsonArray.toString()).apply();
    }

    // Load list from SharedPreferences
    private List<String> loadGreetings() {
        List<String> list = new ArrayList<>();
        String json = prefs.getString(PREF_KEY, null);
        if (json != null) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}