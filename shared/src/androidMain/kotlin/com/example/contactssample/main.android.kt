package com.example.contactssample.datasource.com.example.contactssample


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.ui.App



@Composable fun MainView() = App(
    DriverFactory(LocalContext.current.applicationContext).createDriver()
,DriverFactory(LocalContext.current.applicationContext).getContactProvider())
