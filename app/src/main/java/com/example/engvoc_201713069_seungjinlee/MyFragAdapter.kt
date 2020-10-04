package com.example.engvoc_201713069_seungjinlee


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyFragAdapter(val items:ArrayList<String>, val items2:ArrayList<String>)
    : RecyclerView.Adapter<MyFragAdapter.MyViewHolder>()
{
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, data:String, position: Int)
    }

    var ItemClickListener:OnItemClickListener ?= null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView:TextView = itemView.findViewById(R.id.textView)
        var textView2:TextView = itemView.findViewById(R.id.textView2)
        init{
            itemView.setOnClickListener {
                ItemClickListener?.OnItemClick(this, it,
                    items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = items[position]
        holder.textView2.text = items2[position]
    }

    fun moveItem(oldPos:Int, newPos:Int){
        val item = items.get(oldPos)
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
}
