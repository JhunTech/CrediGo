package com.redfrogec.credigo.backgroundscheduler

import android.content.Context
import android.util.Log
import androidx.work.*
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.utils.LoanUtils
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CancellationException

class BackgroundWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val manager: LocalNotificationManager,
    private val loanUtils: LoanUtils
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("BackgroundWorker", "Iniciando tarea en segundo plano")
        return try {
            // Solo mostrar notificación si tiene permiso concedido previamente
            manager.showNotification("CrediGo", "Ejecutando tarea en segundo plano...")
            
            // Tarea compartida del commonMain
            val loans = loanUtils.loadLoansInfo()
            Log.d("BackgroundWorker", "Información de préstamos cargada: ${loans.size} registros.")
            
            Log.d("BackgroundWorker", "Tarea completada con éxito")
            Result.success()
        } catch (e: CancellationException) {
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
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
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

    // Usamos KEEP para evitar cancelar y re-encolar la tarea cada vez que se inicia la app.
    // Esto reduce el riesgo de inconsistencias con los paths del APK tras una actualización.
    workManager.enqueueUniquePeriodicWork(
        "CrediGoBackgroundTask",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
