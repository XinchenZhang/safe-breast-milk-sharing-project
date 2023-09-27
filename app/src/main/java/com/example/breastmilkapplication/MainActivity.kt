package com.example.breastmilkapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnSignup : Button
    private lateinit var btnChat : Button
    private lateinit var btnProfile : Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var txtLoginStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)
        btnChat = findViewById(R.id.btn_chat)
        btnProfile = findViewById(R.id.btn_profile)
        mAuth = FirebaseAuth.getInstance()
        txtLoginStatus = findViewById(R.id.txt_login_status)
        if(mAuth.currentUser == null) {
            txtLoginStatus.text = "Not logged in"
        }else{
            txtLoginStatus.text = "Logged in as: ${mAuth.currentUser?.email}"
        }
        btnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
        btnSignup.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)

        }

        btnChat.setOnClickListener{
            if (mAuth.currentUser == null){
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)
            }

        }

        btnProfile.setOnClickListener{
            if (mAuth.currentUser == null){
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return true
    }
}