package com.example.catchme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.lang.Integer.max

class GameActivity : MainActivity(), View.OnClickListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        score = 0
        findViewById<TextView>(R.id.game_activity_score).setText("Score: 0")
        findViewById<TextView>(R.id.game_activity_record).setText("Record:" + record.toString())
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.game_activity_back_to_menu -> {
                startActivity(Intent(applicationContext, StartMenuActivity::class.java))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        findViewById<TextView>(R.id.game_activity_score).setText("Score: " + score.toString())
        if (!in_processing) {
            if (is_win) {
                was_show = false
                startActivity(Intent(applicationContext, WinActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, LoseActivity::class.java))
            }
        }
        return super.onTouchEvent(event)
    }
}