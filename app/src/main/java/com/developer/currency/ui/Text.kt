package com.developer.currency.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SeatGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val seatData = listOf(
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"),
        listOf("01", "03", "05", "07", "09", "11", "13", "15", "17", "19", "21", "23"),
        listOf("02", "04", "06", "08", "10", "12", "14", "16", "18", "20", "22", "24")
    )

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 40f
    }

    private val noSeatsColor = Color.parseColor("#FF5722") // Orange
    private val availableSeatsColor = Color.parseColor("#4CAF50") // Green
    private val fewSeatsColor = Color.parseColor("#FFEB3B") // Yellow

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val columnWidth = width / seatData.size
        val rowHeight = height / seatData[0].size

        seatData.forEachIndexed { columnIndex, column ->
            column.forEachIndexed { rowIndex, seatNumber ->
                val left = columnIndex * columnWidth
                val top = rowIndex * rowHeight
                val right = left + columnWidth
                val bottom = top + rowHeight

                paint.color = when (seatNumber) {
                    "01", "02", "03", "04", "05", "06", "07", "08", "10", "11", "12" -> noSeatsColor
                    "09", "14" -> availableSeatsColor
                    else -> fewSeatsColor
                }

                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)

                paint.color = Color.WHITE
                canvas.drawText(
                    seatNumber,
                    (left + right) / 2f,
                    (top + bottom) / 2f - (paint.descent() + paint.ascent()) / 2,
                    paint
                )
            }
        }
    }
}