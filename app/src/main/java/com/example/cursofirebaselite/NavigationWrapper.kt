package com.example.cursofirebaselite

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cursofirebaselite.presentation.initial.InitialScreen
import com.example.cursofirebaselite.presentation.login.LoginScreen
import com.example.cursofirebaselite.presentation.signup.SignUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = {navHostController.navigate("logIn")},
                navigateToSignUp = {navHostController.navigate("SignUp")},
            )
        }
        composable("logIn") {
            LoginScreen()
        }
        composable("signUp") {
            SignUpScreen()
        }
    }
}