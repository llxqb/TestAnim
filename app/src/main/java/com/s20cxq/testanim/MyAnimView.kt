package com.s20cxq.testanim

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator


/**
 *  Created by li.liu  on 2020/10/27
 */
class MyAnimView(context: Context, attrs: AttributeSet) :
    View(context, attrs) {
    private var currentPoint: Point? = null
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        if (currentPoint == null) {
            currentPoint = Point(RADIUS.toInt(), RADIUS.toInt())
            drawCircle(canvas)
            startAnimation()
        } else {
            drawCircle(canvas)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        val x = currentPoint!!.x.toFloat()
        val y = currentPoint!!.y.toFloat()
        canvas.drawCircle(x, y, RADIUS, mPaint)
    }

    private fun startAnimation() {
        val startPoint =
            Point(RADIUS.toInt(), RADIUS.toInt())
        val endPoint = Point(
            (width - RADIUS).toInt(),
            (height - RADIUS).toInt()
        )
        val anim = ValueAnimator.ofObject(PointEvaluator(), startPoint, endPoint)
        anim.addUpdateListener { animation ->
            currentPoint = animation.animatedValue as Point
            invalidate()
        }
        anim.duration = 5000
        anim.interpolator = BounceInterpolator() // 设置差值器
        anim.start()
    }

    companion object {
        const val RADIUS = 50f
    }

    init {
        mPaint.color = Color.BLUE
    }
}