package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val tvLoginTitle: TextView = findViewById(R.id.tvLoginTitle)
        val etId: EditText = findViewById(R.id.etId)
        val etPW: EditText = findViewById(R.id.etPW)
        val tvFindId: TextView = findViewById(R.id.tvFindId)
        val tvFindPW: TextView = findViewById(R.id.tvFindPW)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val userId = etId.text.toString().trim()
            val password = etPW.text.toString().trim()

            if (userId.isNotEmpty() && password.isNotEmpty()) {
                login(userId, password)
            } else {
                showErrorMessage("아이디와 비밀번호를 입력하세요.")
            }
        }

        btnRegister.setOnClickListener {
            navigateToRegister()
        }

        etId.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank()) {
                etId.error = "아이디를 입력하세요."
            }
        }

        etPW.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank()) {
                etPW.error = "비밀번호를 입력하세요."
            }
        }
    }

    private fun login(userId: String, password: String) {
        auth.signInWithEmailAndPassword(userId, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공, 메인 화면으로 이동
                    navigateToMainActivity()
                } else {
                    // 로그인 실패
                    showErrorMessage("로그인 실패: ${task.exception?.message}")
                }
            }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        // 메인 화면으로 이동하는 로직
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        // 회원가입 화면으로 이동
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
