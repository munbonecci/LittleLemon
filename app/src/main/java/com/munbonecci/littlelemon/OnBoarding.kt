package com.munbonecci.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.munbonecci.littlelemon.ui.theme.Gray
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme
import com.munbonecci.littlelemon.ui.theme.Yellow

@Composable
fun OnBoarding() {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.padding(40.dp)
        )
        Row(modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth()) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                textAlign = TextAlign.Right,
            )
        }
        Text(text = "Personal information")
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Yellow,
                unfocusedBorderColor = Gray
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Yellow,
                unfocusedBorderColor = Gray
            ),
            singleLine = true
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Yellow,
                unfocusedBorderColor = Gray
            ),
            singleLine = true
        )
        Button(
            onClick = { }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Register")
        }
    }
}

@Composable
@Preview(name = "light_mode")
@Preview(name = "dark_mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun OnBoardingPreview() {
    LittleLemonTheme {
        Surface {
            OnBoarding()
        }
    }
}