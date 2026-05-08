package com.redfrogec.credigo.backgroundscheduler

import android.content.Context
import android.util.Log
import androidx.work.*
import com.redfrogec.credigo.data.model.Constants
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CancellationException

class BackgroundWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val manager: LocalNotificationManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("BackgroundWorker", "Iniciando tarea en segundo plano... ID: ${id}")
        return try {
            // Solo mostrar notificación si tiene permiso concedido previamente
            if (manager.hasPermission()) {
                manager.showNotification("CrediGo", "Ejecutando tarea en segundo plano...")
            }
            
            // Tarea compartida del commonMain
            performSharedBackgroundWork()
            
            Log.d("BackgroundWorker", "Tarea completada con éxito")
            Result.success()
        } catch (e: CancellationException) {
            // CoroutineWorker usa CancellationException para señalar que el worker debe detenerse.
            // No es un error, es un comportamiento esperado cuando WorkManager detiene la tarea.
            Log.d("BackgroundWorker", "Tarea cancelada o interrumpida por el sistema")
            throw e
        } catch (e: Exception) {
            Log.e("BackgroundWorker", "Error en la ejecución", e)
            Result.retry()
        }
    }
}

fun initializeBackgroundScheduler(workManager: WorkManager) {
    Log.d("BackgroundWorker", "Configurando scheduler periódico")
    
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<BackgroundWorker>(
        Constants.TIME_MINUTES.toLong(), TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            WorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )
        .build()

    // Cambiamos a KEEP para evitar que se cancele y reinicie la tarea cada vez que se inicia la app
    // si no hay cambios significativos en la configuración.
    workManager.enqueueUniquePeriodicWork(
        "CrediGoBackgroundTask",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
