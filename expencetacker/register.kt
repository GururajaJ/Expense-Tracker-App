package com.example.expencetacker

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registerPage(innerPadding: PaddingValues, navController: NavHostController,eViewModel: EViewModel) {
    var UserEmail by remember { mutableStateOf("") }
    var FirstName by remember { mutableStateOf("") }
    var LastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }
    var isclicked by remember { mutableStateOf(false) }
    val authState =eViewModel.authSate.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthSate.Authenticated-> navController.navigate("home")
            is AuthSate.Error -> Toast.makeText(context,
                (authState.value as AuthSate.Error).message,
                Toast.LENGTH_LONG).show()

            else -> Unit
        }
    }

    // Color animations for button
    val signBtnColor by animateColorAsState(
        targetValue = if (isclicked) Color(0xFF00C853) else Color(0xFF1B5E20),
        label = "signup"
    )
    val signtxColor by animateColorAsState(
        targetValue = if (isclicked) Color.White else Color.White,
        label = "signup"
    )

    // Reset isclicked after delay
    LaunchedEffect(isclicked) {
        if (isclicked) {
            kotlinx.coroutines.delay(500)
            isclicked = false
        }
    }

    // Gradient Background Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E1E), Color(0xFF121212))
                )
            )
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Sign-Up Header
            Text(
                text = "Sign Up!",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Join us for an amazing experience",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // First Name Input
            OutlinedTextField(
                value = FirstName,
                onValueChange = { FirstName = it },
                label = { Text(text = "First Name", color = Color.Gray) },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF00C853),
                ),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(15.dp), ambientColor = Color.Black)
            )

            // Last Name Input
            OutlinedTextField(
                value = LastName,
                onValueChange = { LastName = it },
                label = { Text(text = "Last Name", color = Color.Gray) },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF00C853),
                ),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(15.dp), ambientColor = Color.Black)
            )

            // Email Input
            OutlinedTextField(
                value = UserEmail,
                onValueChange = { UserEmail = it },
                label = { Text(text = "Email", color = Color.Gray) },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF00C853),
                ),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(15.dp), ambientColor = Color.Black)
            )

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", color = Color.Gray) },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF00C853),
                ),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray)
                },
                trailingIcon = {
                    IconButton(onClick = { passVisible = !passVisible }) {
                        Icon(
                            imageVector = if (passVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            tint = Color.Gray
                        )
                    }
                },
                visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(15.dp), ambientColor = Color.Black)
            )

            // Sign-Up Button with animation
            Button(
                onClick = {
                    if (UserEmail.isNotEmpty() && password.isNotEmpty()) {
                        var isClicked = true
                        eViewModel.signUp(UserEmail, password)
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = signBtnColor, contentColor = signtxColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .height(50.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(50.dp))
                    .then(if (isclicked) Modifier.scale(0.95f) else Modifier.scale(1f))
            ) {
                Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf(
                    Pair(R.drawable.google, "Google"),
                    Pair(R.drawable.facebook, "Facebook"),
                    Pair(R.drawable.git, "GitHub")
                ).forEach { (iconId, description) ->
                    Image(
                        painter = painterResource(id = iconId),
                        contentDescription = description,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { /* Handle click */ }
                            .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                            .padding(6.dp)
                            .padding(horizontal = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }

            // Footer Text for Existing Users
            Text(
                text = buildAnnotatedString {
                    append("Already have an ")
                    withStyle(style = SpanStyle(color = Color(0xFF00C853), fontWeight = FontWeight.Bold)) {
                        append("Account?")
                    }
                },
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .clickable { navController.navigate("login") }
            )
            Spacer( modifier = Modifier.height(8.dp))
            Text(
                text = buildAnnotatedString {
                    append("Rigister with ")
                    withStyle(style = SpanStyle(color = Color(0xFF00C853), fontWeight = FontWeight.Bold)) {
                        append("Opt")
                    }
                },
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 18.dp)
                    .clickable { navController.navigate("otp") }
            )
        }
        }

}
