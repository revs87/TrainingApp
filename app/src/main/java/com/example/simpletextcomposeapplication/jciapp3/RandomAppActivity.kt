package com.example.simpletextcomposeapplication.jciapp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.simpletextcomposeapplication.R


class Data2AppActivity : ComponentActivity(), Observer<List<Data2>> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: Data2ViewModel = ViewModelProvider(this)[Data2ViewModelImpl::class.java]
        viewModel.getLiveData().observe(this, this)
        viewModel.getData()
    }

    override fun onChanged(list: List<Data2>) {
        setContentView(R.layout.activity_layout_generic)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Data2Adapter(list)
    }
}

class Data2Adapter(private val list: List<Data2>) : RecyclerView.Adapter<Data2Adapter.MyViewHolder>() {
    class MyItemView(val text: TextView)
    class MyViewHolder(itemView: View, val myItemView: MyItemView) : ViewHolder(itemView)

    override fun getItemCount() = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Data2Adapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val myItemView = MyItemView(
            view.findViewById(R.id.my_text)
        )
        return MyViewHolder(view, myItemView)
    }

    override fun onBindViewHolder(holder: Data2Adapter.MyViewHolder, position: Int) {
        holder.myItemView.text.text = list[position].name + " " + list[position].number
    }
}
