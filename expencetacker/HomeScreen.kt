package com.example.expencetacker

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.androidgamesdk.gametextinput.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homePage(innerPadding: PaddingValues,navController: NavController,eViewModel: EViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // The title text is displayed in the center
                        Text(text = "MyExpense")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Action for Home button
                    }) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(17.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        

    ) { paddingValues ->
        homeContent(paddingValues,eViewModel)
    }
}

@Composable
fun homeContent(innerPadding: PaddingValues,eViewModel: EViewModel) {
    var isclicked1 by remember { mutableStateOf(false) }
    LaunchedEffect(isclicked1) {
        if(isclicked1){
            kotlinx.coroutines.delay(200)
            isclicked1=false
        }
    }
    val buttonColor by animateColorAsState(
        targetValue = if(isclicked1)Color.White else Color.DarkGray, label = "b" )
    val buttontext by animateColorAsState(
        targetValue = if(isclicked1)Color.Black else Color.White, label = "bt" )
    var isclicked2 by remember { mutableStateOf(false) }
    LaunchedEffect(isclicked2) {
        if(isclicked2){
            kotlinx.coroutines.delay(200)
            isclicked2=false
        }
    }
    val buttonColor2 by animateColorAsState(
        targetValue = if(isclicked2)Color.White else Color.DarkGray, label = "b2" )
    val buttontext2 by animateColorAsState(
        targetValue = if(isclicked2)Color.Black else Color.White, label = "bt2" )
    var isclicked3 by remember { mutableStateOf(false) }
    LaunchedEffect(isclicked3) {
        if(isclicked3){
            kotlinx.coroutines.delay(200)
            isclicked3=false
        }
    }
    val buttonColor3 by animateColorAsState(
        targetValue = if(isclicked3)Color.White else Color.DarkGray, label = "b3" )
    val buttontext3 by animateColorAsState(
        targetValue = if(isclicked3)Color.Black else Color.White, label = "bt3" )
/// alart otion
    var income by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var tempIncome by remember { mutableStateOf("") }

    var addexpence by remember { mutableStateOf("") }
    var cardcolour by remember { mutableStateOf(false) }
    if(eViewModel.income.isNotEmpty()) {
        if (eViewModel.income < eViewModel.getTotalExpenses().toString()) {
            cardcolour = true

        }
    }

    val card1 by animateColorAsState(
        targetValue = if(cardcolour)Color.Red else Color.DarkGray, label = "c" )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        // Align the content to the bottom
    ) {
        // Main Card containing the Rows
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.CenterHorizontally),
            //border = BorderStroke(color = Color.DarkGray, width = 2.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // Ensures proper spacing between the cards
                ) {
                    // Income Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp),
                       // border = BorderStroke(color = Color.DarkGray, width = 2.dp),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {


                        Text(
                            text="Income:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(20.dp)
                        )
                        Text(
                            text = if (eViewModel.income.isEmpty()) "Click to set income" else "₹ ${eViewModel.income}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            modifier = Modifier.padding(20.dp).align(alignment = AbsoluteAlignment.Right).clickable {
                                showDialog=true
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    // Budget Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(200.dp),
                        //border = BorderStroke(color = Color.DarkGray, width = 2.dp),
                        elevation = CardDefaults.cardElevation(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = card1
                        )

                    ) {
                            Text(
                                text="Expence",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(20.dp)

                            )
                        addexpence = eViewModel.getTotalExpenses().toString()
                        Text(
                            text = "₹ $addexpence",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            modifier = Modifier.padding(20.dp).align(alignment = AbsoluteAlignment.Right)
                        )
                    }
                }
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(10.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Padding around the buttons
                horizontalArrangement = Arrangement.SpaceEvenly, // Space buttons evenly
                verticalAlignment = Alignment.CenterVertically // Center buttons vertically
            ) {
                // Button for "Today"
                Button(
                    onClick = {
                        isclicked1=!isclicked1
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = buttontext
                    ),
                    modifier = Modifier.weight(1f) // Each button takes equal space
                ) {
                    Text(text = "Today")
                }

                Spacer(modifier = Modifier.width(8.dp)) // Spacer between buttons

                // Button for "Weekly"
                Button(
                    onClick = {
                        isclicked2=!isclicked2
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor2,
                        contentColor = buttontext2
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Weekly")
                }


                Spacer(modifier = Modifier.width(8.dp)) // Spacer between buttons

                // Button for "Monthly"
                Button(
                    onClick = {
                        isclicked3=!isclicked3
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor3,
                        contentColor = buttontext3
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Monthly")
                }
            }
        }
        LazyColumn(
            modifier = Modifier.padding(7.dp)
        ) {
            items(eViewModel.expenses) { expense ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Amount: ${expense.amount}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text("Description:   ${expense.description}", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White)
                    }
                }
            }
        }



    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Enter Monthly Income") },
            text = {
                OutlinedTextField(
                    value = tempIncome,
                    onValueChange = { tempIncome = it },
                    label = { Text("Income") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                   eViewModel.updateIncome(tempIncome) // Update income with user input
                    showDialog = false // Close dialog
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
