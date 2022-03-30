package usecase

import com.itsession.finplanner.presentation.domain.ChartData

interface FinanceUseCase {
    fun getChartData(age : Int, initialPatrimony : Double, monthSaving : Double, uniqueSaving : Double, uniqueSavingMonth : Int, rate : Double) : List<ChartData>
}