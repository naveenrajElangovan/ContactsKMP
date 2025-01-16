package com.example.contactssample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.componenets.ContactScreen


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
fun App(
    contactViewModel: ContactViewModel,
    driverFactory: DriverFactory,
    onEdit: (Contacts2) -> Unit,
    onAdd: () -> Unit
) {
    ContactScreen(contactViewModel, onEdit = { onEdit(it) }, onAdd = onAdd)
}

