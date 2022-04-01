package com.itsession.finplanner.presentation.domain

data class ChartData(val label : String, val value : Double, var selected : Boolean = false, var age : Int = 0, var totalSavings : Double = 0.0, var passiveIncome : Double = 0.0, var year : Int = 0, var hasBreakEven : Boolean = false)