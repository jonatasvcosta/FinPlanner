package com.itsession.finplanner.presentation.uicomponents

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.itsession.finplanner.R
import com.itsession.finplanner.presentation.domain.ExtensionMethods.toFinancialValue
import kotlin.math.roundToInt

class CustomGraphBar : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object{
        const val MAX_BAR_HEIGHT = 600.0
        const val BAR_ANIMATION_DURATION = 300L
    }

    private var graphBarLabel : TextView? = null
    private var graphBarValue : TextView? = null
    private var graphBarColored : View? = null
    private var graphBarBackground : View? = null
    private var barValue : Double = 0.0
    private var barMax : Double = 1.0

    init{
        View.inflate(context, R.layout.custom_graph_bar, this)
        graphBarLabel = findViewById(R.id.graphbar_label)
        graphBarValue = findViewById(R.id.graphbar_value)
        graphBarColored = findViewById(R.id.graphbar_colored)
        graphBarBackground = findViewById(R.id.graphbar_background)
    }

    fun View?.setHeightWithAnimation(newHeight : Int){
        val currentHeight = this?.layoutParams?.height ?: 0
        val slideAnimator = ValueAnimator.ofInt(currentHeight, newHeight)
        slideAnimator.setDuration(BAR_ANIMATION_DURATION)

        slideAnimator.addUpdateListener {
            this?.layoutParams?.height = (it.animatedValue as? Int) ?: 0
            this?.requestLayout()
        }

        AnimatorSet().apply {
            setInterpolator( AccelerateDecelerateInterpolator())
            play(slideAnimator)
            start()
        }
    }

    fun setGraphBarValue(value : Double, maxValue : Double, label : String? = null, animationDelay : Long = 0L){
        barValue = value
        barMax = maxValue
        graphBarBackground?.layoutParams?.height = MAX_BAR_HEIGHT.toInt()
        graphBarBackground?.requestLayout()
        Handler().postDelayed({
            val barHeight = MAX_BAR_HEIGHT * (value / barMax)
            graphBarColored?.setHeightWithAnimation(barHeight.roundToInt())
        }, animationDelay)
        graphBarLabel?.text = label
        graphBarValue?.text = value.toFinancialValue()
    }
}