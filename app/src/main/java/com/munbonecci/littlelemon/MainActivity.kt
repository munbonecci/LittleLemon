package com.munbonecci.littlelemon

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.munbonecci.littlelemon.Constants.IS_REGISTERED_PREF
import com.munbonecci.littlelemon.Constants.PREF_NAME
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {

    private val sharedPreferences by lazy {
        getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isRegistered = sharedPreferences.getBoolean(IS_REGISTERED_PREF, false)

        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationComposable(navController, isRegistered)
                }
            }
        }
    }
}

@Composable
@Preview(name = Constants.LIGHT_MODE)
@Preview(name = Constants.DARK_MODE, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DefaultPreview() {
    LittleLemonTheme {
        val navController = rememberNavController()
        NavigationComposable(navController, false)
    }
}