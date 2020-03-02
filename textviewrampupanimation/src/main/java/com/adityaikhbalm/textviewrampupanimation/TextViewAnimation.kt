package com.adityaikhbalm.textviewrampupanimation

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.widget.TextView
import kotlin.properties.Delegates

class TextViewAnimation(
    context: Context,
    attrs: AttributeSet? = null
) : TextView(context, attrs), ValueAnimator.AnimatorUpdateListener {

    companion object {
        const val ANIMATION_MIN_VALUE = 0f
        const val ANIMATION_MAX_VALUE = 1f
    }

    var animDuration = 500
    private var isFirst: Boolean = false
    private lateinit var mText: CharSequence
    private var mProgress by Delegates.notNull<Float>()
    private var mTextSize by Delegates.notNull<Float>()
    private var mCharWidthList = floatArrayOf()
    private var mPaint = paint
    var animator: ValueAnimator? = ValueAnimator.ofFloat(ANIMATION_MIN_VALUE, ANIMATION_MAX_VALUE)

    init {
        isFirst = true
        mProgress = ANIMATION_MIN_VALUE
        mTextSize = textSize
        animator?.apply {
            addUpdateListener(this@TextViewAnimation)

            addListener(object : Animator.AnimatorListener {

                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) { setPadding(0,0,0,0) }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}
            })
        }

        mPaint.apply {
            color = currentTextColor
            alpha = 255
            this.textSize = textSize
        }
    }

    override fun onDraw(canvas: Canvas) {
        val layout = layout
        val d = (ANIMATION_MAX_VALUE / mText.length.toDouble())
        val paddingTop = paddingTop
        val paint = mPaint
        val progress = mProgress
        val textSize = mTextSize
        val text = mText
        val charWidthList = mCharWidthList
        var gapIndex = 0

        var lineStart: Int
        var lineEnd: Int
        var lineBaseline: Int
        var lineLeft: Float
        var lineText: String
        val paddingTopSum = paddingTop.toFloat() + textSize/2

        for (i in 0 until layout.lineCount) {
            lineStart = layout.getLineStart(i)
            lineEnd = layout.getLineEnd(i)
            lineLeft = layout.getLineLeft(i) + paddingStart
            lineBaseline = layout.getLineBaseline(i)

            lineText = text.subSequence(lineStart, lineEnd).toString()

            for (j in lineText.indices) {
                if (progress <= (gapIndex * d) + d) {
                    if (progress > (gapIndex * d)) {
                        val tmpProgress = progress - (gapIndex * d)
                        val zoomDiff = paddingTopSum * (ANIMATION_MAX_VALUE - (tmpProgress / d))

                        val matrix = Matrix()
                        matrix.postTranslate(0f, zoomDiff.toFloat())
                        canvas.concat(matrix)

                        if (j == lineText.length - 1) {
                            canvas.drawText(
                                lineText[j].toString(), lineLeft, lineBaseline.toFloat(), paint
                            )
                        }
                        else {
                            canvas.drawText(
                                lineText[j].toString(), lineLeft, lineBaseline.toFloat() / 3, paint
                            )
                        }
                    }
                }
                else {
                    canvas.drawText(
                        lineText[j].toString(), lineLeft, lineBaseline.toFloat(), paint
                    )
                }
                if (charWidthList.isNotEmpty()) {
                    lineLeft += charWidthList[gapIndex++]
                }
            }
        }
    }

    fun startAnimation() {
        mText = text.toString()
        animator?.run {
            if (isStarted) cancel()
            initAnimation()
            duration = getDefaultAnimationDuration()
            start()
        }
    }

    private fun initAnimation() {
        if (isFirst) {
            mPaint.apply {
                textSize = mTextSize
                mCharWidthList = FloatArray(mText.length)
                for (i in mText.indices) {
                    mCharWidthList[i] = measureText(mText[i].toString())
                }
            }
        }
    }

    private fun getDefaultAnimationDuration() =
        (mText.length * animDuration).toLong()

    override fun setText(text: CharSequence, type: BufferType?) {
        if (paramChangeAllowed()) {
            mText = text
            initAnimation()
            super.setText(text, type)
        }
    }

    override fun setTextSize(unit: Int, size: Float) {
        if (paramChangeAllowed()) {
            super.setTextSize(unit, size)
            mTextSize = textSize
            initAnimation()
        }
    }

    private fun paramChangeAllowed() = (animator == null || animator!!.isStarted || isFirst)

    override fun onAnimationUpdate(animation: ValueAnimator) {
        mProgress = animation.animatedValue as Float
        this@TextViewAnimation.invalidate()
    }
}