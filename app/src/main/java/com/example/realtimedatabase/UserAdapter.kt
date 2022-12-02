package com.example.realtimedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(var list :MutableList<User>,var onDelete: (User) ->Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    fun setDat(list: MutableList<User>){
        this.list = list
        notifyDataSetChanged()
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    fun onBindViews(user: User){
        val id = itemView.tvId
        val name = itemView.tvName
        id.text = user.id.toString()
        name.text = user.name
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.onBindViews(item)
        holder.itemView.btnDelete.setOnClickListener {
            onDelete(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}