package com.example.timofeev.shopping_list.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.timofeev.shopping_list.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
  private lateinit var viewModel: MainViewModel
  private lateinit var shopListAdapter: ShopListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupRecyclerView()
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    viewModel.shopList.observe(this) {
      /** Изменение */
      shopListAdapter.submitList(it)
    }

    val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
    buttonAddItem.setOnClickListener {
      startActivity(ShopItemActivity.newIntentAddItem(this))
    }
  }

  private fun setupRecyclerView() {
    val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
    with(rvShopList) {
      shopListAdapter = ShopListAdapter()
      adapter = shopListAdapter
      recycledViewPool.setMaxRecycledViews(
        ShopListAdapter.VIEW_TYPE_ENABLED,
        ShopListAdapter.MAX_POOL_SIZE
      )
      recycledViewPool.setMaxRecycledViews(
        ShopListAdapter.VIEW_TYPE_DISABLED,
        ShopListAdapter.MAX_POOL_SIZE
      )
    }

    setupLongClickListener()
    setupClickListener()
    setupSwipeListener(rvShopList)
  }

  private fun setupSwipeListener(rvShopList: RecyclerView) {
    val callback = object : ItemTouchHelper.SimpleCallback(
      0,
      ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        /** Изменение */
        val item = shopListAdapter.currentList[viewHolder.adapterPosition]
        viewModel.deleteShopItem(item)
      }
    }
    val itemTouchHelper = ItemTouchHelper(callback)
    itemTouchHelper.attachToRecyclerView(rvShopList)
  }

  private fun setupClickListener() {
    shopListAdapter.onShopItemClickListener = {
      startActivity(ShopItemActivity.newIntentEditItem(this, it.id))
    }
  }

  private fun setupLongClickListener() {
    shopListAdapter.onShopItemLongClickListener = {
      viewModel.changeEnableState(it)
    }
  }
}