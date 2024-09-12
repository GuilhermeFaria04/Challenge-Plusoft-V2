package com.fiap.wink.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.R

class HeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.headerlayout)

        // Configurações de navegação para os ícones
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
            //val searchText = searchInput.text.toString()
            true
        }
    }
}
