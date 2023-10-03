package com.example.simpletextcomposeapplication.meowfactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeowFactsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel: MeowFactsViewModel = viewModel()
            viewModel.init(LocalContext.current)
            val list = remember { viewModel.state }

            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        MeowFactsLazyColumn(
                            viewModel::addMeowFact,
                            list
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MeowFactsLazyColumn(
    onButtonClick: () -> Unit = {},
    list: MutableList<String> = mutableListOf("test1","test2","test3")
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Button(
                onClick = { onButtonClick.invoke() }
            ) {
                Text(text = "Add new fact")
            }
        }
        items(list) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = it
            )
        }
    }
}