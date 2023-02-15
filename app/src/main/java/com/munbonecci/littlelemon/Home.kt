package com.munbonecci.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavHostController) {
    Column {
        Header(navController)
        Spacer(modifier = Modifier.height(56.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(140.dp),
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Composable
private fun Header(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
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

@Composable
@Preview(name = Constants.LIGHT_MODE)
@Preview(name = Constants.DARK_MODE, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomePreview() {
    LittleLemonTheme {
        Surface {
            val navController = rememberNavController()
            Home(navController)
        }
    }
}