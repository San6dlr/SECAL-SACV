package com.secal.juraid.Views.Generals.Bookings

import ScheduleViewModel
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.secal.juraid.BottomBar
import com.secal.juraid.Routes
import com.secal.juraid.TitlesView
import com.secal.juraid.TopBar
import com.secal.juraid.ViewModel.BookingsViewModel
import com.secal.juraid.ViewModel.UserViewModel
import com.secal.juraid.Views.Generals.Bookings.Schedule.ScheduleScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HelpView(
    navController: NavController,
    scheduleViewModel: ScheduleViewModel,
    bookingsViewModel: BookingsViewModel,
    userViewModel: UserViewModel
) {
    val showHelpForm by bookingsViewModel.showHelpForm.collectAsState()
    val userBookings by bookingsViewModel.filteredBookings.collectAsState()

    var selectedSituation by remember { mutableStateOf("") }
    val situationOptions = listOf("Víctima", "Investigado")
    var termsAccepted by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val options = listOf("Apodaca", "Escobedo", "Guadalupe", "Monterrey", "San Nicolás", "San Pedro")

    val scope = rememberCoroutineScope()
    val scheduleState by scheduleViewModel.uiState.collectAsState()
    val userName by userViewModel.userName.collectAsState()
    val userId by userViewModel.userId.collectAsState()

    val appointmentDate = scheduleState.databaseDateTime?.split(" ")?.getOrNull(0) ?: ""
    val appointmentTime = scheduleState.databaseDateTime?.split(" ")?.getOrNull(1) ?: ""

    val isFormValid = selectedOption.isNotEmpty() && selectedSituation.isNotEmpty() &&
            scheduleState.selectedDate != null && termsAccepted

    LaunchedEffect(userBookings) {
        bookingsViewModel.onEnterHelpView()
    }

    LaunchedEffect(showHelpForm) {
        if (!showHelpForm) {
            navController.navigate(Routes.bookingsVw)
        }
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = { TopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                TitlesView(title = "Agendar Asesoría Legal", 30)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        ScheduleScreen(scheduleViewModel)

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "Selecciona tu región:",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }
                                ) {
                                    OutlinedTextField(
                                        value = if (selectedOption.isEmpty()) "Selecciona tu región" else selectedOption,
                                        onValueChange = {},
                                        label = { Text("Selecciona tu región") },
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                        },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            focusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            cursorColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                                    ) {
                                        options.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option) },
                                                onClick = {
                                                    selectedOption = option
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                                Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    // Situation Selection
                                    Text(
                                        "Selecciona tu situación:",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    situationOptions.forEach { option ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .selectable(
                                                    selected = (option == selectedSituation),
                                                    onClick = { selectedSituation = option }
                                                )
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = (option == selectedSituation),
                                                onClick = { selectedSituation = option },
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .clip(CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = option)
                                        }
                                    }
                                }
                            }

                                Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    // Terms and Conditions
                                    Text(
                                        "Términos y Condiciones",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = termsAccepted,
                                            onCheckedChange = { termsAccepted = it },
                                            modifier = Modifier
                                                .padding(0.dp)
                                                .size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        val annotatedText = buildAnnotatedString {
                                            append("Acepto que un ")
                                            pushStringAnnotation(tag = "alumno", annotation = "")
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    textDecoration = TextDecoration.Underline
                                                )
                                            ) {
                                                append("alumno")
                                            }
                                            pop()
                                            append(" esté presente durante la reunión")
                                        }

                                        Text(
                                            text = annotatedText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.clickable {
                                                showDialog = true
                                            },
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                }
                            }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Confirm Button
                                Button(
                                    onClick = {
                                        scope.launch {
                                            val parts = userName.split(" ")
                                            bookingsViewModel.addBooking(
                                                nombre = parts[0],
                                                apellido = parts.drop(1).joinToString(" "),
                                                fecha = appointmentDate,
                                                hora = appointmentTime,
                                                idRegion = getRegionId(selectedOption),
                                                estado_cita = true,
                                                idSituacion = getSituationId(selectedSituation),
                                                id_usuario = userId
                                            )
                                            navController.navigate(Routes.bookingsVw)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = isFormValid
                                ) {
                                    Text("Confirmar")
                                }
                            }
                        }
                    }
                }
            }


            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {},
                    dismissButton = {},
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Aviso Importante",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { showDialog = false },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    text = {
                        Text(
                            "Dado que la Clínica Penal del Instituto Tecnológico y de Estudios Superiores de Monterrey es un organismo interno, los casos atendidos por esta serán de conocimiento exclusivo de los abogados y alumnos involucrados. Esto tiene como objetivo mejorar la calidad de aprendizaje de los estudiantes. En ningún caso la persona representada será defendida por un alumno, sino por el o los abogados en turno. Al aceptar este aviso, usted acepta las condiciones para agendar una cita con nosotros, así como las estipulaciones en caso de que decidamos tomar su caso."
                        , color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }






private fun getRegionId(regionName: String): Int {
    return when (regionName) {
        "Apodaca" -> 1
        "Escobedo" -> 2
        "Guadalupe" -> 3
        "Monterrey" -> 4
        "San Nicolás" -> 5
        "San Pedro" -> 6
        else -> 1
    }
}

private fun getSituationId(situation: String): Int {
    return when (situation) {
        "Víctima" -> 1
        "Investigado" -> 2
        else -> 1
    }
}
