package com.example.contactssample.datasource.model

import kotlin.Long
import kotlin.String

public data class Contacts2(
  public val id: Long,
  public val name: String,
  public val phone_number: String,
  public val email: String?,
  public val address: String?,
)

interface ContactProvider {
  suspend fun getContacts(): List<Contacts2>
}
