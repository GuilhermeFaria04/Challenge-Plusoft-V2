package com.fiap.wink.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.R
import com.fiap.wink.api.ApiService
import com.fiap.wink.model.Cadastro
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditCadastroActivity : AppCompatActivity() {

    private lateinit var nomeEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var telefoneEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmarSenhaEditText: EditText
    private lateinit var atualizarButton: Button
    private lateinit var deletarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_cadastrolayout)

        nomeEditText = findViewById(R.id.editTextNome)
        emailEditText = findViewById(R.id.editTextEmail)
        telefoneEditText = findViewById(R.id.editTextTelefone)
        senhaEditText = findViewById(R.id.editTextSenha)
        confirmarSenhaEditText = findViewById(R.id.editTextConfirmarSenha)
        atualizarButton = findViewById(R.id.buttonAtualizar)
        deletarButton = findViewById(R.id.buttonDeletar)


        val userId = "12345"

        getUserDetails(userId)

        atualizarButton.setOnClickListener {
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
            updateUser(userId, cadastro)
        }

        deletarButton.setOnClickListener {
            deleteUser(userId)
        }
    }

    private fun getUserDetails(userId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getUser(userId).enqueue(object : Callback<Cadastro> {
            override fun onResponse(call: Call<Cadastro>, response: Response<Cadastro>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    nomeEditText.setText(user?.nome)
                    emailEditText.setText(user?.email)
                    telefoneEditText.setText(user?.telefone)
                } else {
                    Toast.makeText(this@EditCadastroActivity, "Erro ao buscar dados!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cadastro>, t: Throwable) {
                Toast.makeText(this@EditCadastroActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser(userId: String, cadastro: Cadastro) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.updateUser(userId, cadastro).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditCadastroActivity, "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@EditCadastroActivity, "Erro ao atualizar cadastro!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EditCadastroActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(userId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.deleteUser(userId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditCadastroActivity, "Cadastro deletado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditCadastroActivity, "Erro ao deletar cadastro!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@EditCadastroActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
