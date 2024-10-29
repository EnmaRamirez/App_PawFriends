import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enma.pawfriends.reportemascotas.PetReportRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun ReportPetScreen(
    onReportSubmitted: () -> Unit,
    repository: PetReportRepository,
    onViewReports: () -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("Perdido") }
    var petDescription by remember { mutableStateOf("") }
    var petLocation by remember { mutableStateOf("") }
    var lastSeenDate by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Almacenamos el nombre del usuario obtenido de Firestore
    var userName by remember { mutableStateOf("Anónimo") }

    // Cargar el nombre del usuario cuando se compone la pantalla
    LaunchedEffect(currentUser) {
        currentUser?.email?.let { email ->
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("Users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        userName = documents.first().getString("name") ?: "Anónimo"
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "REPORTE DE MASCOTAS DESAPARECIDAS",
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        BasicTextField(
            value = petName,
            onValueChange = { petName = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petName.isEmpty()) {
                    Text("Nombre de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = petDescription,
            onValueChange = { petDescription = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petDescription.isEmpty()) {
                    Text("Descripción de la mascota", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = petLocation,
            onValueChange = { petLocation = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (petLocation.isEmpty()) {
                    Text("Ubicación", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = lastSeenDate,
            onValueChange = { lastSeenDate = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black),
            decorationBox = { innerTextField ->
                if (lastSeenDate.isEmpty()) {
                    Text("Última fecha vista (dd/mm/yyyy)", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (petName.isEmpty() || petDescription.isEmpty() || petLocation.isEmpty() || lastSeenDate.isEmpty()) {
                    message = "Por favor, completa todos los campos."
                } else {
                    coroutineScope.launch {
                        val petReport = PetReport(
                            id = System.currentTimeMillis().toString(),
                            name = petName,
                            type = petType,
                            description = petDescription,
                            location = petLocation,
                            date = lastSeenDate,
                            userName = userName // Aquí se agrega el nombre del usuario desde Firestore
                        )
                        repository.reportPet(petReport)
                        message = "Mascota reportada exitosamente."
                        onReportSubmitted()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Reportar mascota", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                petName = ""
                petDescription = ""
                petLocation = ""
                lastSeenDate = ""
                message = ""
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Limpiar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onViewReports()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text("Ver reportes", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.startsWith("Por favor")) Color.Red else Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPetScreenPreview() {
    val repository = PetReportRepository() // Simulación de un repositorio
    ReportPetScreen(
        onReportSubmitted = { /* acción de ejemplo */ },
        repository = repository,
        onViewReports = { /* acción de ejemplo */ }
    )
}
