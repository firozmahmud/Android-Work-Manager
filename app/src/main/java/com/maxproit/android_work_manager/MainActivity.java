package com.maxproit.android_work_manager;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    public static String KEY_INPUT_TASK = "main_activity_key_task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder().putString(KEY_INPUT_TASK, "Hey, I'am sending you this data").build();
        Constraints constraints = new Constraints.Builder().setRequiresCharging(true).build();
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).setInputData(data)
                .setConstraints(constraints).build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(request);
            }
        });

        final TextView textView = findViewById(R.id.textView);

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                textView.append("\n" + workInfo.getState().name());
                String output_data = workInfo.getOutputData().getString(MyWorker.KEY_OUTPUT_TASK);
                textView.append("\n" + output_data);
            }
        });
    }
}
