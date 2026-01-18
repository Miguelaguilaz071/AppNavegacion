package com.example.appnavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appnavegacion.ui.theme.AppNavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavegacionTheme {
                AppNavegacion()
            }
        }
    }
}

/*
 * Composable principal que controla la navegación
 * Define las pantallas disponibles en la app
 */
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "contador"
    ) {
        // Pantalla 1: Contador
        composable("contador") {
            PantallaContador(navController)
        }

        // Pantalla 2: Resultado (recibe un parámetro)
        composable("resultado/{valor}") { backStackEntry ->
            val valor = backStackEntry.arguments?.getString("valor") ?: "0"
            PantallaResultado(valor, navController)
        }
    }
}

/*
 * Pantalla principal
 * Maneja estado, eventos y navegación
 */
@Composable
fun PantallaContador(navController: NavController) {
    var contador by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Contador: $contador",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { contador++ }) {
            Text("Sumar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { contador-- }) {
            Text("Restar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Envía el valor multiplicado por 2 a la otra pantalla
            navController.navigate("resultado/${contador * 2}")
        }) {
            Text("Ver Resultado")
        }
    }
}

/*
 * Pantalla de resultado
 * Solo lectura, recibe datos desde la navegación
 */
@Composable
fun PantallaResultado(valor: String, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Valor final: $valor",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Regresar")
            }
        }
    }
}
