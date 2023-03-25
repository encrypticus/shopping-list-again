package com.example.timofeev.shopping_list.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timofeev.shopping_list.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
  val tvName = view.findViewById<TextView>(R.id.tv_name)
  val tvCount = view.findViewById<TextView>(R.id.tv_count)
}