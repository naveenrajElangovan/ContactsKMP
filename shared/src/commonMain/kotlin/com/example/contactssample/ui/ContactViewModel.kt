package com.example.contactssample.ui

import app.cash.sqldelight.db.SqlDriver
import com.example.contactssample.datasource.dao.deleteContact
import com.example.contactssample.datasource.dao.getAllContacts
import com.example.contactssample.datasource.dao.setContact
import com.example.contactssample.datasource.dao.updateContact
import com.example.contactssample.datasource.dao.updateFavourite
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.db.MyAppSQLDelightDatabase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(driver : SqlDriver) : ViewModel() {
    val contactsList = MutableStateFlow<List<Contacts2>>(emptyList())
    val database = MyAppSQLDelightDatabase(driver)
    val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val searchResults : StateFlow<List<Contacts2>> = combine(_query, contactsList) { query, items ->
        if (query.isEmpty()) {
            items
        } else {
            items.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.phone_number.contains(query, ignoreCase = true)
            }
        }
    }
        .stateIn(
            scope = viewModelScope, // Replace with the appropriate CoroutineScope
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactsList.value = database.getAllContacts()
        }
    }


    fun updateItems(newItems: List<Contacts2>) {
        contactsList.value = newItems
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun addContact(contacts: Contacts2){
        viewModelScope.launch(Dispatchers.IO) {
            database.setContact(contacts)
            contactsList.value = database.getAllContacts()
        }
    }

    fun deleteContact(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteContact(id)
            contactsList.value = database.getAllContacts()
        }
    }

    fun updateContact(contacts: Contacts2) {
        viewModelScope.launch(Dispatchers.IO) {
            database.updateContact(contacts.copy())
            getContacts()
        }
    }

    fun updateFavourite(isFav : Boolean,id : Long ){
        viewModelScope.launch (Dispatchers.IO){
            database.updateFavourite(id,isFav)
            getContacts()
        }
    }

}