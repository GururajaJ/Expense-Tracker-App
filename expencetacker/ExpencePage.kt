import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expencetacker.EViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensePage(
    innerPadding: PaddingValues,
    navController: NavHostController,
    eViewModel: EViewModel,
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(17.dp)),
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                    }

                    IconButton(onClick = { /* Placeholder for expenses */ }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Expenses", tint = Color.White)
                    }

                    IconButton(onClick = { /* Placeholder for settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            }
        }
    ) { paddingValues ->
        ExpenseContent(paddingValues,eViewModel,navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseContent(paddingValues: PaddingValues,eViewModel: EViewModel,navController: NavHostController) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var showButtonLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)  // Background color changed to black
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Expense input card
        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Enter Expense Details", fontSize = 24.sp, color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    leadingIcon = {
                        Icon(Icons.Default.AddCircle, contentDescription = "Amount", tint = Color.Green)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    leadingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Description", tint = Color.Blue)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && description.isNotEmpty()) {
                            showButtonLoading = true


                            coroutineScope.launch {
                                delay(1000)
                                showButtonLoading = false
                                showConfirmation = true
                                eViewModel.addExpense(amount, description)
                                eViewModel.getTotalExpenses()
                                amount = ""
                                description = ""
                                navController.navigate("home")
                            }
                        }


                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    if (showButtonLoading) {
                        CircularProgressIndicator(color = Color.DarkGray)
                    } else {
                        Text("Add Expense", color = Color.DarkGray)
                    }
                }

                AnimatedVisibility(visible = showConfirmation) {
                    Text("Expense added successfully!", color = Color.Green)
                }
            }
        }

        Divider(modifier = Modifier.fillMaxWidth().padding(20.dp), color = Color.White)

        // Recent expenses list

    }
}
