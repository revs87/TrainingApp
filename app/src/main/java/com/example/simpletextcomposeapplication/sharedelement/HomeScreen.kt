package com.example.simpletextcomposeapplication.sharedelement

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.sharedelement.navigation.HomeDetails

val items: List<HomeDetails> = listOf(
    HomeDetails(R.drawable.default_profile_picture, "Default profile picture"),
    HomeDetails(R.drawable.ic_launcher_foreground, "Icon Launcher"),
)

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    onItemClick: (resId: Int, text: String) -> Unit = {_,_->}
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
                        .size(80.dp),
                    model = item.resId,
                    contentDescription = "${item.resId}-$i"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = item.text
                )
            }
        }
    }

}