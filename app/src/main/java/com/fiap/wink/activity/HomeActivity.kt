package com.fiap.wink.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.R
import com.fiap.wink.api.ApiService
import com.fiap.wink.model.Produto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private val produtosFavoritos = mutableSetOf<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homelayout)

        setupHeader()
        fetchProdutos()
    }

    private fun setupHeader() {
        val userIcon = findViewById<ImageView>(R.id.userIcon)
        val loginIcon = findViewById<ImageView>(R.id.loginIcon)
        val favoriteIcon = findViewById<ImageView>(R.id.favoriteIcon)
        val cartIcon = findViewById<ImageView>(R.id.cartIcon)
        val searchInput = findViewById<TextView>(R.id.pesquisaInput)

        userIcon.setOnClickListener {
            startActivity(Intent(this, EditCadastroActivity::class.java))
        }

        loginIcon.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        favoriteIcon.setOnClickListener {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }

        cartIcon.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }

        searchInput.setOnEditorActionListener { _, _, _ -> true }
    }

    private fun fetchProdutos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getProdutos().enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val produtos = response.body() ?: emptyList()
                    displayProdutos(produtos)
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun displayProdutos(produtos: List<Produto>) {
        if (produtos.isNotEmpty()) {
            val produto = produtos[0]

            val productImage1 = findViewById<ImageView>(R.id.productImage1)
            val productTitle1 = findViewById<TextView>(R.id.productTitle1)
            val productSubtitle1 = findViewById<TextView>(R.id.productSubtitle1)
            val productPrice1 = findViewById<TextView>(R.id.productPrice1)
            val favoriteIcon1 = findViewById<ImageView>(R.id.iconFavoritar1)
            val cartIcon1 = findViewById<ImageView>(R.id.iconCarrinho1)

            productImage1.setImageResource(produto.imageResId)
            productTitle1.text = produto.title
            productSubtitle1.text = produto.subtitle
            productPrice1.text = produto.price

            favoriteIcon1.setOnClickListener {
                if (produtosFavoritos.contains(produto)) {
                    produtosFavoritos.remove(produto)
                    favoriteIcon1.setImageResource(R.drawable.favorito)
                    showToast("Removido dos Favoritos")
                } else {
                    produtosFavoritos.add(produto)
                    favoriteIcon1.setImageResource(R.drawable.favorito)
                    showToast("Adicionado aos Favoritos")
                }
            }

            cartIcon1.setOnClickListener {
                showToast("Adicionado ao carrinho")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
