package com.example.catchme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DescriptionActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.description_activity_back_to_menu -> {
                startActivity(Intent(applicationContext, StartMenuActivity::class.java))
            }
        }
    }
}