package com.fiap.wink.model

data class Produto(
    val title: String,
    val subtitle: String,
    val price: String,
    val imageResId: Int,
    val iconFavoritarId: Int,
    val iconCarrinhoId: Int
)
