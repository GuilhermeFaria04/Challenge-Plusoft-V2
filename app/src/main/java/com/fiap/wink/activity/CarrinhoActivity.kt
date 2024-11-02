package com.fiap.wink.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.fiap.wink.R
import com.fiap.wink.model.ItemCarrinho

class CarrinhoActivity : Activity() {

    private val itensCarrinho = mutableListOf<ItemCarrinho>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrinholayout)

        setupHeader()

        itensCarrinho.add(ItemCarrinho("Smartphone Apple iPhone 13", 1, "$999.99"))
        itensCarrinho.add(ItemCarrinho("Smart TV LG 55\"", 2, "$1,499.98"))
        itensCarrinho.add(ItemCarrinho("Fritadeira Airfryer", 1, "$299.99"))

        renderItensCarrinho()
    }

    @SuppressLint("MissingInflatedId", "DefaultLocale")
    private fun renderItensCarrinho() {
        val container = findViewById<LinearLayout>(R.id.cartContainer)
        container.removeAllViews()

        for (item in itensCarrinho) {
            val itemView = layoutInflater.inflate(R.layout.item_produto, container, false)

            val titleTextView = itemView.findViewById<TextView>(R.id.productTitle1)
            val quantityTextView = itemView.findViewById<TextView>(R.id.productSubtitle1)
            val btnIncreaseQuantity = itemView.findViewById<ImageView>(R.id.btnIncreaseQuantity)
            val btnDecreaseQuantity = itemView.findViewById<ImageView>(R.id.btnDecreaseQuantity)
            val btnRemoveItem = itemView.findViewById<ImageView>(R.id.btnRemoveItem)

            titleTextView.text = item.title
            quantityTextView.text = item.quantity.toString()


            btnIncreaseQuantity.setOnClickListener {
                item.quantity++
                renderItensCarrinho()
            }


            btnDecreaseQuantity.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    renderItensCarrinho()
                }
            }

            btnRemoveItem.setOnClickListener {
                itensCarrinho.remove(item)
                renderItensCarrinho()
            }

            container.addView(itemView)
        }
    }

    private fun setupHeader() {
        val userIcon = findViewById<ImageView>(R.id.userIcon)
        val loginIcon = findViewById<ImageView>(R.id.loginIcon)
        val favoriteIcon = findViewById<ImageView>(R.id.favoriteIcon)
        val cartIcon = findViewById<ImageView>(R.id.cartIcon)
        val searchInput = findViewById<EditText>(R.id.pesquisaInput)

        val logo = findViewById<TextView>(R.id.logo)
        logo.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

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

        searchInput.setOnEditorActionListener { _, _, _ ->
            true
        }
    }
}
