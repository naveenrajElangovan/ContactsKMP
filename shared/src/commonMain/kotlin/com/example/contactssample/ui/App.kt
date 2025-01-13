package com.example.contactssample.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.ui.componenets.ContactScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun ContactAppTheme(
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

    ContactAppTheme {
        val contactViewModel = getViewModel(Unit, viewModelFactory { ContactViewModel(sqlDriver) })
        ContactScreen(contactViewModel,contactProvider)
    }
}