package usecase

import com.itsession.finplanner.presentation.domain.ChartData

interface FinanceUseCase {
    fun getChartData(age : Int, initialPatrimony : Double, monthSavings : MutableList<Double>, uniqueSavings : MutableList<Double>, uniqueSavingMonth : Int, rates : MutableList<Double>, salary : Double = 0.0) : List<ChartData>
    val PROJECTION_YEARS : Int
}