package data

data class UserPreferencesDTO(val initialPatrimony : Double, val salary : Double, val age : Int, val monthSavings : MutableList<Double>, val uniqueSavings : MutableList<Double>, val uniqueSavingsMonth : Int, val rates : MutableList<Double>)