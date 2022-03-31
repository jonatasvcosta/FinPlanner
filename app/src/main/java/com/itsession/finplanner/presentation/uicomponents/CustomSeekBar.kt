package com.itsession.finplanner.presentation.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itsession.finplanner.R

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
        val label = findViewById<TextView>(R.id.custom_seek_bar_label)
        val title = findViewById<TextView>(R.id.custom_seek_bar_title)
        val seekBar = findViewById<SeekBar>(R.id.custom_seek_bar)
        title.text = labelTitle

        seekBar?.apply {
            progress = initialProgress
            max = maxValue
            incrementProgressBy(increment)
            var offsetX = (initialProgress * width / max).toFloat()
            label?.text = "$labelPrefix$progress$labelSuffix"
            label?.x = (x +offsetX + thumbOffset.toFloat() / 2.0f)

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    onProgressChangedListener?.invoke(p1)
                    offsetX = (p1 * (width - (thumbOffset.toFloat() * 2.0)) / max).toFloat()
                    label?.text = "$labelPrefix$progress$labelSuffix"
                    label?.x = (x +offsetX + thumbOffset.toFloat() / 2.0f)
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}

            })
        }
    }
}