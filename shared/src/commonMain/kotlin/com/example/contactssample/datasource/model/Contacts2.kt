package com.example.contactssample.datasource.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.Long
import kotlin.String


data class Contacts2(

  @SerialName("id")
  val id: Long,
  @SerialName("name")
  public val name: String,
  @SerialName("phone_number")
  public val phone_number: String,
  @SerialName("email")
  public val email: String?,
  @SerialName("address")
  public val address: String?,
  @SerialName("photo")
  public val photo : String?,
  @SerialName("isFavorite")
  public val isFavorite: Boolean,

)

interface ContactProvider {
   suspend fun getContacts(): MutableStateFlow<List<Contacts2>>
}
