package com.s20cxq.testanim

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 测试 Android 动画
 * 一、Android3.0之前有 帧动画 和 补间动画  为什么还要引入属性动画呢？
 * 二、属性动画 核心类 ValueAnimator 以及常用类ObjectAnimator
 * 三、属性动画 怎样执行组合动画？
 * 四、估值器（TypeEvaluator）与 差值器（Interpolator）
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        valueAnimator.setOnClickListener(this)
        objectAnimator.setOnClickListener(this)
        animatorSet_btn.setOnClickListener(this)
        object_view.setOnClickListener(this)
        viewPropertyAnimator.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.valueAnimator -> {
                setValueAnimator()
            }
            R.id.objectAnimator -> {
                setObjectAnimator()
            }
            R.id.animatorSet_btn->{
                setAnimatorSet()
            }
            R.id.object_view->{
                setObjectView()
            }
            R.id.viewPropertyAnimator->{
                setViewPropertyAnimator()
            }
        }
    }

    /**
     *  核心ValueAnimator类
     */
    private fun setValueAnimator() {
        val valueAnimator = ValueAnimator.ofInt(0, 50)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val currentValue = it.animatedValue
            Log.i(TAG, "currentValue=$currentValue")
        }
        valueAnimator.start()
        //setStartDelay()方法来设置动画延迟播放的时间，调用setRepeatCount()和setRepeatMode()方法来设置动画循环播放的次数以及循环播放的模式，
        // 循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放的意思。
    }

    /**
     *  基础ObjectAnimator
     *  因为ValueAnimator只不过是对值进行了一个平滑的动画过渡，但我们实际使用到这种功能的场景好像并不多。
     *  而ObjectAnimator则就不同了，它是可以直接对任意对象的任意属性进行动画操作的，比如说View的alpha属性。
     */
    private fun setObjectAnimator() {
        //淡入淡出
//        val animator = ObjectAnimator.ofFloat(text_tv,"alpha",1f,0f,1f)
        //旋转 rotation
//        val animator = ObjectAnimator.ofFloat(text_tv,"rotation",0f,360f)
        //translationX  x轴上平移 x向左平移500 然后又平移回来
        val animator = ObjectAnimator.ofFloat(text_tv,"translationX",text_tv.translationX,-500f,text_tv.translationX)
        //scaleY   对y轴缩放
//        val animator = ObjectAnimator.ofFloat(text_tv,"scaleY",1f,3f,1f)

        animator.duration = 2000
        animator.addUpdateListener {
            val currentValue = it.animatedValue
            Log.i(TAG, "currentValue=$currentValue")
        }
        animator.start()
    }

    /**
     * 组合动画
     * 实现组合动画功能主要需要借助AnimatorSet这个类
     * after(Animator anim)   将现有动画插入到传入的动画之后执行
       after(long delay)   将现有动画延迟指定毫秒后执行
       before(Animator anim)   将现有动画插入到传入的动画之前执行
       with(Animator anim)   将现有动画和传入的动画同时执行
     */
    private fun setAnimatorSet(){
        val alphaAnim = ObjectAnimator.ofFloat(text_tv,"alpha",1f,0f,1f)
        val translationXAnim = ObjectAnimator.ofFloat(text_tv,"translationX",text_tv.translationX,-500f,text_tv.translationX)
        val rotationAnim = ObjectAnimator.ofFloat(text_tv,"rotation",0f,360f)
        val  animatorSet  = AnimatorSet()
        animatorSet.play(translationXAnim).after(alphaAnim).with(rotationAnim)
        animatorSet.duration = 4000
        animatorSet.start()
        animatorSet.addListener(object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                Log.i(TAG,"动画执行结束")
            }
        })
    }

    /**
     * 使用XML编写动画 :
     * <animator>  对应代码中的ValueAnimator
       <objectAnimator>  对应代码中的ObjectAnimator
       <set>  对应代码中的AnimatorSet
     */
//------------------------//

    /**
     * 设置自定义view 动画
     */
    private fun setObjectView(){
        startActivity(Intent(this,ObjectViewActivity::class.java))
    }

    /**
     * ViewPropertyAnimator:  ObjectAnimator高级用法
     */
    private fun setViewPropertyAnimator(){
//        val animator = ObjectAnimator.ofFloat(text_tv,"alpha",1f,0f,1f)
//        animator.duration = 2000
//        animator.start()
        //使用ViewPropertyAnimator 易懂、简洁
        text_tv.animate().alpha(0f).translationX(-500f).setDuration(3000)
    }
}