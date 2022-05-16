package data

import android.content.Context

interface UserPreferencesRepository {
    fun saveData(applicationContext : Context, userPreferences : UserPreferencesDTO)
    fun getData(applicationContext : Context) : UserPreferencesDTO
    fun clearData(applicationContext : Context)
    fun hasData(applicationContext: Context) : Boolean

}