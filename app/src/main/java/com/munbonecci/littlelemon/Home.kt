package com.munbonecci.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.munbonecci.littlelemon.ui.theme.*

@Composable
fun Home(navController: NavHostController) {
    Column {
        Header(navController)
        HeroSection(onPhraseSelected = {})
        MenuCategories(onItemClick = {})
        MenuItems()
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
    onPhraseSelected(searchPhrase)

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
            label = {
                Text(
                    text = stringResource(id = R.string.enter_search_phrase),
                    color = DarkGray
                )
            })
    }
}

@Composable
fun MenuCategories(onItemClick: (String) -> Unit) {
    val categories = listOf("Starters", "Mains", "Desserts", "Drinks")
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
    }
}

@Composable
fun MenuItems() {

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