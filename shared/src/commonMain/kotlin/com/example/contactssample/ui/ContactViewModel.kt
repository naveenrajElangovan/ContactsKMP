package com.example.contactssample.ui

import com.example.contactssample.datasource.DriverFactory
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(driver: DriverFactory) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    val driverLocal = driver
    val contactsList = MutableStateFlow<List<Contacts2>>(emptyList())
    val database = MyAppSQLDelightDatabase(driver.createDriver())
    val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query


    val searchResults: StateFlow<List<Contacts2>> = combine(_query, contactsList) { query, items ->

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
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    init {
        getContacts(driverLocal)

    }

    private fun getContactsFromLocal(driver: DriverFactory) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            driver.getLocalContacts().collect { contactList ->
                try {
                    for (contact in contactList) {
                        addContact(contact)
                    }

                } catch (e: Exception) {
                    println(e.message)
                } finally {
                    _isLoading.value = false
                }

            }
        }
    }

    private fun getContacts(driver: DriverFactory) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                contactsList.value = database.getAllContacts()
                if (contactsList.value.isEmpty()) {
                    getContactsFromLocal(driver)
                }
            } catch (e: Exception) {
                println(e.message)
            } finally {
            }
        }
    }


    fun updateItems(newItems: List<Contacts2>) {
        contactsList.value = newItems
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun addContact(contacts: Contacts2) {
        viewModelScope.launch(Dispatchers.IO) {
            database.setContact(contacts)
            getContacts(driverLocal)
        }
    }

    fun deleteContact(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteContact(id)
            getContacts(driverLocal)
        }
    }

    fun updateContact(contacts: Contacts2) {
        viewModelScope.launch(Dispatchers.IO) {
            database.updateContact(contacts.copy())
            getContacts(driverLocal)
        }
    }

    fun updateFavourite(isFav: Boolean, id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            database.updateFavourite(id, isFav)
            getContacts(driverLocal)
        }
    }

}