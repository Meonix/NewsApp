package com.mionix.baseapp.di



import com.mionix.newsapp.repo.*
import org.koin.dsl.module

val repositoryModule = module {
      single { PopularNewsRepo(get()) }
      single { SearchNewsRepo(get()) }
      single { SourcesNewsRepo(get()) }
      single { LoginRepo() }
      single { ProfileRepo() }
      single { NewsDetailRepo() }
//      single { provideSecurePreferences(androidApplication() as MyApplication)}
}
//fun provideSecurePreferences(app: MyApplication): SecurePreferences {
//      return SecurePreferences(app, "", "kanzume.xml")
//}
//fun provideAppSharePreferences(sharedPreferences: SecurePreferences) : Preferences {
//      return Preferences(sharedPreferences)
//}