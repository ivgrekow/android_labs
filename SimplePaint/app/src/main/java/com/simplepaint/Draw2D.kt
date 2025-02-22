package com.simplepaint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class Draw2D(context: Context?): View(context) {
    private val paint: Paint = Paint()
    private val rect: Rect = Rect()
    private var bitmap: Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.cat),
        250, 250, false)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }

        canvas.drawPaint(paint)

        paint.apply {
            isAntiAlias = true
            color = Color.YELLOW
        }

        canvas.drawCircle(width - 30F, 30F, 50F, paint)

        paint.color = Color.GREEN
        canvas.drawRect(0F, height - 100F, width.toFloat(), height.toFloat(), paint)

        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 32F
        }

        canvas.drawText("Лужайка только для котов", 30F, height -  32F, paint)

        val x = width - 195F
        val y = 190F

        paint.apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            textSize = 27F
        }

        val str2rotate = "Лучик солнца!"

        canvas.save()
        canvas.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas.drawText(str2rotate, x, y, paint)

        canvas.restore()

        canvas.drawBitmap(bitmap, (width - bitmap.width).toFloat(),
            (height - bitmap.height - 10).toFloat(), paint)
    }
}