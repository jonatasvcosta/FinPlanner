package domain

data class InputScreenData(val title : String, val type : InputScreenType, val callBack : (String?) -> Unit)

enum class InputScreenType{ TEXT, CURRENCY, NUMERIC, MONTH_SELECTOR }