package com.example.simpletextcomposeapplication.ui.meals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.simpletextcomposeapplication.model.response.CategoryResponse


@Composable
fun MealsCategoriesScreen() {
    val viewModel: MealsCategoriesViewModel = viewModel() // bound to the Composable lifecycle
    val meals = viewModel.mealsState.value

    LazyColumn {
        itemsIndexed(items = meals) { index, item ->
            MealCategory(category = item, index.isFirst(), index.isLast(meals.size))
        }
    }
}

private fun Int.isFirst(): Boolean = this == 0
private fun Int.isLast(size: Int): Boolean = this == size - 1

@Composable
fun MealCategory(
    category: CategoryResponse = CategoryResponse(
        "id",
        "name",
        "description",
        "https://media.istockphoto.com/id/1282169219/photo/christmas-theme-charcuterie-top-view-table-scene-against-dark-wood.jpg"
    ),
    first: Boolean = false,
    last: Boolean = false
) {
    var visible by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = if (first) 8.dp else 4.dp,
                bottom = if (last) 8.dp else 4.dp
            ),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier
                .clickable { visible = !visible }
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 18.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = category.imageUrl),
                modifier = Modifier
                    .weight(1f)
                    .size(if (visible) 160.dp else 60.dp)
                    .padding(end = 12.dp),
                contentDescription = null
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(text = category.name, style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = if (visible) 12.dp else 0.dp))
                AnimatedVisibility(visible = visible) {
                    Text(text = category.description, style = MaterialTheme.typography.body2, modifier = Modifier.alpha(ContentAlpha.medium))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealCategory()
}