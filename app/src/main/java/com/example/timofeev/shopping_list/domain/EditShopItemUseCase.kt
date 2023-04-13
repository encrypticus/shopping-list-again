package com.example.timofeev.shopping_list.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
  suspend fun editShopItem(shopItem: ShopItem) {
    shopListRepository.editShopItem(shopItem)
  }
}