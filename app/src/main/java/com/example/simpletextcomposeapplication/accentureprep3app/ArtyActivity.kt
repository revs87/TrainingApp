package com.example.simpletextcomposeapplication.accentureprep3app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.SubcomposeAsyncImage
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItem
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItemDetails
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val viewModel: ArtyViewModel = hiltViewModel()
            val list by viewModel.listState.collectAsStateWithLifecycle()
            val details by viewModel.detailsState.collectAsStateWithLifecycle()


            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = "list"
                        ) {

                            composable(
                                route = "list"
                            ) {
                                ArtyList(
                                    list = { list },
                                    onItemClick = { id -> navController.navigate("details/$id") }
                                )
                            }

                            composable(
                                route = "details/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.LongType
                                    }
                                )
                            ) { navBackStackEntry ->
                                navBackStackEntry.arguments?.getLong("id")?.let { id ->
                                    LaunchedEffect(id) {
                                        viewModel.getDetails(id)
                                    }
                                    when (details) {
                                        UiState.Starting -> {}
                                        UiState.Loading -> { CircularProgressIndicator() }
                                        is UiState.Success -> {
                                            ArtyDetails(
                                                details = (details as UiState.Success<ArtyItemDetails>).result,
                                                itemId = id
                                            )
                                        }
                                        is UiState.Error -> {
                                            LaunchedEffect((details as UiState.Error<ConnectionError>).timestamp) {
                                                println("ERROR: " + (details as UiState.Error<ConnectionError>).error.msg)
                                            }
                                        }
                                    }
                                }
                            }


                        }

                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ArtyList(
    modifier: Modifier = Modifier,
    list: () -> List<ArtyItem> = { (0..3).map { ArtyItem(it.toLong(), "$it") } },
    onItemClick: (id: Long) -> Unit = {}
) {
    LazyColumn(

    ) {
        items(
            list(),
            { it.id }
        ) {
            Text(
                modifier = modifier.clickable { onItemClick.invoke(it.id) },
                text = "${it.id}: ${it.title}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtyDetails(
    details: ArtyItemDetails = ArtyItemDetails(0L, "", "", ""),
    itemId: Long = 0L
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "${details.id}")
        Text(text = details.title)
        Text(text = "${details.description}")
        details.imageId?.let {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = "https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg",
                contentDescription = it
            )
        }
    }
}
