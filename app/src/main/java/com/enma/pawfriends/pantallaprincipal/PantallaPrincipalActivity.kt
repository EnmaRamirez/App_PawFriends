package com.enma.pawfriends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.enma.pawfriends.ui.theme.PawFriendsTheme
import com.enma.pawfriends.navigation.NavManager
import com.enma.pawfriends.viewmodel.LoginViewModel
import com.enma.pawfriends.viewmodel.NotesViewModel

class PantallaPrincipalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val loginViewModel: LoginViewModel by viewModels()
        val notesViewModel: NotesViewModel by viewModels()
        setContent {
            PawFriendsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavManager(
                        loginViewModel = loginViewModel,
                        notesViewModel = notesViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerItem(item: Destinos,
               selected: Boolean,
               onItemClick: (Destinos)->Unit
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .background(if(selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
            else Color.Transparent)
            .padding(8.dp)
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically

    ){
        Image(painterResource(id = item.icon),
            contentDescription = item.title)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = item.title,
            style =  MaterialTheme.typography.bodySmall,
            color = if (selected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun currentRoute(navController: NavController): String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview2() {
    PawFriendsTheme {
        PantallaPrincipal()
    }
}
