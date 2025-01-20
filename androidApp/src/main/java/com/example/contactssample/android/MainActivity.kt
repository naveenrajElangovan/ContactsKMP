package com.example.contactssample.android

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
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

class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var permissionCallback: ((Boolean) -> Unit)? = null



    private val permissions =
        arrayOf(
            "android.permission.READ_CONTACTS"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                val allGranted = result.values.all { it }
                permissionCallback?.invoke(allGranted)
            }

        if(hasPermissions()){
            setContent {
                MainView()
            }
        }else{
            requestPermission(permissions) { granted ->
                if(granted){
                    setContent{
                        MainView()
                    }
                }
            }
        }

    }

    @Composable
    fun MainView() {
        MyApplicationTheme {
            val navController = rememberNavController()

            val contactViewModel = ContactViewModel(DriverFactory(applicationContext))


            NavHost(navController = navController, startDestination = "contact") {
                composable("contact") {
                    MainView(contactViewModel,
                        onEdit = { contact ->
                            val contactString = JsonParser().toJson(contact)
                            navController.navigate("detail/$contactString")
                        },
                        onAdd = {
                            val contactString = getString(R.string.navigate_to_add)
                            navController.navigate("detail/$contactString")

                        })
                }
                composable(
                    route = "detail/{contactString}",
                    arguments = listOf(navArgument("contactString") { type = NavType.StringType })
                ) { backStackEntry ->
                    val jsonString = backStackEntry.arguments?.getString("contactString")

                    if (jsonString == getString(R.string.navigate_to_add)) {
                        ContactDetails(
                            contactViewModel,
                            null,
                            onBack = { navController.popBackStack() },
                            onDelete = {})
                    } else {
                        ContactDetails(contactViewModel,
                            jsonString?.let { JsonParser().fromJson(it, Contacts2::class.java) },
                            onBack = { navController.popBackStack() },
                            onDelete = { id -> contactViewModel.deleteContact(id) })
                    }
                }
            }
        }
    }

    private fun requestPermission(permissions: Array<String>, callback: (Boolean) -> Unit) {
        permissionCallback = callback
        permissionLauncher.launch(permissions)
    }

    private fun hasPermissions(): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}

