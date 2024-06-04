package com.example.simpletextcomposeapplication.itunestop100

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpletextcomposeapplication.itunestop100.presentation.ITunesAlbumsViewModel
import com.example.simpletextcomposeapplication.itunestop100.presentation.ListDetailLayout
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ITunesTop100Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->

                    val items by hiltViewModel<ITunesAlbumsViewModel>()
                        .getTop100Albums()
                        .collectAsStateWithLifecycle(initialValue = emptyList())

                    ListDetailLayout(
                        modifier = Modifier.padding(innerPadding),
                        items = items
                    )

                }
            }
        }
    }

}



