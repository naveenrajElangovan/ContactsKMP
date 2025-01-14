package com.example.contactssample.ui.componenets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contactssample.datasource.model.Contacts2
import com.example.contactssample.ui.ContactViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactItem(
    viewModel: ContactViewModel,
    contact: Contacts2,
    onEdit: (Contacts2) -> Unit,
    onDelete : (Long) -> Unit,
    onFavoriteChange: (Boolean) -> Unit
) {
    var show by rememberSaveable { mutableStateOf(true) }
    val dismissState = rememberDismissState()

    LaunchedEffect(dismissState.currentValue) {
        when(dismissState.currentValue){
            DismissValue.DismissedToEnd -> {
                onEdit(contact)
                dismissState.reset()
            }
            DismissValue.DismissedToStart ->{
                onDelete(contact.id)
                dismissState.reset()
            }
            DismissValue.Default ->{

            }

            else -> {}
        }
    }

    AnimatedVisibility(
        show,
        modifier = Modifier.padding(vertical = 4.dp),
        exit = fadeOut(spring(stiffness = Spring.StiffnessLow))
    ) {
        SwipeToDismiss(
            state = dismissState,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                Card(modifier = Modifier.clip(RoundedCornerShape(0.dp)
                ),
                    onClick = {
                        onEdit(contact)
                    }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = contact.name.firstOrNull()?.toString()?.uppercase() ?: "",
                                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = contact.name,
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = contact.phone_number,
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.primary
                                )
                                contact.email?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.body2,
                                        color = MaterialTheme.colors.secondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // Favorite Checkbox
                            FavouriteCheckBox(
                                isChecked = contact.isFavorite,
                                onCheckedChange = onFavoriteChange
                            )
                        }
                    }
                }
            })

    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissBackground(dismissState: DismissState) {

    val direction = dismissState.dismissDirection
    val progress = dismissState.progress.fraction
    val alpha = progress.coerceIn(0f, 0.5f)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = alpha))
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier)
        if (direction == DismissDirection.StartToEnd)
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit"
            )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart)
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete"
            )
    }
}