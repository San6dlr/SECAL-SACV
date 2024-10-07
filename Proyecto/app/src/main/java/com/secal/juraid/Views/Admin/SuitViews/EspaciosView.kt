package com.secal.juraid.Views.Admin.SuitViews

import AsesoriasView
import CasosCardView
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
<<<<<<< HEAD
=======
import androidx.compose.foundation.layout.size
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
<<<<<<< HEAD
=======
import androidx.compose.material.icons.filled.DateRange
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.secal.juraid.BottomBar
import com.secal.juraid.Routes
import com.secal.juraid.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EspaciosView(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
<<<<<<< HEAD
=======
    val tabs = listOf("Citas confirmadas")
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465

    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = { TopBar() }
    ) { innerPadding ->
<<<<<<< HEAD
        CitasConfirmadasView()
    }
}


=======
        Column(modifier = Modifier.padding(innerPadding)) {
            CitasConfirmadasView()
        }
    }
}

>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
@Composable
fun CitasConfirmadasView() {
    var expandedMenuIndex by remember { mutableStateOf<Int?>(null) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var showDetailsDialog by remember { mutableStateOf(false) }
    var cancelReason by remember { mutableStateOf("") }
    var selectedCitaIndex by remember { mutableStateOf<Int?>(null) }
<<<<<<< HEAD

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Agregamos padding al contenedor principal
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre los elementos
    ) {
        items(9) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
=======
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Añade un padding para separar del contenido
        horizontalArrangement = Arrangement.Center, // Centra el contenido horizontalmente
        verticalAlignment = Alignment.CenterVertically // Centra el contenido verticalmente
    ) {
        Icon(
            imageVector = Icons.Default.DateRange, // Cambia al ícono que necesitas
            contentDescription = null,
            modifier = Modifier.size(24.dp) // Ajusta el tamaño del ícono
        )
        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
        Text("Citas", style = MaterialTheme.typography.headlineMedium)
    }
    LazyColumn {
        items(7) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
                    .height(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    ) {
                        Text("Cita $index", fontWeight = FontWeight.Bold)
                        Text("Descripción de la cita $index", maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                    Box {
                        IconButton(onClick = { expandedMenuIndex = index }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                        }
                        DropdownMenu(
                            expanded = expandedMenuIndex == index,
                            onDismissRequest = { expandedMenuIndex = null }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Detalles") },
                                onClick = {
                                    selectedCitaIndex = index
                                    showDetailsDialog = true
                                    expandedMenuIndex = null
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Cancelar cita") },
                                onClick = {
                                    selectedCitaIndex = index
                                    showCancelDialog = true
                                    expandedMenuIndex = null
                                }
                            )
                        }
                    }
                }
            }
        }
    }

<<<<<<< HEAD
    // Los AlertDialogs se mantienen igual
=======
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
    if (showDetailsDialog) {
        AlertDialog(
            onDismissRequest = { showDetailsDialog = false },
            title = { Text("Detalles de la Cita") },
            text = {
                Column {
<<<<<<< HEAD
                    Text("Nombre: Emiliano Luna George")
                    Text("Región: Región Monterrey")
                    Text("Situación: En espera")
                    Text("Fecha: 01/10/2024")
                    Text("Hora: 13:00hrs")
=======
                    Text("Nombre")
                    Text("Región")
                    Text("Situación")
                    Text("Fecha")
                    Text("Hora")
>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
                }
            },
            confirmButton = {
                Button(onClick = { showDetailsDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }

    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancelar cita") },
            text = {
                Column {
                    Text("Por favor, indique el motivo de la cancelación:")
                    TextField(
                        value = cancelReason,
                        onValueChange = { cancelReason = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    println("Cita $selectedCitaIndex cancelada. Motivo: $cancelReason")
                    showCancelDialog = false
                    cancelReason = ""
                    selectedCitaIndex = null
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showCancelDialog = false
                    cancelReason = ""
                    selectedCitaIndex = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
<<<<<<< HEAD
=======

>>>>>>> c24e129ac49ba1983ea6fbc11c372ebb75441465
