package com.example.contactssample.datasource

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.contactssample.AndroidContactProvider
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.db.MyAppSQLDelightDatabase
import kotlinx.coroutines.flow.MutableStateFlow

actual class DriverFactory(private val context: Context){
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            MyAppSQLDelightDatabase.Schema.synchronous(), context, "MyAppSQLDelightDatabase.db"
        )
    }

    actual suspend fun getLocalContacts(): MutableStateFlow<List<Contacts2>> {
        return AndroidContactProvider(context).getContacts()
    }

}