package com.example.simpletextcomposeapplication.itunestop100

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.itunestop100.presentation.ITunesAlbumsViewModel
import com.example.simpletextcomposeapplication.itunestop100.presentation.adapter.ITunesAlbumsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ITunesTop100RecyclerviewActivity : ComponentActivity() {

    private val viewModel: ITunesAlbumsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_generic)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            viewModel.refresh()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ITunesAlbumsAdapter()
        recyclerView.adapter = adapter
        viewModel.getTop100Albums()
            .onEach { list ->
                withContext(Dispatchers.Main) {
                    adapter.submitList(list)
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModel.viewModelScope)
    }
}



