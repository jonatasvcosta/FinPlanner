package usecase

import android.content.Context
import data.UserPreferencesDTO
import domain.ChartData

interface FinanceUseCase {
    val shouldShowInputSteps : Boolean
    fun getUserData() : UserPreferencesDTO?
    fun saveUserData(userData: UserPreferencesDTO)
    fun clearData()
    fun getChartData(age : Int, initialPatrimony : Double, monthSavings : MutableList<Double>, uniqueSavings : MutableList<Double>, uniqueSavingMonth : Int, rates : MutableList<Double>, salary : Double = 0.0) : List<ChartData>
    val PROJECTION_YEARS : Int
}