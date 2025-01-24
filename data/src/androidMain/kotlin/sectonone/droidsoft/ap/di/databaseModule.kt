package sectonone.droidsoft.ap.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.dsl.module
import sectonone.droidsoft.ap.data.db.KTIDatabase2

fun databaseModule(context: Context) = module {
    single<SqlDriver> {
        AndroidSqliteDriver(schema = KTIDatabase2.Schema, context = context, name = "kti_database.db")
    }
    single {
        KTIDatabase2(get())
    }
}
