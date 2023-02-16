package com.munbonecci.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.munbonecci.littlelemon.Constants.DATABASE_NAME
import com.munbonecci.littlelemon.Constants.DEFAULT_CATEGORY
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavHostController) {
    val context = LocalContext.current

    val database by lazy {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    var orderMenuItems by remember { mutableStateOf(false) }
    var searchPhrase by remember { mutableStateOf("") }
    var categorySelected by remember { mutableStateOf("") }
    var menuItems = if (orderMenuItems) {
        databaseMenuItems.sortedBy { it.title }
    } else {
        databaseMenuItems
    }

    Column {
        HomeHeader(navController)
        HeroSection(onPhraseSelected = {phrase ->
            searchPhrase = phrase
        })
        MenuCategories(onItemClick = {category ->
            categorySelected = category
        })
        if (searchPhrase.isNotEmpty()) {
            menuItems = databaseMenuItems.filter {
                it.title.contains(searchPhrase, ignoreCase = true)
            }
        }
        if (categorySelected.isNotEmpty()) {
            if (categorySelected == DEFAULT_CATEGORY){
                orderMenuItems = false
            } else {
                menuItems = databaseMenuItems.filter {
                    it.category.contains(categorySelected, ignoreCase = true)
                }
            }
        }
        MenuItems(menuItems, onItemPressed = {})
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