package com.munbonecci.littlelemon

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.munbonecci.littlelemon.ui.theme.DarkGray
import com.munbonecci.littlelemon.ui.theme.LittleLemonTheme
import com.munbonecci.littlelemon.ui.theme.PrimaryGray
import com.munbonecci.littlelemon.ui.theme.Yellow

@Composable
fun OnBoarding(navController: NavHostController) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        Modifier.fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(140.dp),
            contentScale = ContentScale.FillWidth,
        )
        Row(
            modifier = Modifier
                .background(color = PrimaryGray)
                .fillMaxWidth()
        ) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .weight(1f)
                    .padding(55.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Personal information",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Yellow,
                    unfocusedBorderColor = DarkGray
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Yellow,
                    unfocusedBorderColor = DarkGray
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Yellow,
                    unfocusedBorderColor = DarkGray
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = {
                if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                    showMessage("Registration successful!", context)
                    navController.navigate(HomeScreen.route)
                } else {
                    showMessage("Registration unsuccessful. Please enter all data.", context)
                }
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
            Text(text = "Register")
        }
    }
}

private fun showMessage(message: String, context: Context) {
    Toast.makeText(
        context, message, Toast.LENGTH_SHORT
    ).show()
}

@Composable
@Preview(name = "light_mode")
@Preview(name = "dark_mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun OnBoardingPreview() {
    LittleLemonTheme {
        Surface {
            val navController = rememberNavController()
            OnBoarding(navController)
        }
    }
}