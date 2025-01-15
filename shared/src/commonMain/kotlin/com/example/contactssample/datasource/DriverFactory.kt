package com.example.contactssample.datasource

import app.cash.sqldelight.db.SqlDriver
import com.example.contactssample.datasource.model.Contacts2
import kotlinx.coroutines.flow.MutableStateFlow


expect class DriverFactory {
    fun createDriver(): SqlDriver

    suspend fun getLocalContacts(): MutableStateFlow<List<Contacts2>>

}