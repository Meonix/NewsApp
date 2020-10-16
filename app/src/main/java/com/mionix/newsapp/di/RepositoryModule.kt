package com.mionix.baseapp.di



import com.mionix.newsapp.repo.LoginRepo
import com.mionix.newsapp.repo.PopularNewsRepo
import com.mionix.newsapp.repo.SearchNewsRepo
import com.mionix.newsapp.repo.SourcesNewsRepo
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
      single { PopularNewsRepo(get()) }
      single { SearchNewsRepo(get()) }
      single { SourcesNewsRepo(get()) }
      single { LoginRepo() }
//      single { provideSecurePreferences(androidApplication() as MyApplication)}
}
//fun provideSecurePreferences(app: MyApplication): SecurePreferences {
//      return SecurePreferences(app, "", "kanzume.xml")
//}
//fun provideAppSharePreferences(sharedPreferences: SecurePreferences) : Preferences {
//      return Preferences(sharedPreferences)
//}