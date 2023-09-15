package com.example.simpletextcomposeapplication.jciapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.databinding.ActivityLayoutGenericBinding

class AdentisAppActivity : ComponentActivity(), Observer<List<GenderProfile>> {

    private lateinit var binding: ActivityLayoutGenericBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLayoutGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: IAdentisViewModel = ViewModelProvider(this)[AdentisViewModel::class.java]
        val observer = this as Observer<List<GenderProfile>>
        viewModel.getLiveData().observe(this, observer)
        viewModel.getSortedNamesData()
    }

    override fun onChanged(result: List<GenderProfile>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = MyRecyclerView(
            result,
            arrayOf(
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.teal_200),
                ContextCompat.getColor(this, R.color.purple_200)
            )
        )
    }
}

class MyRecyclerView(private val profileData: List<GenderProfile>, private val bgColors: Array<Int>) : RecyclerView.Adapter<MyRecyclerView.MyViewHolder>() {
    class MyViewItems(val mytext: TextView)
    class MyViewHolder(mainView: View, val items: MyViewItems) : RecyclerView.ViewHolder(mainView)

    override fun getItemCount() = profileData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view.rootView, MyViewItems(view.findViewById(R.id.my_text)))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.items.mytext.text = "${profileData[position].name}: ${profileData[position].numberOfOccurrences}"
        holder.items.mytext.setBackgroundColor(when (profileData[position].gender) {
            "M" -> bgColors[1]
            "F" -> bgColors[2]
            else -> bgColors[0]
        })
    }
}
