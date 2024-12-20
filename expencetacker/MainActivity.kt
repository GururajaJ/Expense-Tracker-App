package com.example.expencetacker

import ExpensePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.expencetacker.ui.theme.ExpenceTackerTheme

import androidx.compose.animation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



class MainActivity : ComponentActivity() {
    private val eViewModel :EViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            ExpenceTackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
               nav(innerPadding,eViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class) // Add this for animation support
@Composable
fun nav(innerPadding: PaddingValues,eViewModel: EViewModel) {
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
        composable("otp"){
            PhoneNumberOtpScreen(innerPadding,navController,eViewModel)
        }
        composable("splace") {
            splaceScreen(innerPadding, navController,eViewModel)
        }
        composable("onboarding"){
            OnboardingScreen(navController=navController, innerPadding = innerPadding)
        }
        composable("login"){
            LoginScreen(innerPadding,navController,eViewModel)
        }
        composable("register"){
            registerPage(innerPadding,navController,eViewModel)
        }
        composable("home") {
            // Add your home composable screen
            homePage(innerPadding, navController,eViewModel)
        }
        composable("expense") {
            // Add your home composable screen
            ExpensePage(innerPadding,navController,eViewModel)

        }
      composable("chat") {
          ChatPage(navController,innerPadding,eViewModel)
      }
    }

    // AnimatedNavHost from Accompanist

}

// Example of a composable screen function

