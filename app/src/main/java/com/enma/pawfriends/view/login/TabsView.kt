package com.enma.pawfriends.view.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun TabsView(
    innerPadding: PaddingValues  = PaddingValues(top = 40.dp),
    navController: NavController,
    loginViewModel: com.enma.pawfriends.chat.viewmodel.LoginViewModel
){
    var selectedTab by remember { mutableStateOf(0) }
    var tabs = listOf("Login", "Register")

    Column (modifier = Modifier.padding(innerPadding)) {
        TabRow(selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            containerColor = Color.White,

            indicator = {
                    tabPositions ->TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
            )
            }) {
            tabs.forEachIndexed{
                    index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {selectedTab = index}
                ) {
                    Text(text = title)
                }
            }
        }
        when(selectedTab){
            0 -> LoginView(navController = navController, loginViewModel = loginViewModel)
            1 -> RegisterView(navController = navController, loginViewModel = loginViewModel)
        }
    }
}

