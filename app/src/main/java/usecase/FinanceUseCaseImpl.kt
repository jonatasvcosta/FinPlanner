package usecase

import com.itsession.finplanner.presentation.domain.ChartData
import java.util.*
import kotlin.math.pow

class FinanceUseCaseImpl : FinanceUseCase {
    companion object{
        const val PROJECTION_YEARS = 25
        const val MONTH_INTERVAL = (1.0/12.0)
        const val MONTHS_IN_YEAR = 12
    }
    override fun getChartData(
        age: Int,
        initialPatrimony: Double,
        monthSaving: Double,
        uniqueSaving: Double,
        uniqueSavingMonth : Int,
        rate: Double
    ) : List<ChartData> {
        val chartData = mutableListOf<ChartData>()
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var month = Calendar.getInstance().get(Calendar.MONTH)+1
        var value = initialPatrimony
        chartData.add(
            ChartData("$month/$year", value)
        )
        for(i in  (month+1)..(PROJECTION_YEARS* MONTHS_IN_YEAR+month)){
            value *= ((1.0+rate).pow(MONTH_INTERVAL))
            value += monthSaving
            month++
            if(month == uniqueSavingMonth) value += uniqueSaving
            chartData.add(
                ChartData("$month/$year", value)
            )
            if(i.mod(MONTHS_IN_YEAR) == 0){
                year++
                month = 0
            }
        }
        return chartData
    }


}