package com.example.contactssample.datasource

import android.app.Application
import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.contactssample.AndroidContactProvider
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.db.MyAppSQLDelightDatabase

actual class DriverFactory(private val context: Context){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            MyAppSQLDelightDatabase.Schema.synchronous(), context, "MyAppSQLDelightDatabase.db"
        )
    }

    actual fun getContactProvider(): ContactProvider {
        return AndroidContactProvider(context)
    }

}