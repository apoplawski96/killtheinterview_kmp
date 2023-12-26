package sectonone.droidsoft.ap.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

//expect fun sharedPlatformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = { }) = startKoin {
    appDeclaration()
    modules(dataModule, domainModule, screenModelModule)
}

fun initKoin() {
    initKoin {  }
}