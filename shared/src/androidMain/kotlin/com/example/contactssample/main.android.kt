package com.example.contactssample.datasource.com.example.contactssample


import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.contactssample.datasource.DriverFactory
import androidx.navigation.compose.rememberNavController
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.App
import com.example.contactssample.ui.ContactViewModel


@Composable
fun MainView(viewModel: ContactViewModel,onEdit: (Contacts2) -> Unit,onAdd : () -> Unit) = App(viewModel,DriverFactory(LocalContext.current.applicationContext), onEdit = {onEdit(it)}, onAdd = onAdd)
