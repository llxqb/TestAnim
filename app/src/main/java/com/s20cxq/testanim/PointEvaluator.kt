package com.s20cxq.testanim

import android.animation.TypeEvaluator


/**
 *  Created by li.liu  on 2020/10/26
 *  点的 估值器
 */
class PointEvaluator : TypeEvaluator<Point> {

    override fun evaluate(fraction: Float, startPoint: Point, endPoint: Point): Point {
        val x = startPoint.x + fraction * (endPoint.x - startPoint.x)
        val y = startPoint.y + fraction * (endPoint.y - startPoint.y)
        return Point(x.toInt(), y.toInt())
    }
}