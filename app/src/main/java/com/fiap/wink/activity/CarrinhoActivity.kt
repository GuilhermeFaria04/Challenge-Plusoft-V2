package com.fiap.wink.activity

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

        // Configurações do cabeçalho
        setupHeader()

        // Simulação de itens no carrinho
        itensCarrinho.add(ItemCarrinho("Smartphone Apple iPhone 13", 1, "$999.99"))
        itensCarrinho.add(ItemCarrinho("Smart TV LG 55\"", 2, "$1,499.98"))
        itensCarrinho.add(ItemCarrinho("Fritadeira Airfryer", 1, "$299.99"))

        // Renderiza os itens simulados
        renderItensCarrinho()
    }

    private fun renderItensCarrinho() {
        val container = findViewById<LinearLayout>(R.id.cartContainer)

        for (item in itensCarrinho) {
            val itemView = layoutInflater.inflate(R.layout.item_produto, container, false)

            val titleTextView = itemView.findViewById<TextView>(R.id.productTitle1)
            val quantityTextView = itemView.findViewById<TextView>(R.id.productSubtitle1)
            val totalTextView = itemView.findViewById<TextView>(R.id.productPrice1)

            titleTextView.text = item.title
            quantityTextView.text = item.quantity.toString()
            totalTextView.text = item.total

            container.addView(itemView)
        }
    }

    private fun setupHeader() {
        // Configurações de navegação para os ícones do cabeçalho
        val userIcon = findViewById<ImageView>(R.id.userIcon)
        val loginIcon = findViewById<ImageView>(R.id.loginIcon)
        val favoriteIcon = findViewById<ImageView>(R.id.favoriteIcon)
        val cartIcon = findViewById<ImageView>(R.id.cartIcon)
        val searchInput = findViewById<EditText>(R.id.pesquisaInput)

        // Logo click (voltar para Home)
        val logo = findViewById<TextView>(R.id.logo)
        logo.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java)) // Navega para a Home
        }

        // User icon click (vai para Cadastro)
        userIcon.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        // Login icon click (vai para Login)
        loginIcon.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Favorite icon click (vai para Favoritos)
        favoriteIcon.setOnClickListener {
            startActivity(Intent(this, FavoritosActivity::class.java))
        }

        // Cart icon click (vai para Carrinho)
        cartIcon.setOnClickListener {
            startActivity(Intent(this, CarrinhoActivity::class.java))
        }

        // Barra de pesquisa - aqui você pode configurar a lógica de busca
        searchInput.setOnEditorActionListener { _, _, _ ->
            true
        }
    }
}
