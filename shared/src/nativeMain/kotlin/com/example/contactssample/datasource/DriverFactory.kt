package com.example.contactssample.datasource

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.db.MyAppSQLDelightDatabase



actual class DriverFactory{
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyAppSQLDelightDatabase.Schema.synchronous(), "MyAppSQLDelightDatabase.db")
    }

    actual fun getContactProvider(): ContactProvider {
        TODO("Not yet implemented")
    }
}