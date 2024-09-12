package com.fiap.wink.api

import com.fiap.wink.model.Cadastro
import com.fiap.wink.model.User
import com.fiap.wink.model.Produto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/registro")
    fun registro(@Body cadastro: Cadastro): Call<ResponseBody>

    @POST("login")
    fun login(@Body user: User): Call<ResponseBody>

    @GET("produtos")
    fun getProdutos(): Call<List<Produto>>

}


