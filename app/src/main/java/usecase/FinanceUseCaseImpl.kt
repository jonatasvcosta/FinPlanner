package usecase

import com.itsession.finplanner.presentation.domain.ChartData
import java.util.*
import kotlin.math.pow

class FinanceUseCaseImpl : FinanceUseCase {
    companion object{
        const val MONTH_INTERVAL = (1.0/12.0)
        const val MONTHS_IN_YEAR = 12
    }

    override val PROJECTION_YEARS: Int = 25

    override fun getChartData(
        age: Int,
        initialPatrimony: Double,
        monthSavings: MutableList<Double>,
        uniqueSavings: MutableList<Double>,
        uniqueSavingMonth : Int,
        rates: MutableList<Double>,
        salary : Double,
    ) : List<ChartData> {
        val chartData = mutableListOf<ChartData>()
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var month = Calendar.getInstance().get(Calendar.MONTH)+1
        var value = initialPatrimony
        var reachedBreakEven : Boolean? = null
        chartData.add(
            ChartData("$month/$year", value, age = age)
        )
        var savings = 0.0
        var projectedAge = age
        var yearPassiveIncome = 0.0
        var previousPassiveIncome = 0.0
        var projectedYear = 0

        for(i in  (month+1)..(PROJECTION_YEARS* MONTHS_IN_YEAR+month)){
            val rate = rates[projectedYear]
            val monthSaving = monthSavings[projectedYear]
            val uniqueSaving = uniqueSavings[projectedYear]

            value *= ((1.0+rate).pow(MONTH_INTERVAL))
            value += monthSaving
            savings += monthSaving
            month++
            if(month == uniqueSavingMonth){
                value += uniqueSaving
                savings += uniqueSaving
            }
            yearPassiveIncome = value - savings - initialPatrimony - previousPassiveIncome
            if(yearPassiveIncome > (MONTHS_IN_YEAR * salary + uniqueSaving) && reachedBreakEven != false && salary > 0.0){
                reachedBreakEven = true
            }
            chartData.add(
                ChartData("$month/$year", value, totalSavings = savings, age = projectedAge, passiveIncome = yearPassiveIncome, year = year, hasBreakEven = reachedBreakEven ?: false)
            )
            previousPassiveIncome = yearPassiveIncome
            if(i.mod(MONTHS_IN_YEAR) == 0){
                year++
                projectedAge++
                projectedYear++
                month = 0
                if(reachedBreakEven == true) reachedBreakEven = false
            }
        }
        return chartData
    }


}