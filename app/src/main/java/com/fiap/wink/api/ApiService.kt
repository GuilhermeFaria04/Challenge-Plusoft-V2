package com.fiap.wink.api

import com.fiap.wink.model.Cadastro
import com.fiap.wink.model.User
import com.fiap.wink.model.Produto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/registro")
    fun registro(@Body cadastro: Cadastro): Call<ResponseBody>

    @POST("login")
    fun login(@Body user: User): Call<ResponseBody>

    @POST("favoritos")
    fun addToFavorites(@Body product: Produto): Call<Void>

    @POST("carrinho")
    fun addToCart(@Body product: Produto): Call<Void>

}


