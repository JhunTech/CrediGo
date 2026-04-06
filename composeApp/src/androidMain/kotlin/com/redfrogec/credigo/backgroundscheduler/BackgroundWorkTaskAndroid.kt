package com.redfrogec.credigo.backgroundscheduler

import android.content.Context
import androidx.work.*
import com.redfrogec.credigo.data.model.Constants
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CancellationException

class BackgroundWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Ejecuta la función del commonMain (ahora es suspend)
            performSharedBackgroundWork()
            Result.success()
        } catch (e: CancellationException) {
            // IMPORTANTE: No capturar CancellationException para permitir que CoroutineWorker
            // maneje la detención del worker correctamente.
            throw e
        } catch (e: Exception) {
            // Si hay un error, reintentar según la política de retroceso
            Result.retry()
        }
    }
}

private var appContext: Context? = null

fun setAppContext(context: Context) {
    appContext = context
}

actual fun initializeBackgroundScheduler() {
    appContext?.let { context ->
        val workRequest = PeriodicWorkRequestBuilder<BackgroundWorker>(
            Constants.TIME_MINUTES.toLong(), TimeUnit.MINUTES // Intervalo mínimo de Android
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        // Cambiamos KEEP por UPDATE para sincronizar correctamente con el JobStore del sistema
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CrediGoBackgroundTask",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
