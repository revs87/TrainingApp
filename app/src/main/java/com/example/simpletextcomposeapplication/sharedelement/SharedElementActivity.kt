package com.example.simpletextcomposeapplication.sharedelement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simpletextcomposeapplication.sharedelement.navigation.Home
import com.example.simpletextcomposeapplication.sharedelement.navigation.HomeDetails
import com.example.simpletextcomposeapplication.theme.MyTheme

class SharedElementActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()

            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Home
                        ) {
                            composable<Home> {
                                HomeScreen(
                                    onItemClick = { resId, text -> navController.navigate(HomeDetails(resId, text)) }
                                )
                            }
                            composable<HomeDetails> { navBackStackEntry ->
                                val homeDetails: HomeDetails = navBackStackEntry.toRoute()
                                with(homeDetails) {
                                    HomeDetailsScreen(
                                        resId = resId,
                                        text = text
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

