package com.example.simpletextcomposeapplication.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val LightGray700 = Color(0x60DCDCDC)
val LightGreen200 = Color(0x9932CD32)
val TextBlack = Color.Black
val TextWhite = Color.White


val Colors.lightGreen200: Color
    @Composable
    @ReadOnlyComposable
    get() = LightGreen200

val Colors.lightGray700: Color
    @Composable
    @ReadOnlyComposable
    get() = LightGray700

val Colors.textBlack: Color
    @Composable
    @ReadOnlyComposable
    get() = TextBlack

val Colors.textWhite: Color
    @Composable
    @ReadOnlyComposable
    get() = TextWhite
