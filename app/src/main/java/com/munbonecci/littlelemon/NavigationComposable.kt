package com.munbonecci.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OnBoardingScreen.route) {
        composable(OnBoardingScreen.route){
            OnBoarding(navController)
        }
        composable(HomeScreen.route){
            Home(navController)
        }
        composable(ProfileScreen.route){
            Profile(navController)
        }
    }
}