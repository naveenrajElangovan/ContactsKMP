package com.example.contactssample.datasource

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.db.MyAppSQLDelightDatabase
import kotlinx.coroutines.flow.MutableStateFlow


actual class DriverFactory{
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyAppSQLDelightDatabase.Schema.synchronous(), "MyAppSQLDelightDatabase.db")
    }

    actual suspend fun getLocalContacts(): MutableStateFlow<List<Contacts2>> {
        TODO("Not yet implemented")
    }

}