package com.example.contactssample.datasource.dao

import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.db.MyAppSQLDelightDatabase
import com.example.contactssample.util.toContact2

suspend fun MyAppSQLDelightDatabase.setContact(contacts2: Contacts2) {
    return contactQueries.insertContact(
        contacts2.id, contacts2.name, contacts2.phone_number , contacts2.email, contacts2.address, contacts2.photo,contacts2.isFavorite)
}

suspend fun MyAppSQLDelightDatabase.deleteContact(id : Long){
    return contactQueries.deleteContact(id)
}


fun MyAppSQLDelightDatabase.getAllContacts() : List<Contacts2>{
    return this.contactQueries.getContacts().executeAsList().sortedBy { it.name }.map {
        it.toContact2()
    }
}

suspend fun MyAppSQLDelightDatabase.updateContact(contact : Contacts2){
    return contactQueries.updateContacts(
        contact.name,contact.phone_number,contact.email,contact.address,contact.photo,contact.isFavorite,contact.id
    )
}

suspend fun MyAppSQLDelightDatabase.updateFavourite(id : Long,isFavourite : Boolean){
    return contactQueries.updateFavourite(isFavourite,id)
}



