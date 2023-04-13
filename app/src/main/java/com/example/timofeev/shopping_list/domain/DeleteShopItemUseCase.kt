package com.example.timofeev.shopping_list.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
  suspend fun deleteShopItem(shopItem: ShopItem) {
    shopListRepository.deleteShopItem(shopItem)
  }
}