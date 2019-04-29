package com.maxproit.android_work_manager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public static String KEY_OUTPUT_TASK = "worker_task";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String desc = getInputData().getString(MainActivity.KEY_INPUT_TASK);
        displayNotification("Hey, this is task title", desc);

        Data data = new Data.Builder().putString(KEY_OUTPUT_TASK, "This is your output data").build();
        setOutputData(data);
        return Result.SUCCESS;
    }


    private void displayNotification(String task, String desc) {

        NotificationManager manager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("id", "my_notification", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "id")
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(task).setContentText(desc);

        manager.notify(1, builder.build());
    }
}
