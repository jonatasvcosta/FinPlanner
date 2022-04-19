package com.itsession.finplanner.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.itsession.finplanner.R
import com.itsession.finplanner.presentation.domain.ChartData
import com.itsession.finplanner.presentation.domain.InputScreenData
import com.itsession.finplanner.presentation.domain.InputScreenType
import com.itsession.finplanner.presentation.uicomponents.ChartAdapter
import org.koin.core.component.KoinComponent
import usecase.FinanceUseCase

class ChartViewModel(val financeUseCase: FinanceUseCase) : ViewModel(), KoinComponent {
    var monthSavings = 1000.0
    var initialPatrimony = 12000.0
    var uniqueSavings = 10000.0
    var salary = 3000.0
    var uniqueSavingsMonth = 1
    var rate = 0.1
    var age = 22
    var name : String = ""

    enum class InputStep{ NAME, AGE, SALARY, MONTH_SAVINGS, UNIQUE_SAVINGS, UNIQUE_SAVINGS_MONTH, INITIAL_PATRIMONY }

    private var currentStep = InputStep.AGE

    fun getChartData() : List<ChartData>{
        return financeUseCase.getChartData(age, initialPatrimony, monthSavings, uniqueSavings, uniqueSavingsMonth, rate, salary)
    }

    fun getInputData(context : Context) : InputScreenData{
        return when(currentStep){
            InputStep.AGE -> InputScreenData(context.getString(R.string.input_screen_age), InputScreenType.NUMERIC) {
                age = it?.toIntOrNull() ?: 0
            }
            InputStep.SALARY -> InputScreenData(context.getString(R.string.input_screen_salary), InputScreenType.CURRENCY) {
                salary = it?.toDoubleOrNull() ?: 0.0
            }
            InputStep.MONTH_SAVINGS -> InputScreenData(context.getString(R.string.input_screen_monthly_savings), InputScreenType.CURRENCY){
                monthSavings = it?.toDoubleOrNull() ?: 0.0
            }
            InputStep.INITIAL_PATRIMONY -> InputScreenData(context.getString(R.string.input_screen_initial_patrimony), InputScreenType.CURRENCY){
                initialPatrimony = it?.toDoubleOrNull() ?: 0.0
            }
            InputStep.UNIQUE_SAVINGS -> InputScreenData(context.getString(R.string.input_screen_unique_savings), InputScreenType.CURRENCY){
                uniqueSavings = it?.toDoubleOrNull() ?: 0.0
            }
            else -> InputScreenData(context.getString(R.string.input_screen_unique_savings_month), InputScreenType.MONTH_SELECTOR) {
                uniqueSavingsMonth = it?.toIntOrNull() ?: 0
            }
        }
    }

    fun nextStep(finishCallBack : () -> Unit){
        when(currentStep){
            InputStep.AGE -> currentStep = InputStep.SALARY
            InputStep.SALARY -> currentStep = InputStep.MONTH_SAVINGS
            InputStep.MONTH_SAVINGS -> currentStep = InputStep.INITIAL_PATRIMONY
            InputStep.INITIAL_PATRIMONY -> currentStep = InputStep.UNIQUE_SAVINGS
            InputStep.UNIQUE_SAVINGS -> currentStep = InputStep.UNIQUE_SAVINGS_MONTH
            else -> {
                finishCallBack.invoke()
                currentStep = InputStep.AGE
            }
        }
    }
}