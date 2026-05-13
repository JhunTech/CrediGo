package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redfrogec.credigo.backgroundscheduler.LocalNotificationManager
import kotlinx.coroutines.launch

class NotificationViewModel(private val manager: LocalNotificationManager): ViewModel() {

    fun onEnableNotifications() {
        viewModelScope.launch {
            if (!manager.hasPermission()) {
                val granted = manager.requestPermission()

                if (granted) {
                    manager.showNotification("Success", "Notifications enabled!")
                }
            } /*else {
                manager.showNotification("Wohooo", "Notifications enabled!")
            }*/
        }
    }
}