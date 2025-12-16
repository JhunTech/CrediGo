package com.redfrogec.credigo.di

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.sdk.KeySDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.ui.viewModel.ClientsViewModel
import com.redfrogec.credigo.ui.viewModel.LoansViewModel
import com.redfrogec.credigo.ui.viewModel.LoginViewModel
import com.redfrogec.credigo.ui.viewModel.NewClientViewModel
import com.redfrogec.credigo.ui.viewModel.NewLoanViewModel
import com.redfrogec.credigo.ui.viewModel.PasswordRecoveryViewModel
import com.redfrogec.credigo.ui.viewModel.SharedViewModel
import com.redfrogec.credigo.ui.viewModel.SignUpViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val targetModule: Module


val sharedModule = module {
    single<LocalDatabase> { LocalDatabase(get()) }
    single<UserSDK> {
        UserSDK(
            database = get()
        )
    }
    single<LoanSDK> {
        LoanSDK(
            database = get()
        )
    }
    single<ClientSDK> {
        ClientSDK(
            database = get()
        )
    }
    single<KeySDK> {
        KeySDK(
            database = get()
        )
    }
    single<ChargeSDK> {
        ChargeSDK(
            database = get()
        )
    }

    single { SharedViewModel() }

    viewModel { LoginViewModel(sdk = get()) }
    viewModel { SignUpViewModel(sdk = get()) }
    viewModel { PasswordRecoveryViewModel(sdk = get()) }
    viewModel { LoansViewModel(sdk = get()) }
    viewModel { ClientsViewModel(sdk = get(), sharedViewModel = get()) }
    viewModel { NewClientViewModel(sdk = get(), sharedViewModel = get()) }
    viewModel { NewLoanViewModel(loanSDK = get(), clientSDK = get(), chargeSDK = get(), sharedViewModel = get()) }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}