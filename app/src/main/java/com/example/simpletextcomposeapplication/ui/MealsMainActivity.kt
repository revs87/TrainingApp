package com.example.simpletextcomposeapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.simpletextcomposeapplication.ui.meals.MealsCategoriesScreen
import com.example.simpletextcomposeapplication.ui.theme.MyTheme

class MealsMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme() {
                MealsCategoriesScreen()
            }
        }
    }

}