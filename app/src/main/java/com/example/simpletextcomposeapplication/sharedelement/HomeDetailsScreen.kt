package com.example.simpletextcomposeapplication.sharedelement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Preview(showBackground = true)
@Composable
fun HomeDetailsScreen(
    resId: Int = 0,
    text: String = "Text"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
            ,
            model = resId,
            contentDescription = "$resId"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text)
    }
}