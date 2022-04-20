package com.itsession.finplanner.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.itsession.finplanner.R
import com.itsession.finplanner.presentation.domain.ChartData
import com.itsession.finplanner.presentation.domain.InputScreenData
import com.itsession.finplanner.presentation.domain.InputScreenType
import org.koin.core.component.KoinComponent
import usecase.FinanceUseCase

class ChartViewModel(val financeUseCase: FinanceUseCase) : ViewModel(), KoinComponent {
    var monthSavings = MutableList(financeUseCase.PROJECTION_YEARS + 1){ 1000.0 }
    var initialPatrimony = 12000.0
    var uniqueSavings = MutableList(financeUseCase.PROJECTION_YEARS + 1){ 10000.0 }
    var salary = 3000.0
    var uniqueSavingsMonth = 1
    var rates = MutableList(financeUseCase.PROJECTION_YEARS + 1){ 0.1 }
    var age = 22
    var name : String = ""
    var chart : MutableList<ChartData> = mutableListOf()

    enum class InputStep{ NAME, AGE, SALARY, MONTH_SAVINGS, UNIQUE_SAVINGS, UNIQUE_SAVINGS_MONTH, INITIAL_PATRIMONY }

    private var currentStep = InputStep.AGE

    fun getChartData() : List<ChartData>{
        return financeUseCase.getChartData(age, initialPatrimony, monthSavings, uniqueSavings, uniqueSavingsMonth, rates, salary)
    }

    private fun setDefaultValues(){
        setRate(0, 0.1, true)
        setMonthSavings(0, 1000.0, true)
        setUniqueSavings(0, 10000.0, true)
    }

    private fun setChartVariable(chartVariable : MutableList<Double>, position : Int, value : Double, applyToNextYears: Boolean){
        chartVariable[position] = value
        if(applyToNextYears){ //globally applied to all projection years
            for(i in position..financeUseCase.PROJECTION_YEARS){
                chartVariable[i] = value
            }
        }
    }

    fun setUniqueSavings(position : Int, value : Double, applyToNextYears : Boolean = false){
        setChartVariable(uniqueSavings, position, value, applyToNextYears)
    }

    fun setMonthSavings(position : Int, value : Double, applyToNextYears : Boolean = false){
        setChartVariable(monthSavings, position, value, applyToNextYears)
    }

    fun setRate(position : Int, value : Double, applyToNextYears : Boolean = false){
        setChartVariable(rates, position, value, applyToNextYears)
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
                setMonthSavings(0, it?.toDoubleOrNull() ?: 0.0, true)
            }
            InputStep.INITIAL_PATRIMONY -> InputScreenData(context.getString(R.string.input_screen_initial_patrimony), InputScreenType.CURRENCY){
                initialPatrimony = it?.toDoubleOrNull() ?: 0.0
            }
            InputStep.UNIQUE_SAVINGS -> InputScreenData(context.getString(R.string.input_screen_unique_savings), InputScreenType.CURRENCY){
                setUniqueSavings(0, it?.toDoubleOrNull() ?: 0.0, true)
            }
            else -> InputScreenData(context.getString(R.string.input_screen_unique_savings_month), InputScreenType.MONTH_SELECTOR) {
                uniqueSavingsMonth = it?.toIntOrNull() ?: 0
            }
        }
    }

    fun nextStep(finishCallBack : () -> Unit){
        when(currentStep){
            InputStep.AGE -> {
                currentStep = InputStep.SALARY
                setDefaultValues()
            }
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