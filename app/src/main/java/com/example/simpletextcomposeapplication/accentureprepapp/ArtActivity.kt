package com.example.simpletextcomposeapplication.accentureprepapp

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.SubcomposeAsyncImage
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtDetailsItem
import com.example.simpletextcomposeapplication.accentureprepapp.domain.ArtItem
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ArtViewModel = hiltViewModel()
            val list = viewModel.state.collectAsStateWithLifecycle()
            val detailsItem = viewModel.stateArtDetails.collectAsStateWithLifecycle()

            val navController = rememberNavController()

            MyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
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
                                ArtList(
                                    list = list,
                                    onItemClick = { id -> navController.navigate("details/$id") },
                                    viewModel::refreshArtItems
                                )
                            }
                            composable(
                                route = "details/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.LongType
                                    }
                                )
                            ) { navBackStack ->
                                navBackStack.arguments?.getLong("id")?.let { id ->
                                    LaunchedEffect(id) {
                                        viewModel.getArtItemDetails(id)
                                    }
                                    ArtDetails(
                                        detailsItem = detailsItem
                                    ) { navController.popBackStack("list", false) }
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
private fun ArtList(
    list: State<List<ArtItem>> = derivedStateOf { (1L..4L).map { ArtItem(it, "Test") } },
    onItemClick: (Long) -> Unit = { _ ->},
    onBtnClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(list.value) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onItemClick.invoke(it.id) },
                    text = "${it.id}: ${it.title}"
                )
            }
        }
        Button(onClick = onBtnClick) {
            Text(text = "Refresh")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ArtDetails(
    detailsItem: State<ArtDetailsItem> = derivedStateOf { ArtDetailsItem(0L, "", "", "") },
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBackClick) {
            Text(text = "Go back")
        }
        Text(text = detailsItem.value.title)
        Text(text = detailsItem.value.description)
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = "https://www.artic.edu/iiif/2/${detailsItem.value.imageId}/full/843,/0/default.jpg",
            contentDescription = detailsItem.value.imageId
        )
    }
}