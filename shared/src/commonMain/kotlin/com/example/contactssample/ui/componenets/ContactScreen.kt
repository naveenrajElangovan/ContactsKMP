package com.example.contactssample.ui.componenets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.ContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ContactScreen(viewModel: ContactViewModel, contactFetcher: ContactProvider) {
    val contacts by viewModel.contactsList.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    suspend fun fetchContactsIfPermissionGranted(): List<Contacts2> {
        // Fetch contacts (implementation platform-specific)
        return contactFetcher.getContacts()
    }



    if (contacts.isEmpty()){
       coroutineScope.launch {
           withContext(Dispatchers.IO) {
               try {
                   println(fetchContactsIfPermissionGranted())
               }catch (e: Exception) {
                   println(e.message)
               }
           }
       }
    }else{

    }



}

