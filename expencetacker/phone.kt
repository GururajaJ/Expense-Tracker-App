package com.example.expencetacker

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberOtpScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    eViewModel: EViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    val backgroundColor by animateColorAsState(targetValue = Color.Black)
    val textColor by animateColorAsState(targetValue = Color.White)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter your phone number",
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number", color = textColor) },
            placeholder = { Text("e.g., +1234567890", color = textColor.copy(alpha = 0.5f)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = textColor,
                cursorColor = textColor,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Simulate OTP sending
                Toast.makeText(context, "OTP sent to $phoneNumber", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = textColor
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send OTP")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP", color = textColor) },
            placeholder = { Text("Enter OTP", color = textColor.copy(alpha = 0.5f)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = textColor,
                cursorColor = textColor,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Submit OTP
                Toast.makeText(context, "OTP Submitted", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = textColor
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit OTP")
        }
    }
}

@Preview
@Composable
fun PreviewPhoneNumberOtpScreen() {
    PhoneNumberOtpScreen(paddingValues = PaddingValues(0.dp), navController = NavController(LocalContext.current), eViewModel = EViewModel())
}
