package com.example.catchme

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text
import java.lang.Math.min

open class MainActivity : AppCompatActivity(), View.OnClickListener {

    var accounts: SharedPreferences? = null
    var logins_num: SharedPreferences? = null
    var logins_record: SharedPreferences? = null
    var curr_account_login: SharedPreferences? = null
    var curr_account_record: SharedPreferences? = null
    var curr_account_number: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        accounts = getSharedPreferences("Accounts", Context.MODE_PRIVATE)
        logins_num = getSharedPreferences("Logins", Context.MODE_PRIVATE)
        logins_record = getSharedPreferences("Records", Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.autorization_activity_button_to_enter -> {
                val login = findViewById<EditText>(R.id.autorization_activity_login).text
                val password = findViewById<EditText>(R.id.autorization_activity_password).text
                if (login.isEmpty()) {
                    findViewById<TextView>(R.id.autorization_activity_error).setText("You didn't enter login")
                } else if (password.isEmpty()) {
                    findViewById<TextView>(R.id.autorization_activity_error).setText("You didn't enter password")
                } else if (!accounts!!.contains(login.toString())) {
                    findViewById<TextView>(R.id.autorization_activity_error).setText("Account with this login isn't exist")
                } else if (accounts?.getString(login.toString(), "") != password.toString()) {
                    findViewById<TextView>(R.id.autorization_activity_error).setText("Invalid Password")
                } else {
                    var counter = 1
                    var curr_num = 1
                    var curr_record = 1000000000
                    while (logins_num!!.contains(counter.toString())) {
                        if (logins_num?.getString(counter.toString(), "") == login.toString()) {
                            curr_record = min(
                                curr_record,
                                logins_record!!.getInt(counter.toString(), 1000000000)
                            )
                            curr_num = counter
                            break
                        }
                        counter += 1
                    }
                    val curr_account_login_editor = curr_account_login?.edit()
                    curr_account_login_editor?.putString("curr_login", login.toString())
                    curr_account_login_editor?.apply()

                    val curr_account_record_editor = curr_account_record?.edit()
                    record = curr_record
                    curr_account_record_editor?.putInt("curr_record", curr_record)
                    curr_account_record_editor?.apply()

                    val curr_account_number_editor = curr_account_number?.edit()
                    curr_number_account = curr_num
                    curr_account_number_editor?.putInt("curr_num", curr_num)
                    curr_account_number_editor?.apply()

                    startActivity(Intent(applicationContext, StartMenuActivity::class.java))
                }
            }

            R.id.autorization_activity_button_to_register -> {
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
            }
        }
    }


}