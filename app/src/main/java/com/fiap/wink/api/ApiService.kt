package com.fiap.wink.api

import com.fiap.wink.model.Cadastro
import com.fiap.wink.model.Favoritos
import com.fiap.wink.model.User
import com.fiap.wink.model.Produto
import com.fiap.wink.model.ItemCarrinho
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("/cadastro")
    fun registro(@Body cadastro: Cadastro): Call<ResponseBody>

    @GET("/editcadastro/{id}")
    fun getUser(@Path("id") id: String): Call<Cadastro>

    @PUT("/editcadastro/{id}")
    fun updateUser(@Path("id") id: String, @Body cadastro: Cadastro): Call<ResponseBody>

    @DELETE("/editcadastro/{id}")
    fun deleteUser(@Path("id") id: String): Call<ResponseBody>

    @POST("login")
    fun login(@Body user: User): Call<ResponseBody>

    @GET("produtos")
    fun getProdutos(): Call<List<Produto>>

    @POST("favoritos")
    fun addFavorito(@Body favorito: Favoritos): Call<ResponseBody>

    @DELETE("favoritos/{id}")
    fun removeFavorito(@Path("id") id: String): Call<ResponseBody>

    @GET("favoritos")
    fun getFavoritos(): Call<List<Favoritos>>

    @POST("carrinho")
    fun addItemCarrinho(@Body item: ItemCarrinho): Call<ResponseBody>

    @DELETE("carrinho/{id}")
    fun removeItemCarrinho(@Path("id") id: String): Call<ResponseBody>

    @GET("carrinho")
    fun getCarrinho(): Call<List<ItemCarrinho>>

}


