package com.example.simpletextcomposeapplication.ui.meals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.simpletextcomposeapplication.model.response.CategoryResponse

@Composable
fun CategoryDetailsScreen(category: CategoryResponse?) {
    Column {
        Row {
            Card {
                val data = ImageRequest.Builder(LocalContext.current)
                    .data(category?.imageUrl ?: "")
                    .transformations(CircleCropTransformation())
                    .build()
                Image(
                    painter = rememberAsyncImagePainter(model = data),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Text(category?.name ?: "Default name")
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Change state of picture")
        }
    }
}