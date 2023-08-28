package com.example.catchme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SignUpActivity : MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.sign_up_activity_button_to_enter -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

            R.id.sign_up_activity_button_to_register -> {
                val login = findViewById<EditText>(R.id.sign_up_activity_login).text
                val password = findViewById<EditText>(R.id.sign_up_activity_password).text
                val password2 = findViewById<EditText>(R.id.sign_up_activity_password2).text
                if (login.isEmpty()) {
                    findViewById<TextView>(R.id.sign_up_activity_error).setText("You didn't enter login")
                } else if (accounts!!.contains(login.toString())) {
                    findViewById<TextView>(R.id.sign_up_activity_error).setText("Account with this login already exist")
                } else if (password.isEmpty()) {
                    findViewById<TextView>(R.id.sign_up_activity_error).setText("You didn't enter password")
                } else if (password2.isEmpty()) {
                    findViewById<TextView>(R.id.sign_up_activity_error).setText("You didn't repeat password")
                } else if (password.toString() != password2.toString()) {
                    findViewById<TextView>(R.id.sign_up_activity_error).setText("Passwords are different")
                } else {
                    val editor = accounts?.edit()
                    editor?.putString(login.toString(), password.toString())
                    editor?.apply()

                    val editor_logins = logins_num?.edit()
                    val editor_records = logins_record?.edit()
                    var counter = 1
                    while (logins_num!!.contains(counter.toString())) {
                        counter += 1
                    }
                    editor_logins?.putString(counter.toString(), login.toString())
                    editor_logins?.apply()
                    editor_records?.putInt(counter.toString(), 1000000000)
                    editor_records?.apply()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            }
        }
    }
}