package com.example.contactssample.ui

import app.cash.sqldelight.db.SqlDriver
import com.example.contactssample.datasource.dao.deleteContact
import com.example.contactssample.datasource.dao.getAllContacts
import com.example.contactssample.datasource.dao.setContact
import com.example.contactssample.datasource.dao.updateContact
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.db.MyAppSQLDelightDatabase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(driver : SqlDriver) : ViewModel() {
    val contactsList = MutableStateFlow<List<Contacts2>>(emptyList())

    private val database = MyAppSQLDelightDatabase(driver)

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactsList.value = database.getAllContacts()
        }
    }

    fun addContact(contacts: Contacts2){
        viewModelScope.launch(Dispatchers.IO) {
            database.setContact(contacts)
            contactsList.value = database.getAllContacts()
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteContact(id)
            contactsList.value = database.getAllContacts()
        }
    }

    fun update(contacts: Contacts2) {
        viewModelScope.launch(Dispatchers.IO) {
            database.updateContact(contacts.copy())
            getContacts()
        }
    }

}