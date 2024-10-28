package com.fiap.wink.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiap.wink.R
import com.fiap.wink.model.User

import com.fiap.wink.api.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    private val sharedPreferences by lazy {
        getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginlayout)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerLink = findViewById(R.id.textRegisterLink)

        loadUserData()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(email, password)
                loginUser(user)
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun loginUser(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    saveUserData(user.email, user.password)

                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "Erro ao realizar login!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erro na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
                t.printStackTrace() // Para mais detalhes no logcat
            }
        })
    }

    private fun saveUserData(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.putString("user_password", password)
        editor.apply() // Aplicar as mudanças
    }

    private fun loadUserData() {
        val email = sharedPreferences.getString("user_email", "")
        val password = sharedPreferences.getString("user_password", "")

        emailEditText.setText(email)
        passwordEditText.setText(password)
    }
}
