package com.example.timofeev.shopping_list.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
  fun editShopItem(shopItem: ShopItem) {
    shopListRepository.editShopItem(shopItem)
  }
}