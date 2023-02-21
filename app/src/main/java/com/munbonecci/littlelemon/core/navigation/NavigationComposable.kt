package com.munbonecci.littlelemon.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.munbonecci.littlelemon.presentation.dish_detail.DishDetail
import com.munbonecci.littlelemon.presentation.home.Home
import com.munbonecci.littlelemon.presentation.profile.Profile
import com.munbonecci.littlelemon.presentation.register.OnBoarding

@Composable
fun NavigationComposable(navController: NavHostController, isRegistered: Boolean) {

    val startDestinationRoute = if (isRegistered) HomeScreen.route else OnBoardingScreen.route

    NavHost(navController = navController, startDestination = startDestinationRoute) {
        composable(route = OnBoardingScreen.route) {
            OnBoarding(navController)
        }
        composable(route = HomeScreen.route) {
            Home(navController, onItemPressed = {
                navController.navigate(
                    "${DishDetailScreen.route}/${it.id}"
                )
            })
        }
        composable(ProfileScreen.route) {
            Profile(navController)
        }
        composable(
            route = "${DishDetailScreen.route}/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType },
            )
        ) { navStackEntry ->
            DishDetail(
                onBackButtonClicked = {
                    navController.navigate(HomeScreen.route)
                },
                navStackEntry.arguments?.getString("itemId", ""),
            )
        }
    }
}