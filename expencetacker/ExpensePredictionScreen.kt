package com.example.expencetacker

import ExpensePredictionModel
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ExpensePredictionScreen(context: Context, navController: NavController, innerPaddingValues: PaddingValues) {
    // State variables to hold input, prediction, and loading state

    // Model initialization
    val model = remember { ExpensePredictionModel(context) }

    // Coroutine scope for making prediction without blocking UI thread
    val scope = rememberCoroutineScope()
    var weekNumber by remember { mutableStateOf("") }
    var prediction by remember { mutableStateOf("Predicted Expense will appear here.") }
    var isLoading by remember { mutableStateOf(false) }

    // Input field for the week number
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = weekNumber,
            onValueChange = { weekNumber = it },
            label = { Text("Week Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(56.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle "Done" action
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to predict expense
        Button(
            onClick = {
                if (weekNumber.isNotEmpty()) {
                    isLoading = true
                    val weekFloat = weekNumber.toFloatOrNull()
                    if (weekFloat != null) {
                        // Make prediction using model
                        scope.launch {
                            val predictedExpense = model.predictExpense(weekFloat)
                            prediction = "Predicted Expense: ₹${predictedExpense}"
                            isLoading = false
                        }
                    } else {
                        prediction = "Please enter a valid week number."
                        isLoading = false
                    }
                } else {
                    prediction = "Please enter a week number."
                    isLoading = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Predict Expense")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the prediction result
        Text(text = prediction, fontWeight = FontWeight.Bold, fontSize = 18.sp)

        // Show loading indicator while the prediction is being processed
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }

    // Dispose of the model when no longer needed
    DisposableEffect(Unit) {
        onDispose {
            model.close()
        }
    }
}
