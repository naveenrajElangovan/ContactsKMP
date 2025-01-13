package com.example.contactssample.datasource

import app.cash.sqldelight.db.SqlDriver
import com.example.contactssample.datasource.model.ContactProvider


expect class DriverFactory {
    fun createDriver(): SqlDriver

    fun getContactProvider(): ContactProvider

}