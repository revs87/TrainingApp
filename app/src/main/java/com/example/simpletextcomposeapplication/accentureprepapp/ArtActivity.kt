package com.example.simpletextcomposeapplication.accentureprepapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtItem
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ArtViewModel = hiltViewModel()
            val list = viewModel.state.collectAsStateWithLifecycle().value

            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        ArtList(list)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtList(
    list: List<ArtItem> = (1L..4L).map { ArtItem(it, "Test") }
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "${it.id}: ${it.title}"
            )
        }
    }
}