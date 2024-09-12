package com.fiap.wink.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.R
import com.fiap.wink.model.Favoritos

class FavoritosActivity : AppCompatActivity() {

    private val produtos = mutableListOf<Favoritos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritoslayout)

        // Configurações do cabeçalho
        setupHeader()

        // Simulação de produtos favoritos
        produtos.add(
            Favoritos(
                title = "Smartphone Apple iPhone 13",
                imageResId = R.drawable.celular
            )
        )
        produtos.add(
            Favoritos(
                title = "Smart TV LG 55\"",
                imageResId = R.drawable.tv
            )
        )
        produtos.add(
            Favoritos(
                title = "Fritadeira Airfryer",
                imageResId = R.drawable.fritadeira
            )
        )

        // Renderiza os produtos simulados
        renderProdutos()
    }

    @SuppressLint("InflateParams")
    private fun renderProdutos() {
        val container = findViewById<LinearLayout>(R.id.produtosContainer)

        for (produto in produtos) {
            val itemView = layoutInflater.inflate(R.layout.item_produto, null)

            val titleTextView = itemView.findViewById<TextView>(R.id.productTitle1)
            val imageView = itemView.findViewById<ImageView>(R.id.productImage1)

            titleTextView.text = produto.title
            imageView.setImageResource(produto.imageResId) // Definindo a imagem local

            // Ao clicar no ícone de favoritos, remover dos favoritos
            itemView.findViewById<ImageView>(R.id.iconFavoritar1).setOnClickListener {
                Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show()
            }

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
