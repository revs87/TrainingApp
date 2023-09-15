package com.example.simpletextcomposeapplication.categoriesapp.ui.meals

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.simpletextcomposeapplication.categoriesapp.repository.domain.CategoryDomain
import java.lang.Float.min


@Preview(showBackground = true)
@Composable
fun CategoryDetailsScreen(category: CategoryDomain? = CategoryDomain("id", "name", "desc", "url")) {
//    var profilePictureState by remember { mutableStateOf(Normal) }
//    val transition = updateTransition(targetState = profilePictureState, label = "")
//
//    val imageSizeDp: Dp by transition.animateDp(targetValueByState = { it.size }, label = "")
//    val color: Color by transition.animateColor(targetValueByState = { it.color }, label = "")
//    val borderWidthSizeDp: Dp by transition.animateDp(targetValueByState = { it.borderWidth }, label = "")
//
////    var isExpanded by remember { mutableStateOf(false) }
////    val imageSizeDp: Dp by animateDpAsState(targetValue = if (isExpanded) 400.dp else 200.dp)


//    val scrollState = rememberScrollState()
//    val offset = min(1f, 1 - (scrollState.value / 600f))
//    val imageSize by animateDpAsState(targetValue = max(60.dp, 165.dp * offset))


    val scrollState = rememberLazyListState()
    val offset = min(1f, 1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex))
    val imageSize by animateDpAsState(targetValue = max(60.dp, 165.dp * offset))

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Surface(shadowElevation = 4.dp) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        colors = CardDefaults.cardColors(
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        shape = CircleShape,
                        border = BorderStroke(
                            width = 2.dp,
                            color = Color.Magenta
                        )
                    ) {
                        val data = ImageRequest.Builder(LocalContext.current)
                            .data(category?.imageUrl ?: "")
                            .transformations(CircleCropTransformation())
                            .build()
                        Image(
                            painter = rememberAsyncImagePainter(model = data),
                            contentDescription = null,
                            modifier = Modifier
                                .size(imageSize)
                                .padding(8.dp)
                        )
                    }
                    Text(
                        text = category?.name ?: "Default name",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

//                Button(
//                    modifier = Modifier
//                        .padding(16.dp),
//                    onClick = {
////                profilePictureState = when (profilePictureState) {
////                    Normal -> Expanded
////                    Expanded -> Normal
////                }
//                    }) {
//                    Text(text = "Change state of picture")
//                }
            }
            val dummyContentList = (0..100).map { it.toString() }
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(dummyContentList) { dummyItem ->
                    Text(text = dummyItem, modifier = Modifier.padding(24.dp))
                }

            }
        }
    }


}

enum class ProfilePictureState(val color: Color, val size: Dp, val borderWidth: Dp) {
    Normal(Color.Magenta, 120.dp, 8.dp),
    Expanded(Color.Green, 200.dp, 24.dp),
}