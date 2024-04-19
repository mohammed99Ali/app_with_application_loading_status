package com.udacity


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

val valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(4000)

class
LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {



    private var count = 0
    private var widthSize = 0
    private var heightSize = 0
    private var circleColor = 0
    private var loadingColor = 0
    private var buttonColor = 0
    private var textColor = 0



    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 100.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }
    private val paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 100.0f
        typeface = Typeface.create("", Typeface.BOLD)

    }

    private val paint3 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 100.0f
        typeface = Typeface.create("", Typeface.BOLD)

    }


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }

    private var currentValue = 0
    private var radioGroup:RadioGroup ? = null

    init {
        radioGroup = findViewById(R.id.group)
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            circleColor = getColor(R.styleable.LoadingButton_circleColor, 0)
            loadingColor = getColor(R.styleable.LoadingButton_loadingColor, 0)
            buttonColor = getColor(R.styleable.LoadingButton_buttonColor, 0)
            textColor = getColor(R.styleable.LoadingButton_textColor, 0)
        }
        isClickable = true
        count = 0

    }



    override fun performClick(): Boolean {
        if (super.performClick()) {
            if (radioGroup?.checkedRadioButtonId != -1){
            paint2.color = circleColor
            paint3.color = loadingColor
            count++
            invalidate()
            valueAnimator.start()}
            return true
        }
        return true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    init {
        valueAnimator.addUpdateListener {
            // Update the start angle and sweep angle of the arc.
            currentValue = it.animatedValue as Int
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        // Get the size of the view.
        val width = widthSize
        val height = heightSize
        var string: String = resources.getString(R.string.button_name)

        if (count != 0)
            string = resources.getString(R.string.button_loading)
        paint.color = buttonColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.color = textColor
        canvas.drawText(string, widthSize / 2.0f, heightSize / 2.0f + 30.0f, paint)

        if (count != 0) {

            paint3.color = loadingColor
            canvas.drawRect(
                0f,
                0f,
                widthSize * currentValue.toFloat() / 360,
                height.toFloat(),
                paint3
            )

            canvas.drawArc(
                widthSize - 200f, 50f, widthSize - 100f, 150f,
                0f, currentValue.toFloat(), true, paint2
            )

            paint3.color = textColor
            canvas.drawText(string, widthSize / 2.0f, heightSize / 2.0f + 30.0f, paint)
            valueAnimator.repeatCount = ValueAnimator.INFINITE
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    count = 0

                    invalidate()
                }
            })
        }

    }
}