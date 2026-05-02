package com.omobikes.app;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);

            LinearLayout btnCycleFinder = findViewById(R.id.btn_cycle_finder);
            LinearLayout btnPartsFinder = findViewById(R.id.btn_parts_finder);

            btnCycleFinder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isConnected()) {
                        showNoInternetDialog();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra("file", "cycle_finder.html");
                    intent.putExtra("title", "Find a Cycle");
                    startActivity(intent);
                }
            });

            btnPartsFinder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isConnected()) {
                        showNoInternetDialog();
                        return;
                    }
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    intent.putExtra("file", "cycle_parts_finder.html");
                    intent.putExtra("title", "Find Cycle Parts");
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("Please check your Wi-Fi or mobile data and try again.")
            .setPositiveButton("OK", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle("Exit OmoBikes")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Exit", (dialog, which) -> {
                super.onBackPressed();
                finish();
            })
            .setNegativeButton("Stay", null)
            .show();
    }
}
