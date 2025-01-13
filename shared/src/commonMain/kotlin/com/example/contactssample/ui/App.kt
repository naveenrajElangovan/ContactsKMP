package com.example.contactssample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.sqldelight.db.SqlDriver

import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.ui.componenets.ContactScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun MyAppTheme(
        content: @Composable () -> Unit,
) {
        MaterialTheme(
                shapes = MaterialTheme.shapes.copy(
                        small = AbsoluteCutCornerShape(0.dp),
                        medium = AbsoluteCutCornerShape(0.dp),
                        large = AbsoluteCutCornerShape(0.dp)
                )
        ) {
                Surface(modifier = Modifier.background(MaterialTheme.colors.background)) {
                        content()
                }
        }
}
@Composable
fun App(sqlDriver: SqlDriver,contactProvider: ContactProvider) {
        val coroutineScope = rememberCoroutineScope()

        MyAppTheme {
                val contactViewModel = getViewModel(Unit, viewModelFactory { ContactViewModel(sqlDriver) })
                ContactScreen(contactViewModel,contactProvider)

                coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                                try {
                                        contactProvider.getContacts().forEach {
                                                contactViewModel.addContact(it)
                                        }
                                } catch (e: Exception) {
                                        println(e.message)
                                        }
                                }
                        }
                }



}

