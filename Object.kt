package com.example.catchme.com.example.catchme

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.example.catchme.R
import kotlin.math.abs

class Object(new_x: Float, new_y: Float, dist: Int, context: Context, resources: Resources) {

    var bitmap: Bitmap
    var x: Float
    var y: Float
    var num_mack: Int
    var angles = arrayListOf<Pair<Float, Float>>()
    var square: Float
    var distance: Int

    init {
        x = new_x
        y = new_y
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.start_ceil_2)
        num_mack = 1
        distance = dist
        var angle = x + 52F to y + 18F
        angles.add(angle)
        angle = x + 122F to y + 18F
        angles.add(angle)
        angle = x + 158F to y + 58F
        angles.add(angle)
        angle = x + 122F to y + 102F
        angles.add(angle)
        angle = x + 52F to y + 102F
        angles.add(angle)
        angle = x + 11F to y + 58F
        angles.add(angle)

        var first_Gauss_sum: Float = angles[angles.size - 1].first * angles[0].second
        for (i in 0..(angles.size - 2)) {
            first_Gauss_sum += angles[i].first * angles[i + 1].second
        }

        var second_Gausse_sum: Float = angles[0].first * angles[angles.size - 1].second
        for (i in 0..(angles.size - 2)) {
            second_Gausse_sum += angles[i].second * angles[i + 1].first
        }

        square = abs(first_Gauss_sum - second_Gausse_sum) * 0.5F
    }

    fun setNumMask(num: Int, resources: Resources) {
        num_mack = num
        when (num_mack) {
            1 -> {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.start_ceil_2)
            }

            2 -> {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.finish_ceil_2)
            }
        }
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, x, y, Paint())
    }

}