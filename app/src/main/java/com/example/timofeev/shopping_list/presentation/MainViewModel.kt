package com.example.timofeev.shopping_list.presentation

import androidx.lifecycle.ViewModel
import com.example.timofeev.shopping_list.data.ShopListRepositoryImpl
import com.example.timofeev.shopping_list.domain.DeleteShopItemUseCase
import com.example.timofeev.shopping_list.domain.EditShopItemUseCase
import com.example.timofeev.shopping_list.domain.GetShopListUseCase
import com.example.timofeev.shopping_list.domain.ShopItem

class MainViewModel: ViewModel() {
  private val repository = ShopListRepositoryImpl
  private val getShopListUseCase = GetShopListUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)
  private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

  val shopList = getShopListUseCase.getShopList()

  fun deleteShopItem(shopItem: ShopItem) {
    deleteShopItemUseCase.deleteShopItem(shopItem)
  }

  fun changeEnableState(shopItem: ShopItem) {
    val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
    editShopItemUseCase.editShopItem(newShopItem)
  }
}