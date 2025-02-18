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
        composable("initail") {
            InitialScreen()
        }
        composable("logIn") {
            LoginScreen()
        }
        composable("signUp") {
            SignUpScreen()
        }
    }
}