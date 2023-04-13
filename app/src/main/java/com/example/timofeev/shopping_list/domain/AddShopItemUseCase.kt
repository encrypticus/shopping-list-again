package com.example.timofeev.shopping_list.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
  suspend fun addShopItem(shopItem: ShopItem) {
    shopListRepository.addShopItem(shopItem)
  }
}