package com.example.timofeev.shopping_list.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.timofeev.shopping_list.data.ShopListRepositoryImpl
import com.example.timofeev.shopping_list.domain.DeleteShopItemUseCase
import com.example.timofeev.shopping_list.domain.EditShopItemUseCase
import com.example.timofeev.shopping_list.domain.GetShopListUseCase
import com.example.timofeev.shopping_list.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = ShopListRepositoryImpl(application)

  private val getShopListUseCase = GetShopListUseCase(repository)
  private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)

  val shopList = getShopListUseCase.getShopList()

  fun deleteShopItem(shopItem: ShopItem) {
    viewModelScope.launch {
      deleteShopItemUseCase.deleteShopItem(shopItem)
    }
  }

  fun changeEnableState(shopItem: ShopItem) {
    val newItem = shopItem.copy(enabled = !shopItem.enabled)
    viewModelScope.launch {
      editShopItemUseCase.editShopItem(newItem)
    }
  }
}