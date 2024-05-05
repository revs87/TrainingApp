package com.example.simpletextcomposeapplication.sharedelement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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

@OptIn(ExperimentalSharedTransitionApi::class)
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
                        SharedTransitionLayout {
                            NavHost(
                                navController = navController,
                                startDestination = Home
                            ) {
                                composable<Home> {
                                    HomeScreen(
                                        onItemClick = { resId, text -> navController.navigate(HomeDetails(resId, text)) },
                                        animatedVisibilityScope = this
                                    )
                                }
                                composable<HomeDetails> { navBackStackEntry ->
                                    val homeDetails: HomeDetails = navBackStackEntry.toRoute()
                                    val animatedVisibilityScope = this
                                    with(homeDetails) {
                                        HomeDetailsScreen(
                                            resId = resId,
                                            text = text,
                                            animatedVisibilityScope = animatedVisibilityScope
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
}

