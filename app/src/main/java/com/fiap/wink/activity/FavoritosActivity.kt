package com.fiap.wink.activity

import android.annotation.SuppressLint
import android.os.Bundle
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
                imageResId = R.drawable.celular // Usar uma imagem local para simulação
            )
        )
        produtos.add(
            Favoritos(
                title = "Fritadeira Airfryer",
                imageResId = R.drawable.celular // Usar uma imagem local para simulação
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
}
