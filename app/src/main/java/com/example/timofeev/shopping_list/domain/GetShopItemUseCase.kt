package com.example.timofeev.shopping_list.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
  fun getShopItem(shopItemId: Int): ShopItem {
    return shopListRepository.getShopItem(shopItemId)
  }
}