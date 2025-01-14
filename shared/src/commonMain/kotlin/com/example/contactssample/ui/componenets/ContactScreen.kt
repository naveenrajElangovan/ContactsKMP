@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.contactssample.ui.componenets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactssample.datasource.DriverFactory
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.ContactViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(viewModel: ContactViewModel,onEdit: (Contacts2) -> Unit) {
    val queryState by viewModel.query.collectAsState()
    val contacts by viewModel.searchResults.collectAsState()
    val scrollState = rememberLazyListState()


    if (contacts.isEmpty() && queryState.isEmpty()) {
        Text("No Contacts")
    } else {
        Scaffold(
            topBar = {
                TopAppBar {
                    Text("Contacts", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

            },
        ) { paddingValues ->
            Column {
                BasicTextField(
                    value = queryState,
                    onValueChange = { viewModel.updateQuery(it) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(1.8.dp))
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .border(1.dp, Color.Gray)
                        .padding(10.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (queryState.isEmpty()) {
                                Text("Search...")
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(Modifier.fillMaxSize().padding(paddingValues)) {

                    LazyColumn(
                        state = scrollState, modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(contacts) { contact ->
                            Row(Modifier.animateItemPlacement()) {
                                ContactItem(viewModel, contact,
                                    onEdit = { onEdit(it) },
                                    onDelete = { id -> viewModel.deleteContact(id) },
                                    onFavoriteChange = { isCheked -> viewModel.updateFavourite(isCheked,contact.id)}
                                )
                            }
                        }
                    }


                }
            }


        }
    }
}



