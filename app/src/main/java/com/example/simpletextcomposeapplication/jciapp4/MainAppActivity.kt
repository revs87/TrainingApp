package com.example.simpletextcomposeapplication.jciapp4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R

class MainAppActivity : ComponentActivity(), Observer<List<MeawData>> {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewModel: IMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(this, this)


        setContentView(R.layout.activity_layout_generic)
        recyclerView = findViewById(R.id.recycler_view)

        val editText: EditText = findViewById(R.id.edit_text)

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener {
            val currText = editText.text.toString().trim()
            if (currText.isDigitsOnly() && currText.toInt() > 0) {
                viewModel.getData(currText.toInt())
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onChanged(list: List<MeawData>) {
        recyclerView.adapter = MeawAdapter(list)
    }

}

class MeawAdapter(val list: List<MeawData>) : RecyclerView.Adapter<MeawAdapter.MyViewHolder>() {
    class MyListItemView(val mytext: TextView)
    class MyViewHolder(itemView: View, val myListItemView: MyListItemView): RecyclerView.ViewHolder(itemView)

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeawAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val myListItemView = MyListItemView(
            view.findViewById(R.id.my_text)
        )
        return MyViewHolder(view, myListItemView)
    }

    override fun onBindViewHolder(holder: MeawAdapter.MyViewHolder, position: Int) {
        holder.myListItemView.mytext.text = list[position].data
    }
}
