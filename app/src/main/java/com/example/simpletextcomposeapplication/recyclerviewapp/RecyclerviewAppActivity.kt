package com.example.simpletextcomposeapplication.recyclerviewapp

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.cardview.widget.CardView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.databinding.ActivityLayoutGenericBinding
import com.example.simpletextcomposeapplication.theme.MyTheme

class RecyclerviewAppActivity : ComponentActivity() {

    private lateinit var context: Context
    fun getThisContext() = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this

        val dummyContentList = (0..100).map { it.toString() }


        /**
         *
         *  Toggle to see differences Compose/Views
         *
         * */
        setContentCompose(dummyContentList)
//        setContentViews(dummyContentList)
    }


    private fun setContentCompose(dummyContentList: List<String>) {
        setContent {
            MyTheme {
//                Scaffold(
//                    topBar = { TopAppBar(title = { Text(text = getString(R.string.app_name)) }) },
//                    modifier = Modifier.fillMaxSize()
//                ) { padding ->
                    val padding = 0.dp
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = colorResource(R.color.yellow_banana)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(dummyContentList) { item ->
                                val disabled = item.toInt() % 2 == 0
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(
                                        contentColor = if (disabled) Color.LightGray else Color.White
                                    ),
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        style = TextStyle(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold),
                                        text = item,
                                    )
                                }
                            }
                        }
                    }
//                }
            }
        }
    }

    lateinit var binding: ActivityLayoutGenericBinding

    private fun setContentViews(dummyContentList: List<String>) {
        binding = ActivityLayoutGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.background = getDrawable(R.color.yellow_banana)
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context).apply { orientation = LinearLayoutManager.VERTICAL }
        binding.recyclerView.adapter = RecyclerViewAdapter(
            dummyContentList.toMutableList(),
            Pair(
                ContextCompat.getColor(getThisContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(getThisContext(), R.color.white)
            )
        )

//        (binding.recyclerView.adapter as RecyclerViewAdapter).updateMeTo(9, 555)
    }
}

class RecyclerViewAdapter(private val items: MutableList<String>, private val bgColors: Pair<Int, Int>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    class MyViewItems(val mycard: CardView, val mytext: TextView)
    class MyViewHolder(mainView: View, val items: MyViewItems) : RecyclerView.ViewHolder(mainView)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_recyclerview_item, parent, false) as ViewGroup
        val items = MyViewItems(
            v.findViewById(R.id.my_card) as CardView,
            v.findViewById(R.id.my_text) as TextView
        )
        return MyViewHolder(v, items)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.items.mycard.setBackgroundColor(if (position % 2 == 0) bgColors.first else bgColors.second)
        holder.items.mytext.text = items[position]
        holder.items.mytext.setTypeface(null, Typeface.BOLD);
    }

    fun updateMeTo(position: Int, newValue: Int) {
        items[position] = newValue.toString()
        notifyItemChanged(position)
    }
}

