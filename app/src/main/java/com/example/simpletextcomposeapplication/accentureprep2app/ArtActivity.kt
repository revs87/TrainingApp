package com.example.simpletextcomposeapplication.accentureprep2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtDetails
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtItem
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val viewModel: ArtViewModel = hiltViewModel()
            val listState by viewModel.stateListFromFlow.collectAsStateWithLifecycle(emptyList())
            val detailsState by viewModel.stateDetails.collectAsStateWithLifecycle()

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
                                if (it.savedStateHandle.contains("ok")) { println("Return data OK: ${it.savedStateHandle.get<Long>("ok")}") }

                                ArtList(
                                    state = { listState },
                                    onItemClick = { id -> navController.navigate("details/$id") },
                                    onBtnClick = viewModel::getArtList
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
                                navBackStackEntry.arguments?.getLong("id").let { id ->
                                    LaunchedEffect(id) {
                                        navController.previousBackStackEntry?.let { it.savedStateHandle["ok"] = id }
                                        viewModel.setId(id)
                                    }
                                    ArtDetails { detailsState }
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
        state: () -> List<ArtItem> = { (0..5).map { ArtItem(it.toLong(), "") } },
        onItemClick: (Long) -> Unit = {},
        onBtnClick: () -> Unit = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                items(state()) {
                    Text(
                        modifier = Modifier.clickable { onItemClick.invoke(it.id) },
                        text = "${it.id}: ${it.title}"
                    )
                }
            }
            Button(onClick = { onBtnClick.invoke() }) {
                Text(text = "Refresh")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ArtDetails(
        state: () -> ArtDetails = { ArtDetails(0L, "") },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = state().title)
            Text(text = state().description ?: "Empty description")

            state().imageId?.let {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = "https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg",
                    contentDescription = it
                )
            }
        }
    }
}
