package com.fiap.wink.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.api.ApiService
import com.fiap.wink.model.Cadastro
import com.fiap.wink.R
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private lateinit var nomeEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var telefoneEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmarSenhaEditText: EditText
    private lateinit var cadastroButton: Button
    private lateinit var loginLink: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastrolayout)


        nomeEditText = findViewById(R.id.editTextNome)
        emailEditText = findViewById(R.id.editTextEmail)
        telefoneEditText = findViewById(R.id.editTextTelefone)
        senhaEditText = findViewById(R.id.editTextSenha)
        confirmarSenhaEditText = findViewById(R.id.editTextConfirmarSenha)
        cadastroButton = findViewById(R.id.buttonCadastrar)
        loginLink = findViewById(R.id.textLoginLink)

        cadastroButton.setOnClickListener {
            val nome = nomeEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val telefone = telefoneEditText.text.toString().trim()
            val senha = senhaEditText.text.toString().trim()
            val confirmarSenha = confirmarSenhaEditText.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha) {
                Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cadastro = Cadastro(nome, email, telefone, senha)
            registerUser(cadastro)
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(cadastro: Cadastro) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.registro(cadastro).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CadastroActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CadastroActivity, "Erro ao realizar cadastro!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@CadastroActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace() // Para mais detalhes no logcat
            }
        })
    }
}