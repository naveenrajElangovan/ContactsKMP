package com.example.contactssample.util

import com.example.contactssample.datasource.model.Contacts2
import comexamplecontactssampledb.Contacts

fun Contacts2.toContact(): Contacts {
    return Contacts(id, name, phone_number, email, address, photo,isFavorite)
}

fun Contacts.toContact2(): Contacts2 {
    return Contacts2(id, name, phone_number, email, address, photo , isFavourite)
}