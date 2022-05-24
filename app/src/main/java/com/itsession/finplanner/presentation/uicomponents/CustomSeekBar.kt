package com.itsession.finplanner.presentation.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itsession.finplanner.R
import domain.ExtensionMethods.toFinancialValue

class CustomSeekBar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init{
        View.inflate(context, R.layout.custom_seek_bar, this)
    }
}