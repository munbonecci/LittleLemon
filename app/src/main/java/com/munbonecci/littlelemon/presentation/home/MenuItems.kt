package com.munbonecci.littlelemon.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.munbonecci.littlelemon.R
import com.munbonecci.littlelemon.database.MenuItemRoom
import com.munbonecci.littlelemon.ui.theme.LightGray
import com.munbonecci.littlelemon.ui.theme.Peach
import com.munbonecci.littlelemon.ui.theme.PrimaryGray

@OptIn(ExperimentalGlideComposeApi::class)
fun LazyListScope.menuItems(menuItems: List<MenuItemRoom>, onItemPressed: (MenuItemRoom) -> Unit) {
    if (menuItems.isEmpty()) {
        item {
            ShowEmptyItemsAnimation()
        }
    } else {
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
fun ShowEmptyItemsAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.sad_empty_box))
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(148.dp),
            alignment = Alignment.TopCenter
        )
        Text(
            text = stringResource(id = R.string.empty_text),
            textAlign = TextAlign.Center,
            color = Peach,
            fontWeight = FontWeight.Bold
        )
    }
}