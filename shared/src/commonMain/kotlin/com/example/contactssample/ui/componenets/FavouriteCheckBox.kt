package com.example.contactssample.ui.componenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FavouriteCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable { onCheckedChange(!isChecked) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = if (isChecked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite Icon",
            tint = if (isChecked) Color.Red else Color.Gray
        )
    }


}