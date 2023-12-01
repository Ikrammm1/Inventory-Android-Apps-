package com.example.e_inventory.UI.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.e_inventory.R
import com.example.e_inventory.UI.Beranda.BerandaActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val SignIn = findViewById<Button>(R.id.btnSignIn)

        SignIn.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }
    }
}
