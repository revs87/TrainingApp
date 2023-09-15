package com.example.simpletextcomposeapplication.jciapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.theme.MyTheme


class DataAppActivity : ComponentActivity(), Observer<List<Data>> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: DataViewModel = ViewModelProvider(this)[DataViewModelImpl::class.java]
        viewModel.getLiveData().observe(this, this)
        viewModel.getData()
    }

    override fun onChanged(dataList: List<Data>) {
        setMyContentCompose(dataList)
//        setMyContentView(dataList)
    }

    private fun setMyContentCompose(dataList: List<Data>) {
        setContent {
            MyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(dataList) {
                            MyText(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyText(data: Data) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = when (data.gender) {
                        "M" -> colorResource(R.color.teal_200)
                        "F" -> colorResource(R.color.purple_200)
                        else -> colorResource(R.color.white)
                    }
                ),
            text = toScreen(data),
            fontSize = 20.sp,
            fontWeight = FontWeight(800),
            )
    }

    private fun setMyContentView(dataList: List<Data>) {
        setContentView(R.layout.activity_layout_generic)

        val recycleView: RecyclerView = findViewById(R.id.recycler_view)
        recycleView.layoutManager = LinearLayoutManager(this)
        val colorArray = arrayListOf(
            ContextCompat.getColor(this, R.color.teal_200),
            ContextCompat.getColor(this, R.color.purple_200),
            ContextCompat.getColor(this, R.color.white)
        )
        recycleView.adapter = MyRecyclerViewAdapter(dataList, colorArray)
    }
}

class MyRecyclerViewAdapter(private val dataList: List<Data>, val colorArray: ArrayList<Int>) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {
    class MyView(val textView: TextView)
    class MyViewHolder(itemView: View, val view: MyView) : ViewHolder(itemView)

    override fun getItemCount() = dataList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val myView = MyView(view.findViewById<TextView>(R.id.my_text))
        return MyViewHolder(view, myView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.textView.text = toScreen(dataList[position])
        holder.view.textView.setBackgroundColor(
            when (dataList[position].gender) {
                "M" -> colorArray[0]
                "F" -> colorArray[1]
                else -> colorArray[2]
            }
        )
    }
}

private fun toScreen(it: Data) = "${it.name}(${it.gender}): ${it.age}yo" + if (it.occurrences > 1) " (duplicated ${it.occurrences} times)" else ""
