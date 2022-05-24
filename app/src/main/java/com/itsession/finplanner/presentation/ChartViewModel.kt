package com.itsession.finplanner.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.itsession.finplanner.R
import data.UserPreferencesDTO
import domain.ChartData
import domain.InputScreenData
import domain.InputScreenType
import org.koin.core.component.KoinComponent
import usecase.FinanceUseCase

class ChartViewModel(val financeUseCase: FinanceUseCase) : ViewModel(), KoinComponent {

}