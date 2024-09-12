package com.fiap.wink.activity

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.fiap.wink.R
import com.fiap.wink.model.ItemCarrinho

class CarrinhoActivity : Activity() {

    private val itensCarrinho = mutableListOf<ItemCarrinho>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carrinholayout)

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
}
