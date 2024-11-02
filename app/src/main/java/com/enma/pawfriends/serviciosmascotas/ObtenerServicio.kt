package com.enma.pawfriends.serviciosmascotas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun GetServiceScreen(
    onServiceRequested: (Service) -> Unit,
    navController: (Any) -> Unit
) {
    val scope = rememberCoroutineScope()
    var services by remember { mutableStateOf(listOf<Service>()) }

    LaunchedEffect(Unit) {
        scope.launch {
            services = ServiceRepository.getServices()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(services.size) { index ->
            val service = services[index]
            ServiceItem(service, onServiceRequested)
        }
    }

}