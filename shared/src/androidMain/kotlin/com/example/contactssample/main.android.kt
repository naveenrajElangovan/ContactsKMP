package com.example.contactssample.datasource.com.example.contactssample


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.App
import com.example.contactssample.ui.ContactViewModel


@Composable
fun MainView(viewModel: ContactViewModel, onEdit: (Contacts2) -> Unit, onAdd: () -> Unit) = App(
    viewModel,
    DriverFactory(LocalContext.current.applicationContext),
    onEdit = { onEdit(it) },
    onAdd = onAdd
)
