package com.example.catchme

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.os.SystemClock.sleep
import android.view.View
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.sqrt
import com.example.catchme.com.example.catchme.Abobka
import com.example.catchme.com.example.catchme.Object
import kotlin.math.min

var score: Int = 0
var is_win: Boolean = true
var in_processing: Boolean = true
var record: Int = 1000000000
var curr_number_account: Int = -1
var was_show = false

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var game_field = arrayListOf<Object>()
    val nums = (0..76).toMutableList()
    val bushes = nums.shuffled()
    val x_delta: Float = 230F
    val y_delta: Float = 50F
    var ceil: Object =
        Object(0F, 500F, 1, getContext(), resources)

    var abobka = Abobka(458F, 800F, getContext(), resources)

    init {
        while (game_field.size < 5) {
            game_field.add(
                Object(
                    ceil.x + x_delta * game_field.size,
                    ceil.y,
                    1,
                    getContext(),
                    resources
                )
            )
        }
        var separator = 9
        var shift = 0
        while (separator <= 72) {
            shift = 0
            var x = x_delta / 2
            var y = game_field[game_field.size - 5].y + y_delta
            while (game_field.size < separator) {
                game_field.add(
                    Object(
                        x + x_delta * shift,
                        y,
                        min(separator / 9, min((shift + 1) * 2, (4 - shift) * 2)),
                        getContext(),
                        resources
                    )
                )
                shift += 1
            }
            shift = 0
            separator += 5
            x = 0F
            y += y_delta
            while (game_field.size < separator) {
                game_field.add(
                    Object(
                        x + x_delta * shift,
                        y,
                        min((separator + 4) / 9, 1 + min(shift * 2, (4 - shift) * 2)),
                        getContext(),
                        resources
                    )
                )
                shift += 1
            }
            separator += 4
        }

        for (i in 0..4) {
            if (bushes[i] != 38) {
                game_field[bushes[i]].setNumMask(2, resources)
            } else {
                game_field[bushes[i + 5]].setNumMask(2, resources)
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in game_field) {
            i.draw(canvas)
        }
        abobka.draw(canvas)

    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!in_processing) {
            return super.onTouchEvent(event)
        }
        val x_event = event?.x?.toFloat()
        val y_event = event?.y?.toFloat()
        var index = 0
        for (new_ceil in game_field) {
            var new_square: Float = 0F
            for (num_angle in 0..5) {
                val x_first_angle = new_ceil.angles[num_angle].first
                val y_first_angle = new_ceil.angles[num_angle].second
                val x_second_angle = new_ceil.angles[(num_angle + 1) % 6].first
                val y_second_angle = new_ceil.angles[(num_angle + 1) % 6].second

                val a =
                    sqrt((x_first_angle - x_second_angle) * (x_first_angle - x_second_angle) + (y_first_angle - y_second_angle) * (y_first_angle - y_second_angle))
                val b =
                    sqrt((x_first_angle - x_event!!) * (x_first_angle - x_event) + (y_first_angle - y_event!!) * (y_first_angle - y_event))
                val c =
                    sqrt((x_second_angle - x_event) * (x_second_angle - x_event) + (y_second_angle - y_event) * (y_second_angle - y_event))

                val p = (a + b + c) / 2
                new_square += sqrt(p * (p - a) * (p - b) * (p - c))
            }
            if (new_square <= new_ceil.square) {
                if (new_ceil.num_mack == 1) {
                    val x_abobka = abobka.x + 100F
                    val y_abobka = abobka.y + 165F
                    new_square = 0F
                    for (num_angle in 0..5) {
                        val x_first_angle = new_ceil.angles[num_angle].first
                        val y_first_angle = new_ceil.angles[num_angle].second
                        val x_second_angle = new_ceil.angles[(num_angle + 1) % 6].first
                        val y_second_angle = new_ceil.angles[(num_angle + 1) % 6].second

                        val a =
                            sqrt((x_first_angle - x_second_angle) * (x_first_angle - x_second_angle) + (y_first_angle - y_second_angle) * (y_first_angle - y_second_angle))
                        val b =
                            sqrt((x_first_angle - x_abobka) * (x_first_angle - x_abobka) + (y_first_angle - y_abobka) * (y_first_angle - y_abobka))
                        val c =
                            sqrt((x_second_angle - x_abobka) * (x_second_angle - x_abobka) + (y_second_angle - y_abobka) * (y_second_angle - y_abobka))

                        val p = (a + b + c) / 2
                        new_square += sqrt(p * (p - a) * (p - b) * (p - c))
                    }
                    if (new_square > new_ceil.square) {
                        score += 1
                        new_ceil.setNumMask(2, resources)
                        abobka.setNumMask(2, resources)
                        invalidate()
                        var neighbours = arrayListOf<Pair<Int, Int>>().toMutableList()
                        if (abobka.num_ceil > 4 && abobka.num_ceil % 9 != 4 && game_field[abobka.num_ceil - 4].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil - 4].distance to abobka.num_ceil - 4)
                        }
                        if (abobka.num_ceil > 4 && abobka.num_ceil % 9 != 0 && game_field[abobka.num_ceil - 5].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil - 5].distance to abobka.num_ceil - 5)
                        }
                        if (abobka.num_ceil < 72 && abobka.num_ceil % 9 != 0 && game_field[abobka.num_ceil + 4].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil + 4].distance to abobka.num_ceil + 4)
                        }
                        if (abobka.num_ceil < 72 && abobka.num_ceil % 9 != 4 && game_field[abobka.num_ceil + 5].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil + 5].distance to abobka.num_ceil + 5)
                        }
                        if (abobka.num_ceil + 9 < 77 && game_field[abobka.num_ceil + 9].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil + 9].distance to abobka.num_ceil + 9)
                        }
                        if (abobka.num_ceil - 9 >= 0 && game_field[abobka.num_ceil - 9].num_mack == 1) {
                            neighbours.add(game_field[abobka.num_ceil - 9].distance to abobka.num_ceil - 9)
                        }
                        if (neighbours.size == 0) {
                            in_processing = false
                            is_win = true
                            invalidate()
                            return super.onTouchEvent(event)
                        }
                        neighbours.sortWith(compareBy({ it.first }, { it.second }))
                        var new_num_ceil = neighbours[0].second
                        if (game_field[abobka.num_ceil].distance == 1) {
                            in_processing = false
                            is_win = false
                            if (abobka.num_ceil < 9) {
                                abobka.y -= 2 * y_delta
                            } else if (abobka.num_ceil > 67) {
                                abobka.y += 2 * y_delta
                            } else if (abobka.num_ceil % 9 == 0) {
                                abobka.x -= 115F
                            } else {
                                abobka.x += 115F
                            }
                            invalidate()
                            return super.onTouchEvent(event)
                        }
                        if (neighbours[0].second == abobka.last_num_ceil && neighbours.size > 1) {
                            new_num_ceil = neighbours[1].second
                        }
                        when (new_num_ceil - abobka.num_ceil) {
                            -4 -> {
                                abobka.x += 115F
                                abobka.y -= y_delta
                            }

                            -5 -> {
                                abobka.x -= 115F
                                abobka.y -= y_delta
                            }

                            -9 -> {
                                abobka.y -= 2 * y_delta
                            }

                            4 -> {
                                abobka.x -= 115F
                                abobka.y += y_delta
                            }

                            5 -> {
                                abobka.x += 115F
                                abobka.y += y_delta
                            }

                            9 -> {
                                abobka.y += 2 * y_delta
                            }
                        }
                        if (score != 1) {
                            abobka.last_num_ceil = abobka.num_ceil
                        }
                        abobka.num_ceil = new_num_ceil
                        invalidate()
                        break
                    }
                }
                break
            }
            index += 1
        }
        return super.onTouchEvent(event)
    }
}