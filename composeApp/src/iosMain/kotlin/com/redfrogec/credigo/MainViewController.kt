package com.redfrogec.credigo

import androidx.compose.ui.window.ComposeUIViewController
import com.redfrogec.credigo.di.initializeKoin

fun MainViewController() = ComposeUIViewController (
    configure = { 
        initializeKoin()
    }
) { App() }
