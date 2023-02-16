package com.munbonecci.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeHeader(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 70.dp, end = 70.dp),
        )
        IconButton(
            enabled = true, onClick = {
                navController.navigate(ProfileScreen.route)
            }, modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = stringResource(R.string.go_to_profile_screen),
            )
        }
    }
}