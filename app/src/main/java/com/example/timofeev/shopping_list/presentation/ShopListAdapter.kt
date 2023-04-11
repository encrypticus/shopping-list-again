package com.example.timofeev.shopping_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.timofeev.shopping_list.R
import com.example.timofeev.shopping_list.databinding.ItemShopDisabledBinding
import com.example.timofeev.shopping_list.databinding.ItemShopEnabledBinding
import com.example.timofeev.shopping_list.domain.ShopItem

/**
 * Изменение.
 * Теперь наследуемся не от RecyclerView.Adapter, а от ListAdapter
 */
class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {
  /** Изменение. Класс ShopItemViewHolder вынесен в отдельный файл */

  /**
   * Изменение.
   * Преимущество ListAdapter в том, что он скрывает в себе всю логику работы со списком.
   * Нам больше не нужно хранить ссылку на него самостоятельно. Можно его удалить (закомментировал)
   */
//  var shopList = listOf<ShopItem>()
//    set(value) {
//      val callback = ShopListDiffCallback(shopList, value)
//      val diffResult = DiffUtil.calculateDiff(callback)
//      diffResult.dispatchUpdatesTo(this)
//      field = value
//    }

  var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
  var onShopItemClickListener: ((ShopItem) -> Unit)? = null

  override fun getItemViewType(position: Int): Int {
    val item = getItem(position) /** Изменение  */
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
    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      LayoutInflater.from(parent.context),
      layout,
      parent,
      false
    )
    return ShopItemViewHolder(binding)
  }

  /**
   * Изменение.
   * Этот метод больше не нужен, т.к. работа со списком скрыта внутри класса ListAdapter
   */
//  override fun getItemCount(): Int {
//    return shopList.size
//  }

  override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
    val shopItem = getItem(position) /** Изменение  */
    val binding = holder.binding

    binding.root.setOnLongClickListener {
      onShopItemLongClickListener?.invoke(shopItem)
      true
    }

    binding.root.setOnClickListener {
      onShopItemClickListener?.invoke(shopItem)
    }

    when(binding) {
      is ItemShopDisabledBinding -> {
        binding.shopItem = shopItem
      }
      is ItemShopEnabledBinding -> {
        binding.shopItem = shopItem
      }
    }
  }

  /**
   * Изменение.
   * Нам больше не нужно устанавливать значения по умолчанию, т.к. у нас в методе onBindViewHolder
   * больше нет установки значений по какому-то условию. Мы в любом случае устанавливаем у элементов
   * какой-то текст и слушатели кликов. Поэтому этот метод больше не нужен
   */
//  override fun onViewRecycled(holder: ShopItemViewHolder) {
//    super.onViewRecycled(holder)
//    holder.tvName.text = ""
//    holder.tvCount.text = ""
//    holder.tvName.setTextColor(
//      ContextCompat.getColor(
//        holder.view.context,
//        android.R.color.white
//      )
//    )
//  }

  companion object {
    const val VIEW_TYPE_ENABLED = 1
    const val VIEW_TYPE_DISABLED = 0

    const val MAX_POOL_SIZE = 15
  }
}