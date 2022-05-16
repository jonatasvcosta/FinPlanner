package data

import android.annotation.SuppressLint
import android.content.Context

class UserPreferencesRepositoryImpl : UserPreferencesRepository {
    companion object{
        const val SHARED_PREFS = "user_prefs"
        const val INITIAL_PATRIMONY = "INITIAL_PATRIMONY"
        const val SALARY = "SALARY"
        const val AGE = "AGE"
        const val MONTHLY_SAVINGS = "MONTHLY_SAVINGS"
        const val UNIQUE_SAVINGS = "UNIQUE_SAVINGS"
        const val RATES = "RATES"
        const val UNIQUE_SAVINGS_MONTH = "UNIQUE_SAVINGS_MONTH"
    }

    override fun saveData(
        applicationContext: Context,
        userPreferences : UserPreferencesDTO
    ) {
        val pref = applicationContext.getSharedPreferences(SHARED_PREFS, 0)
        val editor = pref.edit()
        var uniqueSavingsStr = ""
        var monthlySavingsStr = ""
        var ratesStr = ""
        userPreferences.uniqueSavings.forEach { uniqueSavingsStr += "$it|" }
        userPreferences.monthSavings.forEach { monthlySavingsStr += "$it|" }
        userPreferences.rates.forEach { ratesStr += "$it|" }

        editor.putFloat(SALARY, userPreferences.salary.toFloat())
        editor.putFloat(INITIAL_PATRIMONY, userPreferences.initialPatrimony.toFloat())
        editor.putInt(AGE, userPreferences.age)
        editor.putString(UNIQUE_SAVINGS, uniqueSavingsStr.dropLast(1))
        editor.putString(MONTHLY_SAVINGS, monthlySavingsStr.dropLast(1))
        editor.putString(RATES, ratesStr.dropLast(1))
        editor.putInt(UNIQUE_SAVINGS_MONTH, userPreferences.uniqueSavingsMonth)
        editor.apply()
    }

    override fun hasData(applicationContext: Context) : Boolean{
        val pref = applicationContext.getSharedPreferences(SHARED_PREFS, 0)
        return pref.getInt(AGE, -1) != -1
    }

    @SuppressLint("CommitPrefEdits")
    override fun clearData(applicationContext: Context) {
        val pref = applicationContext.getSharedPreferences(SHARED_PREFS, 0)
        pref.edit().apply{
            clear()
            apply()
        }
    }

    override fun getData(applicationContext: Context) : UserPreferencesDTO{
        val pref = applicationContext.getSharedPreferences(SHARED_PREFS, 0)
        val salary = pref.getFloat(SALARY, 0.0f).toDouble()
        val initialPatrimony = pref.getFloat(INITIAL_PATRIMONY, 0.0f).toDouble()
        val age = pref.getInt(AGE, 0)
        val uniqueSavings = mutableListOf<Double>()
        val rates = mutableListOf<Double>()
        val monthSavings = mutableListOf<Double>()
        pref.getString(UNIQUE_SAVINGS, "")?.split("|")?.forEach{
            uniqueSavings.add(it.toDouble())
        }
        pref.getString(RATES, "")?.split("|")?.forEach {
            rates.add(it.toDouble())
        }
        pref.getString(MONTHLY_SAVINGS, "")?.split("|")?.forEach {
            monthSavings.add(it.toDouble())
        }
        val uniqueSavingsMonth = pref.getInt(UNIQUE_SAVINGS_MONTH, 0)
        return UserPreferencesDTO(initialPatrimony, salary, age, monthSavings, uniqueSavings, uniqueSavingsMonth, rates)
    }
}