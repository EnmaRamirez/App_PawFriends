package com.enma.pawfriends.model

import android.graphics.drawable.Icon
import android.icu.text.CaseMap.Title
import android.media.MediaRouter
import androidx.compose.ui.graphics.vector.ImageVector

//Menus de navegacion

data class DrawerMenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)
