package com.example.newsincardview

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var log: TextView
    private lateinit var register: TextView
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log = findViewById(R.id.log)
        register = findViewById(R.id.reg)
        auth = Firebase.auth
        call()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        log.setOnClickListener {
            log.background = getDrawable(R.drawable.leftcorner)
            loadfrag(LogInFragment())
            register.background = getDrawable(R.drawable.rightwhit)
        }
        register.setOnClickListener {
            register.background = getDrawable(R.drawable.rightcorner)
            loadfrag(RegisterFragment())
            log.background = getDrawable(R.drawable.leftwhite)
        }
        if (auth.currentUser !=null){
            startActivity(Intent(this,NewsClass::class.java))
            finish()
        }


    }

    private fun call() {
        log.background = getDrawable(R.drawable.leftcorner)
        loadfrag(LogInFragment())
        register.background = getDrawable(R.drawable.rightwhit)

    }

    fun loadfrag(frag1: LogInFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, frag1)
        ft.commit()
    }

    fun loadfrag(frag2: RegisterFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame, frag2)
        ft.commit()
    }



}
