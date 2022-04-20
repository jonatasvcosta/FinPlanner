package com.itsession.finplanner.presentation.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itsession.finplanner.R
import com.itsession.finplanner.presentation.domain.ExtensionMethods.toFinancialValue

class CustomSeekBar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var initialProgress = 0
    var increment = 1
    var maxValue = 1
    var labelSuffix : String = ""
    var labelPrefix : String = ""
    var labelTitle : String = ""
    var onProgressChangedListener : ((Int)->Unit)? = null
    var label : TextView? = null
    var title : TextView? = null
    var seekBar : SeekBar? = null
    var notifyProgressChanged = true

    fun setupComponent(initialProgress : Int, increment : Int, labelPrefix : String = "", labelSuffix : String = "", labelTitle : String = "", maxValue : Int, onProgressChangedListener : ((Int)->Unit)? = null){
        this.initialProgress = initialProgress
        this.increment = increment
        this.labelSuffix = labelSuffix
        this.labelPrefix = labelPrefix
        this.maxValue = maxValue
        this.labelTitle = labelTitle
        this.onProgressChangedListener = onProgressChangedListener
        setView()
    }

    init{
        View.inflate(context, R.layout.custom_seek_bar, this)
    }

    private fun setView(){
        label = findViewById<TextView>(R.id.custom_seek_bar_label)
        title = findViewById<TextView>(R.id.custom_seek_bar_title)
        seekBar = findViewById<SeekBar>(R.id.custom_seek_bar)
        title?.text = labelTitle

        seekBar?.apply {
            max = maxValue
            progress = initialProgress
            incrementProgressBy(increment)
            var offsetX = (initialProgress * width / max).toFloat()
            val formattedProgress = if(maxValue > 100) initialProgress.toDouble().toFinancialValue() else initialProgress.toString()
            label?.text = "$labelPrefix$formattedProgress$labelSuffix"
            label?.x = (x +offsetX + thumbOffset.toFloat() / 2.0f)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(notifyProgressChanged) onProgressChangedListener?.invoke(p1)
                    notifyProgressChanged = true
                    offsetX = (p1 * (width - (thumbOffset.toFloat() * 2.0)) / max).toFloat()
                    val doubleProgress = progress.toDouble()
                    val formattedProgress = if(maxValue > 100) progress.toDouble().toFinancialValue() else progress.toString()
                    label?.text = "$labelPrefix$formattedProgress$labelSuffix"
                    label?.x = (x +offsetX + thumbOffset.toFloat() / 2.0f)
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }
    }

    fun setProgressWithoutListener(progress : Int){
        seekBar?.progress = progress
        notifyProgressChanged = false
    }
}