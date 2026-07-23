package com.redfrogec.credigo.backgroundscheduler

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.utils.LoanUtils
import kotlinx.coroutines.CancellationException
import java.util.concurrent.TimeUnit

class BackgroundWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val manager: LocalNotificationManager,
    private val loanUtils: LoanUtils
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("BackgroundWorker", "Iniciando tarea en segundo plano")
        return try {
            // Tarea compartida del commonMain
            val loans = loanUtils.loadLoansInfo(true)
            if(loans.isNotEmpty()){
                loans.forEach { loan ->
                    if(loan.dueInfo.startsWith("Atrasado")){
                        val loanMessage = "Cuotas ${loan.quotaInfo} - ${loan.dueInfo}"
                        manager.showNotification("Prést N.${loan.id} de ${loan.clientName}", loanMessage)
                    }
                }
            }
            Log.d("BackgroundWorker", "Información de préstamos cargada: ${loans.size} registros.")

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
