@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.willaapps.justwords2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.willaapps.auth.presentation.register.RegisterScreen
import com.willaapps.auth.presentation.register.RegisterState
import com.willaapps.core.presentation.designsystem.JustWords2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustWords2Theme {
                RegisterScreen(state = RegisterState()) {

                }
            }
        }
    }
}