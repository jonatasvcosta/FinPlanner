package com.itsession.finplanner.presentation

import data.UserPreferencesRepository
import data.UserPreferencesRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module
import usecase.FinanceUseCase
import usecase.FinanceUseCaseImpl

object ModuleBuilder : KoinComponent {
    fun getModule() : Module{
        return module {
            viewModel { ChartViewModel(get()) }
            single<UserPreferencesRepository>{ UserPreferencesRepositoryImpl() }
            single<FinanceUseCase>{ FinanceUseCaseImpl(get(), get()) }
        }
    }
}