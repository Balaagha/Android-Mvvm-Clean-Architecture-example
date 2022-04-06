package com.example.androidmvvmcleanarchitectureexample.services

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RemoteConfigFetcherWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {


    companion object {

        fun enqueue(context: Context) {
            Log.d("myWorkTag","enqueue call")
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequestBuilder =
                OneTimeWorkRequestBuilder<RemoteConfigFetcherWorker>().setConstraints(constraints)

            workRequestBuilder.setBackoffCriteria(BackoffPolicy.LINEAR, 2, TimeUnit.SECONDS);
            WorkManager.getInstance(context).enqueueUniqueWork(
                RemoteConfigFetcherWorker::class.java.simpleName,
                ExistingWorkPolicy.KEEP,
                workRequestBuilder.build()
            )
        }
    }


    override fun doWork(): Result {
        return try {
            // Block on the task for a maximum of 60 seconds, otherwise time out.
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            var checker = sharedPreferences.getInt("remote_config_checker",1)


            if (checker == 3) {
                Log.d("myWorkTag","checker == true | checker => $checker")
            } else {
                checker++
                abc()
                sharedPreferences.edit().putInt("remote_config_checker", checker).apply()
                Log.d("myWorkTag","checker == false, and throw TimeoutException() | checker => $checker")
                throw TimeoutException()
            }
            Log.d("myWorkTag","after try Result.success()")
            sharedPreferences.edit().putBoolean("remote_config_stale", true).apply()
            sharedPreferences.edit().putInt("remote_config_checker", 1).apply()

            return Result.success()
        } catch (e: InterruptedException) {
            // An interrupt occurred while waiting for the task to complete.
            return Result.retry()
        } catch (e: TimeoutException) {
            // Task timed out before it could complete.
            Log.d("myWorkTag","catch (e: TimeoutException)")
            return Result.retry()
        }
    }

    private fun abc(): Any {
        var a = 1
        Log.d("myWorkTag","abc() finished")
        return Any()
    }
}
