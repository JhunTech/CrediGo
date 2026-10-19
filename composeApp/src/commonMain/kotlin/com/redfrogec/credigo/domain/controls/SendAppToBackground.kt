package com.redfrogec.credigo.domain.controls

import androidx.compose.runtime.Composable
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.redfrogec.credigo.sendToBackground

@Composable
fun SendAppToBackground(){
    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        isBackEnabled = true, // You can toggle this dynamically
        onBackCompleted = {
            sendToBackground()
        }
    )
}