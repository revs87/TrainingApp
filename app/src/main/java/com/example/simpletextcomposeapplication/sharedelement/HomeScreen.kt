package com.example.simpletextcomposeapplication.sharedelement

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.sharedelement.navigation.HomeDetails

val items: List<HomeDetails> = listOf(
    HomeDetails(R.drawable.default_profile_picture, "Default profile picture"),
    HomeDetails(R.drawable.ic_launcher_foreground, "Icon Launcher"),
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    onItemClick: (resId: Int, text: String) -> Unit = { _, _ -> },
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    LazyColumn {
        itemsIndexed(
            items = items,
            key = { i, item -> "${item.resId}-$i" }
        ) { i, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick.invoke(item.resId, item.text) },
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "image/${item.resId}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 300)
                            }
                        ),
                    model = item.resId,
                    contentDescription = "${item.resId}-$i"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sharedElement(
                            state = rememberSharedContentState(key = "text/${item.text}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 300)
                            }
                        ),
                    text = item.text
                )
            }
        }
    }
}
