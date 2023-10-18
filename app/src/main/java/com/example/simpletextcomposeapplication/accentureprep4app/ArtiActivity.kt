package com.example.simpletextcomposeapplication.accentureprep4app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {


                    }
                }
            }
        }
    }

}


//"https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg"