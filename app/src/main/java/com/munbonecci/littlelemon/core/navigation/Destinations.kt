package com.munbonecci.littlelemon.core.navigation

import com.munbonecci.littlelemon.Constants.HOME_SCREEN
import com.munbonecci.littlelemon.Constants.ON_BOARDING_SCREEN
import com.munbonecci.littlelemon.Constants.PROFILE_SCREEN

interface Destinations {
    val route: String
}
object OnBoardingScreen: Destinations {
    override val route = ON_BOARDING_SCREEN
}
object HomeScreen: Destinations {
    override val route = HOME_SCREEN
}
object ProfileScreen: Destinations {
    override val route = PROFILE_SCREEN
}