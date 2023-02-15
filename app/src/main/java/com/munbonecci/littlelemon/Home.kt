package com.munbonecci.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.munbonecci.littlelemon.ui.theme.*

@Composable
fun Home(navController: NavHostController) {
    val context = LocalContext.current

    val database by lazy {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "database")
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
        Header(navController)
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
            if (categorySelected == "All"){
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
private fun Header(navController: NavHostController) {
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

@Composable
fun HeroSection(onPhraseSelected: (String) -> Unit) {
    var searchPhrase by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(color = PrimaryGray)
            .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 18.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.restaurant_name),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Yellow
                )
                Text(
                    text = stringResource(id = R.string.restaurant_city),
                    fontSize = 24.sp,
                    color = White
                )
                Text(
                    text = stringResource(id = R.string.short_description),
                    color = White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 8.dp, top = 24.dp)
                        .fillMaxWidth(0.6f)
                )
            }
            Column(modifier = Modifier.padding(top = 48.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(170.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = Color.Black,
                    contentDescription = stringResource(R.string.search),
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightGray,
                focusedIndicatorColor = LightGray,
                unfocusedIndicatorColor = LightGray,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            label = {
                Text(
                    text = stringResource(id = R.string.enter_search_phrase),
                    color = DarkGray
                )
            })
    }

    if (searchPhrase.isNotEmpty()) {
        onPhraseSelected(searchPhrase)
    }
}

@Composable
fun MenuCategories(onItemClick: (String) -> Unit) {
    val categories = listOf("Starters", "Mains", "Desserts", "Drinks", "All")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.order_for_delivery),
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onItemClick(category) },
                    shape = RoundedCornerShape(26.dp),
                    backgroundColor = LightGray
                ) {
                    Text(
                        text = category,
                        textAlign = TextAlign.Center,
                        color = DarkGray,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Divider(color = Gray, thickness = 1.dp, modifier = Modifier.padding(top = 24.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(menuItems: List<MenuItemRoom>, onItemPressed: (MenuItemRoom) -> Unit) {
    LazyColumn {
        items(menuItems) { menuItem ->
            Column(modifier = Modifier
                .clickable { onItemPressed(menuItem) }) {
                Row(modifier = Modifier.padding(top = 18.dp, start = 18.dp, end = 18.dp)) {
                    Column {
                        Text(
                            text = menuItem.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = menuItem.description,
                            fontSize = 16.sp,
                            color = PrimaryGray,
                            modifier = Modifier
                                .padding(bottom = 18.dp, end = 8.dp, top = 8.dp)
                                .fillMaxWidth(0.6f),
                            maxLines = 2,
                        )
                        Text(
                            text = "$%.2f".format(menuItem.price),
                            color = PrimaryGray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = menuItem.title,
                        alignment = Alignment.TopCenter,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }
                Divider(
                    color = LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
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