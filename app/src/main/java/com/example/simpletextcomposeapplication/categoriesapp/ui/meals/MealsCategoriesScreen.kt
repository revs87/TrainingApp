package com.example.simpletextcomposeapplication.categoriesapp.ui.meals

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.simpletextcomposeapplication.categoriesapp.repository.domain.CategoryDomain


@Composable
fun MealsCategoriesScreen(navigationCallback: (String) -> Unit) {
    val viewModel: MealsCategoriesViewModel = viewModel() // bound to the Composable lifecycle
    val meals = viewModel.categoriesState.value

    LazyColumn {
        itemsIndexed(items = meals) { index, item ->
            MealCategory(category = item, index.isFirst(), index.isLast(meals.size), navigationCallback)
        }
    }
}

private fun Int.isFirst(): Boolean = this == 0
private fun Int.isLast(size: Int): Boolean = this == size - 1

@Composable
fun MealCategory(
    category: CategoryDomain = CategoryDomain(
        "id",
        "name",
        "description",
        "https://media.istockphoto.com/id/1282169219/photo/christmas-theme-charcuterie-top-view-table-scene-against-dark-wood.jpg"
    ),
    first: Boolean = false,
    last: Boolean = false,
    navigationCallback: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                .fillMaxWidth()
                .animateContentSize()
                .clickable { navigationCallback(category.id) }
                .padding(top = 18.dp, bottom = 18.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = category.imageUrl),
                modifier = Modifier
                    .weight(1f)
                    .size(if (expanded) 160.dp else 60.dp)
                    .padding(end = 12.dp),
                contentDescription = null
            )
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(text = category.name, style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (expanded) 10 else 4
                )
            }
            Icon(
                modifier = Modifier.alpha(ContentAlpha.medium).clickable { expanded = !expanded }.padding(8.dp).align(Alignment.CenterVertically),
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealCategory() {}
}