@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3AdaptiveApi::class)

package com.example.simpletextcomposeapplication.itunestop100.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletextcomposeapplication.core.UserPreference
import com.example.simpletextcomposeapplication.itunestop100.domain.model.ITunesAlbum


@Composable
fun ListDetailLayout(
    modifier: Modifier = Modifier,
    items: List<ITunesAlbum>,
    dataStoreData: UserPreference?
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
//                items(items) {
                item {
                    Text(
                        text = "Updated on: ${dataStoreData?.data}",
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(16.dp),
                        fontSize = 13.sp,
                        color = Color.Gray,
                    )
                }
                items(
                    count = items.size,
                    contentType = { items[it] },
                    key = { items[it].id },
                ) {
                    Text(
                        "${it + 1}: ${items[it].name}",
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = "Album: ${items[it].name}"
                                )
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        detailPane = {
            val content = navigator.currentDestination?.content?.toString() ?: "Select an item"
            AnimatedPane {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = content)
                    Row {
                        AssistChip(
                            onClick = {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Extra,
                                    content = "Option 1"
                                )
                            },
                            label = {
                                Text(text = "Option 1")
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AssistChip(
                            onClick = {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Extra,
                                    content = "Option 2"
                                )
                            },
                            label = {
                                Text(text = "Option 2")
                            }
                        )
                    }
                }
            }
        },
        extraPane = {
            val content = navigator.currentDestination?.content?.toString() ?: "Select an option"
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = content)
                }
            }
        })
}