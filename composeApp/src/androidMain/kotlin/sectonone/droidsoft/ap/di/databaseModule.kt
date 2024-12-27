package sectonone.droidsoft.ap.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.dsl.module
import sectonone.droidsoft.ap.db.KTIDatabase

fun databaseModule(context: Context) = module {
    single<SqlDriver> {
        AndroidSqliteDriver(schema = KTIDatabase.Schema, context = context, name = "kti_database.db")
    }
    single {
        KTIDatabase(get())
    }
}
