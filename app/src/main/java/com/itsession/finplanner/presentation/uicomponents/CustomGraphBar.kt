package com.itsession.finplanner.presentation.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.itsession.finplanner.R

class CustomGraphBar : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )



    init{
        View.inflate(context, R.layout.custom_graph_bar, this)
    }
}