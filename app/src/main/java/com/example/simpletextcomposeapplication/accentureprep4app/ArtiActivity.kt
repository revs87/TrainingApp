package com.example.simpletextcomposeapplication.accentureprep4app

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItem
import com.example.simpletextcomposeapplication.accentureprep4app.domain.ArtiItemDetails
import com.example.simpletextcomposeapplication.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val vm: ArtiViewModel = hiltViewModel()
            val list by vm.listState.collectAsStateWithLifecycle()
            val details by vm.detailsState.collectAsStateWithLifecycle()


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

                                ArtiList(
                                    list = list,
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
                                    vm.getDetails(id)
                                    ArtiDetails(
                                        details = details
                                    )
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
    private fun ArtiList(
        list: List<ArtiItem> = (1..4).map { ArtiItem(it.toLong(), "$it") },
        onItemClick: (Long) -> Unit = {}
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = list,
                key = { it.id }
            ) {
                Text(
                    modifier = Modifier.clickable { onItemClick.invoke(it.id) },
                    text = "${it.id}: ${it.title}"
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    private fun ArtiDetails(
        details: ArtiItemDetails = ArtiItemDetails(0L, "title", "", "")
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("${details.id}")
            Text(details.title)
            Text("${details.description}")
            details.imageId?.let {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = "https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg",
                    contentDescription = it
                )
            }
        }
    }
}

//"https://www.artic.edu/iiif/2/$it/full/843,/0/default.jpg"