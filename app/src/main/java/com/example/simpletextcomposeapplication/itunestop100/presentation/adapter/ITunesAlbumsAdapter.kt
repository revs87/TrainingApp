package com.example.simpletextcomposeapplication.itunestop100.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletextcomposeapplication.R
import com.example.simpletextcomposeapplication.itunestop100.domain.model.ITunesAlbum

class ITunesAlbumsAdapter: RecyclerView.Adapter<ITunesAlbumsAdapter.ITunesAlbumsViewHolder>() {
    class ITunesAlbumsViewHolder(itemView: View, val myListItemView: ITunesAlbumsItemView): RecyclerView.ViewHolder(itemView)
    class ITunesAlbumsItemView(val mytext: TextView)

    private var list: List<ITunesAlbum> = emptyList()

    fun submitList(list: List<ITunesAlbum>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITunesAlbumsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val myListItemView = ITunesAlbumsItemView(view.findViewById(R.id.my_text))
        return ITunesAlbumsViewHolder(view, myListItemView)
    }

    override fun onBindViewHolder(holder: ITunesAlbumsViewHolder, position: Int) {
        holder.myListItemView.mytext.text = list[position].name
    }

}
