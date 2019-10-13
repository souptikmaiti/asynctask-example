package com.example.asynchtaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
    }

    public void onStartAsyncTask(View view){
        ExampleAsyncTask exampleAsyncTask = new ExampleAsyncTask(this);
        exampleAsyncTask.execute(10);
    }
}
