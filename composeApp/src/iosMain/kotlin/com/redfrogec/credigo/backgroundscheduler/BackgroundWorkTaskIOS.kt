package com.redfrogec.credigo.backgroundscheduler

import kotlinx.cinterop.*
import platform.BackgroundTasks.*
import platform.Foundation.*
import kotlinx.coroutines.*

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun initializeBackgroundScheduler() {
    val taskId = "com.redfrogec.credigo.backgroundscheduler"
    
    BGTaskScheduler.sharedScheduler.registerForTaskWithIdentifier(taskId, null) { task ->
        if (task is BGAppRefreshTask) {
            handleBackgroundTask(task)
        }
    }
    
    scheduleNextBackgroundTask()
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun handleBackgroundTask(task: BGAppRefreshTask) {
    scheduleNextBackgroundTask() // Re-programar la siguiente
    
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
    task.expirationHandler = {
        scope.cancel()
    }

    scope.launch {
        try {
            performSharedBackgroundWork()
            task.setTaskCompletedWithSuccess(true)
        } catch (e: Exception) {
            task.setTaskCompletedWithSuccess(false)
        }
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun scheduleNextBackgroundTask() {
    val taskId = "com.redfrogec.credigo.backgroundscheduler" // fixed taskId to match registration
    val request = BGAppRefreshTaskRequest(taskId)
    
    // iOS no garantiza ejecución exacta cada 5 min, pero 15.0 es el valor mínimo recomendado
    request.earliestBeginDate = NSDate().dateByAddingTimeInterval(15.0 * 60.0)
    
    memScoped {
        val errorPtr = alloc<ObjCObjectVar<NSError?>>()
        val success = BGTaskScheduler.sharedScheduler.submitTaskRequest(request, errorPtr.ptr)
        if (!success) {
            val error = errorPtr.value
            println("Error programando BGTask: ${error?.localizedDescription}")
        }
    }
}
