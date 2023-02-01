package com.example.simpletextcomposeapplication.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val ShapesTopEndCut = Shapes(
    small = CutCornerShape(topEnd = 12.dp),
    medium = CutCornerShape(topEnd = 24.dp),
    large = CutCornerShape(topEnd = 36.dp),
)