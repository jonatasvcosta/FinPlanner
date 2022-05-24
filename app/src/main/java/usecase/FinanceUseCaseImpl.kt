package usecase

import android.content.Context
import data.UserPreferencesDTO
import data.UserPreferencesRepository
import domain.ChartData
import java.util.*
import kotlin.math.pow

class FinanceUseCaseImpl(val repository : UserPreferencesRepository, val context : Context) : FinanceUseCase {
    override val shouldShowInputSteps: Boolean = repository.hasData(context)
    override val PROJECTION_YEARS: Int = 25

    override fun getUserData(): UserPreferencesDTO? {
        if(!repository.hasData(context)) return null
        return repository.getData(context)
    }

    override fun saveUserData(userData: UserPreferencesDTO) {

    }

    override fun clearData() {

    }

    override fun getChartData(
        age: Int,
        initialPatrimony: Double,
        monthSavings: MutableList<Double>,
        uniqueSavings: MutableList<Double>,
        uniqueSavingMonth : Int,
        rates: MutableList<Double>,
        salary : Double,
    ) : List<ChartData> {
        return listOf()
    }


}