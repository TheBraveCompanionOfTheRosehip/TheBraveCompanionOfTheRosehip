package com.example.catchme.com.example.catchme

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.example.catchme.R

class Abobka(new_x: Float, new_y: Float, context: Context, resources: Resources) {

    var bitmap: Bitmap
    var x: Float
    var y: Float
    var num_mack: Int
    var num_ceil: Int
    var last_num_ceil: Int

    init {
        x = new_x
        y = new_y
        num_mack = 1
        num_ceil = 38
        last_num_ceil = 38
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.abobka)
    }

    fun setNumMask(num: Int, resources: Resources) {
        num_mack = num
        when (num_mack) {
            1 -> {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.abobka)
            }

            2 -> {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.abobka_2)
            }

            3 -> {
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.abobka_3)
            }
        }
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, x, y, Paint())
    }
}