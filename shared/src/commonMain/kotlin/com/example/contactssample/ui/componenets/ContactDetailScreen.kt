package com.example.contactssample.ui.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.ContactViewModel
import kotlin.random.Random

@Composable
fun ContactDetails(
    contactViewModel: ContactViewModel,
    contact: Contacts2?,
    onBack: () -> Unit,
    onDelete: (Long) -> Unit,
) {

    var name by remember { mutableStateOf(contact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(contact?.phone_number ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }
    var address by remember { mutableStateOf(contact?.address ?: "") }
    var photo by remember { mutableStateOf(contact?.photo ?: "") }
    var isFavorite by remember { mutableStateOf(contact?.isFavorite ?: false) }

    fun generateId(): Long {
        return Random.nextLong()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                if (contact != null) {
                    Text("Contact Detail", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                } else {
                    Text("Add Contact", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        },
    ) {
        Surface(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Name input field
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Name input field
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone Number input field
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Address input field
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(8.dp))

                // Favorite checkbox and delete
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FavouriteCheckBox(
                        isChecked = isFavorite,
                        onCheckedChange = { isFavorite = it }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Favorite")

                    Spacer(modifier = Modifier.width(15.dp))

                    contact?.let {
                        IconButton(onClick = {
                            onDelete(it.id)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                        Text("Delete")
                    }


                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Button
                Button(
                    onClick = {

                        if (contact == null) {
                            val localContact = Contacts2(
                                generateId(),
                                name,
                                phoneNumber,
                                email,
                                address,
                                "photo",
                                isFavorite
                            )
                            contactViewModel.addContact(localContact)
                        } else {
                            val newContact =
                                Contacts2(
                                    contact.id, name, phoneNumber, email, address, contact.photo,
                                    isFavorite
                                )

                            newContact.let { contactViewModel.updateContact(it) }
                        }
                        onBack()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    if (contact == null) {
                        Text("Add")
                    } else {
                        Text("Save")
                    }
                }
            }

        }

    }

}