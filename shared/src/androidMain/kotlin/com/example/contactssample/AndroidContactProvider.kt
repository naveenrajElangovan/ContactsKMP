package com.example.contactssample

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import com.example.contactssample.datasource.model.ContactProvider
import com.example.contactssample.datasource.model.Contacts2
import kotlinx.coroutines.suspendCancellableCoroutine

class AndroidContactProvider (private val context: Context) : ContactProvider {

    override suspend fun getContacts(): List<Contacts2> {
        val contactList = mutableListOf<Contacts2>()
        val contentResolver = context.contentResolver

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID)).toLong()
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))

                var phoneNumber: String? = null
                if (it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(id.toString()),
                        null
                    )
                    phoneCursor?.use { phone ->
                        if (phone.moveToNext()) {
                            phoneNumber = phone.getString(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        }
                    }
                }

                var email: String? = null
                val emailCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
                    arrayOf(id.toString()),
                    null
                )
                emailCursor?.use { emailC ->
                    if (emailC.moveToNext()) {
                        email = emailC.getString(emailC.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS))
                    }
                }


                var address : String = null.toString()
                val addressCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    "${ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID} = ?",
                    arrayOf(id.toString()),
                    null
                )
                addressCursor?.use { ac ->
                    while (ac.moveToNext()) {
                        val ad = ac.getString(ac.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS))
                        address = ad
                    }
                }


                contactList.add(Contacts2(id, name, phoneNumber.toString(), email, address))
            }
        }
        return contactList
    }

}