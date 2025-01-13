package com.example.contactssample.ui.componenets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.ui.ContactViewModel

@Composable
fun ContactScreen(viewModel: ContactViewModel, contactFetcher: ContactProvider) {
    val contacts by viewModel.contactsList.collectAsState()

    if (contacts.isEmpty()){
        Text("No Contacts")
    }else{
        println("Hi $contacts")
        Scaffold(topBar = {
                TopAppBar {
                    Text("Contacts", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

        },){paddingValues ->
            Box(Modifier.fillMaxSize().padding(paddingValues)) {
                contacts.forEach {
                    Text(it.name)
                }

            }

        }
    }
}



