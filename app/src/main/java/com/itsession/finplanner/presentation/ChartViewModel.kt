package com.itsession.finplanner.presentation

import androidx.lifecycle.ViewModel
import com.itsession.finplanner.presentation.domain.ChartData
import com.itsession.finplanner.presentation.uicomponents.ChartAdapter
import org.koin.core.component.KoinComponent
import usecase.FinanceUseCase

class ChartViewModel(val financeUseCase: FinanceUseCase) : ViewModel(), KoinComponent {
    var monthSavings = 1000.0
    val initialPatrimony = 12000.0
    var uniqueSavings = 5000.0
    var salary = 3000.0
    val uniqueSavingsMonth = 1
    var rate = 0.1
    val age = 20

    fun getChartData() : List<ChartData>{
        val chartData = financeUseCase.getChartData(age, initialPatrimony, monthSavings, uniqueSavings, uniqueSavingsMonth, rate, salary)
        return chartData
    }
}