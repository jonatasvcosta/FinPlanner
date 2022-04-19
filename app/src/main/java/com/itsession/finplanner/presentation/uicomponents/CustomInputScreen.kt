package com.itsession.finplanner.presentation.uicomponents

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.itsession.finplanner.R
import com.itsession.finplanner.presentation.domain.ExtensionMethods.setIsVisible
import com.itsession.finplanner.presentation.domain.InputScreenData
import com.itsession.finplanner.presentation.domain.InputScreenType

class CustomInputScreen : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var editText : EditText? = null
    var selectedValue : String = ""
    fun setupInputScreen(inputScreenData : InputScreenData, onActionDone : (() -> Unit)? = null){
        val title = findViewById<TextView>(R.id.custom_input_title)
        val prefix = findViewById<TextView>(R.id.custom_input_edit_text_prefix)
        val confirm = findViewById<View>(R.id.custom_input_confirm)
        val monthPicker = findViewById<NumberPicker>(R.id.month_picker)
        editText = findViewById(R.id.custom_input_edit_text)
        title.text = inputScreenData.title

        prefix.setIsVisible(inputScreenData.type == InputScreenType.CURRENCY)
        editText.setIsVisible(inputScreenData.type != InputScreenType.MONTH_SELECTOR)
        monthPicker.setIsVisible(inputScreenData.type == InputScreenType.MONTH_SELECTOR)

        editText?.inputType = when(inputScreenData.type){
            InputScreenType.NUMERIC -> InputType.TYPE_CLASS_NUMBER
            InputScreenType.CURRENCY -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            else -> InputType.TYPE_CLASS_TEXT
        }

        if(inputScreenData.type == InputScreenType.MONTH_SELECTOR){
            monthPicker.apply {
                minValue = 0
                maxValue = 11
                displayedValues = resources.getStringArray(R.array.months)
                setOnValueChangedListener { _, _, month ->
                    selectedValue = month.toString()
                }
            }
        }

        confirm.setOnClickListener {
            inputScreenData.callBack.invoke(
                if(inputScreenData.type == InputScreenType.MONTH_SELECTOR) selectedValue else editText?.text.toString()
            )
            onActionDone?.invoke()
            editText?.setText("")
        }
    }

    init{
        View.inflate(context, R.layout.custom_input_screen, this)
    }
}