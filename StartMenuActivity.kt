package com.example.catchme

import android.content.Intent
import android.os.Bundle
import android.view.View

class StartMenuActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.start_menu_activity_play -> {
                score = 0
                is_win = true
                in_processing = true
                startActivity(Intent(applicationContext, GameActivity::class.java))
            }
            R.id.start_menu_activity_description -> {
                startActivity(Intent(applicationContext, DescriptionActivity::class.java))
            }
            R.id.start_menu_activity_statistic -> {
                startActivity(Intent(applicationContext, StatisticActivity::class.java))
            }
            R.id.start_menu_activity_exit -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }
    }
}