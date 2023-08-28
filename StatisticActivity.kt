package com.example.catchme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView

class StatisticActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        val rating = arrayListOf<Pair<Int?, String?>>()
        var counter = 1
        while (logins_num!!.contains(counter.toString())) {
            val login = logins_num!!.getString(counter.toString(), "")
            val record = logins_record!!.getInt(counter.toString(), 1000000000)
            val new_elem = record to login

            rating.add(new_elem)
            counter += 1
        }

        var statistic: String = ""
        var index: Int = 0
        rating.sortWith(compareBy({ it.first }, { it.second }))
        var limit = 10
        if (rating.size < 10) {
            limit = rating.size
        }
        while (index < limit) {
            var new_block: String
            if (rating[index].first?.toString() == "1000000000") {
                new_block =
                    (index + 1).toString() + ") " + rating[index].second.toString() + " : -" + '\n'
            } else {
                new_block = (index + 1).toString() + ") " + rating[index].second.toString() + " : " + rating[index].first.toString() + '\n'
            }
            statistic += new_block
            index += 1
        }

        findViewById<TextView>(R.id.statistic_activity_statistic).setText(statistic)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.statistic_activity_back_to_menu -> {
                startActivity(Intent(applicationContext, StartMenuActivity::class.java))
            }
        }
    }
}