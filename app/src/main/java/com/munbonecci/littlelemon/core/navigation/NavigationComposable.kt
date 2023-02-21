package com.munbonecci.littlelemon.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.munbonecci.littlelemon.presentation.home.Home
import com.munbonecci.littlelemon.presentation.profile.Profile
import com.munbonecci.littlelemon.presentation.register.OnBoarding

@Composable
fun NavigationComposable(navController: NavHostController, isRegistered: Boolean) {

    val startDestinationRoute = if (isRegistered) HomeScreen.route else OnBoardingScreen.route

    NavHost(navController = navController, startDestination = startDestinationRoute) {
        composable(OnBoardingScreen.route) {
            OnBoarding(navController)
        }
        composable(HomeScreen.route) {
            Home(navController)
        }
        composable(ProfileScreen.route) {
            Profile(navController)
        }
    }
}