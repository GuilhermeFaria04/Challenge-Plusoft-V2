package com.fiap.wink.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.wink.api.ApiService
import com.fiap.wink.model.Produto
import com.fiap.wink.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    private val produtosEletronicos = listOf(
        Produto("Smartphone Apple iPhone 13 256GB 5G Tela 6.1\" Vermelho", "de R$ 8.999,00", "Por R$ 4.589,00 no PIX", R.drawable.Challengecelular),
        Produto("Smart TV LG 55\" NanoCell 4K UHD WebOS 23 ThinQ AI 55NANO77SRA", "de R$ 4.999,00", "Por R$ 2.999,00 no PIX", R.drawable.Challengetv)
    )

    private val produtosDomesticos = listOf(
        Produto("Fritadeira Airfryer Electrolux Family Efficient EAF51 5L 1700W 127V Vermelho", "de R$ 699,00", "Por R$ 399,00 no PIX", R.drawable.Challengefritadeira)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homelayout)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create a simple inline adapter
        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            inner class ProductViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
                val image = itemView.findViewById<android.widget.ImageView>(R.id.ProdutoImage)
                val title = itemView.findViewById<android.widget.TextView>(R.id.ProdutoTitle)
                val subtitle = itemView.findViewById<android.widget.TextView>(R.id.ProdutoSubtitle)
                val price = itemView.findViewById<android.widget.TextView>(R.id.ProdutoPrice)
                val favoriteIcon = itemView.findViewById<android.widget.ImageView>(R.id.iconFavoritar)
                val cartIcon = itemView.findViewById<android.widget.ImageView>(R.id.iconCarrinho)
            }

            override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(R.layout.item_Produto, parent, false)
                return ProductViewHolder(view)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val product = (produtosEletronicos + produtosDomesticos)[position]
                val viewHolder = holder as ProductViewHolder
                viewHolder.image.setImageResource(product.imageResId)
                viewHolder.title.text = product.title
                viewHolder.subtitle.text = product.subtitle
                viewHolder.price.text = product.price

                viewHolder.favoriteIcon.setOnClickListener {
                    apiService.addToFavorites(product).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@HomeActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@HomeActivity, "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                viewHolder.cartIcon.setOnClickListener {
                    apiService.addToCart(product).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@HomeActivity, "Added to cart", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@HomeActivity, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

            override fun getItemCount(): Int {
                return (produtosEletronicos + produtosDomesticos).size
            }
        }

        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        val chatBotButton = findViewById<android.widget.ImageView>(R.id.chatBot)
        chatBotButton.setOnClickListener {
            // Handle chat bot click
            startActivity(Intent(this, ChatBotActivity::class.java))
        }
    }
}
