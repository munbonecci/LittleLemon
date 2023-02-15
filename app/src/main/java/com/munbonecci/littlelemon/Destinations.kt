package com.munbonecci.littlelemon

interface Destinations {
    val route: String
}

object OnBoardingScreen: Destinations {
    override val route = "OnBoarding screen"
}

object HomeScreen: Destinations {
    override val route = "Home screen"
}

object ProfileScreen: Destinations {
    override val route = "Profile screen"
}