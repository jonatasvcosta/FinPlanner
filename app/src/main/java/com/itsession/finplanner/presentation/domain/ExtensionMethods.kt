package com.itsession.finplanner.presentation.domain

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round

object ExtensionMethods {
    private val currencyMap = mapOf(1000000000.0 to "B", 1000000.0 to "M", 1000.0 to "K")
    fun Double.toFinancialValue() : String{
        currencyMap.forEach { map ->
            val divider = (map.key as? Double) ?: 1.0
            if(this > divider) return (this / divider).roundFigures(2)+" "+map.value
        }
        return this.toString()
    }

    fun Double.roundFigures(places : Int) : String{
        var bd: BigDecimal = BigDecimal.valueOf(this)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        var text = bd.toString().replace(".00","")
        if(text.last() == '0') text = text.substring(0, text.length - 1)
        return text
    }
}