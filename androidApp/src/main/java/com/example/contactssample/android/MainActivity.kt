package com.example.contactssample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.Json
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.com.example.contactssample.MainView
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.ContactViewModel
import com.example.contactssample.ui.componenets.ContactDetails
import kotlinx.serialization.encodeToString

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val contactViewModel = ContactViewModel(DriverFactory(this).createDriver())

                NavHost(navController = navController, startDestination = "contact") {
                    composable("contact") { MainView(contactViewModel,onEdit = { contact ->
                        val contactString = JsonParser().toJson(contact)
                        navController.navigate("detail/$contactString")
                    }) }
                    composable(route="detail/{contactString}", arguments = listOf(navArgument("contactString") { type = NavType.StringType })) { backStackEntry ->
                        val jsonString = backStackEntry.arguments?.getString("contactString")
                        jsonString?.let {
                            ContactDetails(contactViewModel,JsonParser().fromJson(it,Contacts2::class.java), onBack = {navController.popBackStack()})
                        }
                    }
                }
            }
        }
    }
}

