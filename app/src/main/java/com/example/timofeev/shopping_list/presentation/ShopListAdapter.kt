package com.example.timofeev.shopping_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.timofeev.shopping_list.R
import com.example.timofeev.shopping_list.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
  class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)
  }

  var count = 0;
  var shopList = listOf<ShopItem>()
    set(value) {
      val callback = ShopListDiffCallback(shopList, value)
      val diffResult = DiffUtil.calculateDiff(callback)
      diffResult.dispatchUpdatesTo(this)
      field = value
    }

  var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
  var onShopItemClickListener: ((ShopItem) -> Unit)? = null

  override fun getItemViewType(position: Int): Int {
    val item = shopList[position]
    return if (item.enabled) {
      VIEW_TYPE_ENABLED
    } else {
      VIEW_TYPE_DISABLED
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
    val layout = when(viewType) {
      VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
      VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
      else -> throw RuntimeException("Unknown view type: $viewType")
    }
    val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
    return ShopItemViewHolder(view)
  }

  override fun getItemCount(): Int {
    return shopList.size
  }

  override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
    Log.d("ShopListAdapter", "onCreateViewHolder, count ${++count}")
    val shopItem = shopList[position]

    holder.view.setOnLongClickListener {
      onShopItemLongClickListener?.invoke(shopItem)
      true
    }

    holder.view.setOnClickListener {
      onShopItemClickListener?.invoke(shopItem)
    }

    holder.tvName.text = shopItem.name
    holder.tvCount.text = shopItem.count.toString()
  }

  override fun onViewRecycled(holder: ShopItemViewHolder) {
    super.onViewRecycled(holder)
    holder.tvName.text = ""
    holder.tvCount.text = ""
    holder.tvName.setTextColor(
      ContextCompat.getColor(
        holder.view.context,
        android.R.color.white
      )
    )
  }

  companion object {
    const val VIEW_TYPE_ENABLED = 1
    const val VIEW_TYPE_DISABLED = 0

    const val MAX_POOL_SIZE = 15
  }
}