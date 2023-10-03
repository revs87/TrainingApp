package com.example.simpletextcomposeapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TrainingApplication : Application() {

    companion object {
//        private lateinit var INSTANCE: TrainingApplication
//
//        fun getInstance(): TrainingApplication =
//            if (::INSTANCE.isInitialized) {
//                INSTANCE
//            } else {
//                synchronized(this) {
//                    INSTANCE = TrainingApplication().also { INSTANCE = it }
//                    INSTANCE
//                }
//            }

        private var INSTANCE: TrainingApplication? = null

        fun getInstance(): TrainingApplication = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TrainingApplication().also { INSTANCE = it }
        }

    }
}