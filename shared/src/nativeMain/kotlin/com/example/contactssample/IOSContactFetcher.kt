package com.example.contactssample

import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.datasource.model.Contacts2
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Contacts.CNAuthorizationStatusAuthorized
import platform.Contacts.CNAuthorizationStatusDenied
import platform.Contacts.CNAuthorizationStatusNotDetermined
import platform.Contacts.CNAuthorizationStatusRestricted
import platform.Contacts.CNContactEmailAddressesKey
import platform.Contacts.CNContactFamilyNameKey
import platform.Contacts.CNContactFetchRequest
import platform.Contacts.CNContactGivenNameKey
import platform.Contacts.CNContactPhoneNumbersKey
import platform.Contacts.CNContactPostalAddressesKey
import platform.Contacts.CNContactStore
import platform.Contacts.CNEntityType
import platform.Contacts.CNPostalAddress

class IOSContactFetcher : ContactProvider {
    @OptIn(ExperimentalForeignApi::class)

    override suspend fun getContacts(): List<Contacts2> {
        val contacts = mutableListOf<Contacts2>()
        val store = CNContactStore()

        val keysToFetch = listOf(
            CNContactGivenNameKey,
            CNContactFamilyNameKey,
            CNContactPhoneNumbersKey,
            CNContactEmailAddressesKey,
            CNContactPostalAddressesKey
        )
        val request = CNContactFetchRequest(keysToFetch = keysToFetch)

        store.enumerateContactsWithFetchRequest(request, error = null) { cnContact, _ ->
            val id = cnContact?.identifier.hashCode().toLong()
            val name = "${cnContact?.givenName} ${cnContact?.familyName}".trim()
            // Fetch phone numbers
            val phoneNumbers = cnContact?.phoneNumbers?.map { it }
            val phoneNumbersString = phoneNumbers?.joinToString(", ").toString()

            // Fetch emails
            val emails = cnContact?.emailAddresses?.map { it }
            val emailsString = emails?.joinToString(", ")


            // Fetch addresses
            val addresses = cnContact?.postalAddresses?.map { postalAddress ->
                val address = postalAddress as CNPostalAddress
                listOfNotNull(
                    address.street,
                    address.city,
                    address.state,
                    address.postalCode,
                    address.country
                ).joinToString(", ")
            }

            val addressesString = addresses?.joinToString(", ")

            contacts.add(Contacts2(id, name, phoneNumbersString, emailsString, addressesString))
        }

       return contacts
    }

    override suspend fun requestContactPermission(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val status = CNContactStore.authorizationStatusForEntityType(CNEntityType.CNEntityTypeContacts)
            when (status) {
                CNAuthorizationStatusAuthorized -> continuation.resume(true) {}
                CNAuthorizationStatusDenied, CNAuthorizationStatusRestricted -> continuation.resume(false) {}
                CNAuthorizationStatusNotDetermined -> {
                    CNContactStore().requestAccessForEntityType(CNEntityType.CNEntityTypeContacts) { granted, _ ->
                        continuation.resume(granted) {}
                    }
                }
                else -> continuation.resume(false) {}
            }
        }
    }

    override fun isContactPermissionGranted(): Boolean {
        val status = CNContactStore.authorizationStatusForEntityType(CNEntityType.CNEntityTypeContacts)
        return status == CNAuthorizationStatusAuthorized
    }
}