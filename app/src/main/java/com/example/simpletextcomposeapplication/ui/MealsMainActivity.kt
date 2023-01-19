package com.example.simpletextcomposeapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simpletextcomposeapplication.ui.meals.CategoryDetailsScreen
import com.example.simpletextcomposeapplication.ui.meals.MealsCategoriesScreen
import com.example.simpletextcomposeapplication.ui.meals.MealsCategoryDetailsViewModel
import com.example.simpletextcomposeapplication.ui.theme.MyTheme

class MealsMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MealsApplication()
            }
        }
    }

    @Composable
    fun MealsApplication() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "categories") {
            composable(route = "categories") {
                MealsCategoriesScreen() { categoryId ->
                    navController.navigate(route = "category_details/${categoryId}")
                }
            }
            composable(
                route = "category_details/{category_id}",
                arguments = listOf(navArgument("category_id") { type = NavType.StringType })
            ) {
                val viewModel: MealsCategoryDetailsViewModel = viewModel()
                CategoryDetailsScreen(viewModel.categoryState.value)
            }
        }
    }

}