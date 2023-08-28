package com.example.catchme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class WinActivity : MainActivity() {
    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        if (record > score) {
            record = score
            was_show = true
            val editor_records = logins_record?.edit()
            editor_records?.putInt(curr_number_account.toString(), record)
            editor_records?.apply()
        }
        findViewById<TextView>(R.id.win_activity_statistic).text =
            "Congratulations, you managed to save the world!"
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.win_activity_back_to_menu -> {
                startActivity(Intent(applicationContext, StartMenuActivity::class.java))
            }
        }
    }
}