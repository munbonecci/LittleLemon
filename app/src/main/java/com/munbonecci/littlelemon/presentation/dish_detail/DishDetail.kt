package com.munbonecci.littlelemon.presentation.dish_detail

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.munbonecci.littlelemon.Constants
import com.munbonecci.littlelemon.R
import com.munbonecci.littlelemon.components.CircleIconButton
import com.munbonecci.littlelemon.database.AppDatabase
import com.munbonecci.littlelemon.database.MenuItemRoom
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme
import com.munbonecci.littlelemon.ui.theme.PrimaryGray
import com.munbonecci.littlelemon.ui.theme.Yellow

@Composable
fun DishDetail(
    onBackButtonClicked: () -> Unit,
    id: String?,
) {
    val context = LocalContext.current
    val database by lazy {
        Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, Constants.DATABASE_NAME
        ).build()
    }
    val item = database.menuItemDao().getItem(id ?: "1").observeAsState(MenuItemRoom())
    val size = LocalConfiguration.current
    val headerHeight = size.screenHeightDp * 0.3
    val contentHeight = size.screenHeightDp * 0.73

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        DetailImageHeader(
            menuItem = item.value,
            onBackButtonClicked = onBackButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight.dp)
        )

        DetailCardContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(contentHeight.dp),
            menuItem = item.value
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DetailImageHeader(
    modifier: Modifier = Modifier,
    menuItem: MenuItemRoom,
    onBackButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            GlideImage(
                model = menuItem.image,
                contentDescription = menuItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleIconButton(
                    icon = Icons.Default.ArrowBack
                ) { onBackButtonClicked.invoke() }
                CircleIconButton(
                    icon = Icons.Default.FavoriteBorder
                ) {}
            }
        }
    }
}

@Composable
private fun DetailCardContent(
    modifier: Modifier = Modifier,
    menuItem: MenuItemRoom
) {
    var total by remember { mutableStateOf(menuItem.price) }
    total = menuItem.price
    Card(
        shape = RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp
        ),
        elevation = 0.dp,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Column {
                    Text(
                        text = menuItem.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(all = 16.dp)
                    )
                    Text(
                        text = menuItem.description,
                        color = PrimaryGray,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.price_label)} $%.2f".format(menuItem.price),
                        color = PrimaryGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    )
                    AddOrRemoveItemComponent(onButtonsPressed = { quantity ->
                        total = menuItem.price * quantity
                    })
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = {

                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Yellow,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(16)
                    ) {
                        Text(
                            text =
                            "${stringResource(id = R.string.add_to_cart_label)} $%.2f".format(total)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddOrRemoveItemComponent(onButtonsPressed: (Int) -> Unit) {
    var counter by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(64.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
            icon = Icons.Default.KeyboardArrowLeft
        ) {
            if (counter > 1) {
                counter--
                onButtonsPressed.invoke(counter)
            }
        }
        Text(
            text = counter.toString(),
            color = PrimaryGray,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        CircleIconButton(
            icon = Icons.Default.KeyboardArrowRight
        ) {
            if (counter < 20) {
                counter++
                onButtonsPressed.invoke(counter)
            }
        }
    }
}

@Composable
@Preview(name = Constants.LIGHT_MODE)
@Preview(name = Constants.DARK_MODE, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfilePreview() {
    LittleLemonTheme {
        Surface {
            DishDetail(onBackButtonClicked = {}, "1")
        }
    }
}