package com.munbonecci.littlelemon.presentation.dish_detail

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.munbonecci.littlelemon.Constants
import com.munbonecci.littlelemon.Constants.BUTTON_ICON
import com.munbonecci.littlelemon.R
import com.munbonecci.littlelemon.components.AddOrRemoveItemComponent
import com.munbonecci.littlelemon.components.CircleIconButton
import com.munbonecci.littlelemon.database.AppDatabase
import com.munbonecci.littlelemon.database.MenuItemRoom
import com.munbonecci.littlelemon.ui.theme.*
import java.util.*

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
    val headerHeight = size.screenHeightDp * 0.5
    val contentHeight = size.screenHeightDp * 0.53

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
                Column(modifier = Modifier.fillParentMaxHeight()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = menuItem.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                        Card(
                            modifier = Modifier.clickable { },
                            shape = RoundedCornerShape(26.dp),
                            backgroundColor = Peach
                        ) {
                            Text(
                                text = menuItem.category.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.US
                                    ) else it.toString()
                                },
                                textAlign = TextAlign.Center,
                                color = White,
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "${stringResource(id = R.string.price_label)} $%.2f".format(menuItem.price),
                        color = PrimaryGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp)
                    )
                    Text(
                        text = menuItem.description,
                        color = PrimaryGray,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 8.dp)
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                modifier = Modifier
                                    .size(26.dp)
                                    .padding(end = 8.dp),
                                contentDescription = BUTTON_ICON,
                                tint = Color.Unspecified
                            )
                            Text(
                                text =
                                "${stringResource(id = R.string.add_to_cart_label)} $%.2f".format(
                                    total
                                )
                            )
                        }
                    }
                }
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