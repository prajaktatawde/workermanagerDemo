package com.example.workmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraints = Constraints.Builder().setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED).build()


        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(BackgroundTask::class.java, 15, TimeUnit.SECONDS)
                .setInputData(Data.Builder().putBoolean("isStart", true).build())
                .setInitialDelay(6000, TimeUnit.MILLISECONDS)
                .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueue(periodicWorkRequest)

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observeForever {
            if (it != null) {

                Log.d("periodicWorkRequest", "Status changed to ${it.state.isFinished}")

            }
        }

    }
}