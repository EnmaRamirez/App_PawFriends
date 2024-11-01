package com.enma.pawfriends.model

import androidx.compose.ui.graphics.painter.Painter

//Menus de navegacion

data class DrawerMenuItem(
    val title: String,
    val icon: Painter,
    val route: String
)
