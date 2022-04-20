package com.itsession.finplanner.presentation.domain

import android.view.View
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round

object ExtensionMethods {
    private val currencyMap = mapOf(1000000000.0 to "B", 1000000.0 to "M", 1000.0 to "K")
    fun Double.toFinancialValue() : String{
        currencyMap.forEach { map ->
            val divider = (map.key as? Double) ?: 1.0
            if(this >= divider) return (this / divider).roundFigures(2)+" "+map.value
        }
        return this.roundFigures(2)
    }

    fun Double.roundFigures(places : Int) : String{
        var bd: BigDecimal = BigDecimal.valueOf(this)
        bd = bd.setScale(places, RoundingMode.HALF_EVEN)
        var text = bd.toString()
        if(text.endsWith(".0") && text.length > 2) text = text.substring(0, text.length - 2)
        if(text.endsWith(".00") && text.length > 3) text = text.substring(0, text.length - 3)
        return text
    }

    fun View?.setIsVisible(isVisible : Boolean){
        this?.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}