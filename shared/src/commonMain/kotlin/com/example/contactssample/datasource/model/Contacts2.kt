package com.example.contactssample.datasource.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.SerialName


data class Contacts2(

    @SerialName("id")
    val id: Long,
    @SerialName("name") val name: String,
    @SerialName("phone_number") val phone_number: String,
    @SerialName("email") val email: String?,
    @SerialName("address") val address: String?,
    @SerialName("photo") val photo: String?,
    @SerialName("isFavorite") val isFavorite: Boolean,

    )

interface ContactProvider {
    suspend fun getContacts(): MutableStateFlow<List<Contacts2>>
}
