package com.mehmetpeker.recipe.presentation.authentication.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace

@Composable
fun RegisterScreen(navController: NavController) {
    BaseScreen(viewModel = RegisterViewModel(), navController) {
        RegisterScreenContent(
            onNavigationClick = {
                navController.popBackStack()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(onNavigationClick: () -> Unit) {
    val (username, setUsername) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (email, setEmail) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (password, setPassword) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.sign_up,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {

            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = stringResource(id = R.string.username),
                value = username,
                onValueChange = setUsername
            )
            16.verticalSpace()
            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = stringResource(id = R.string.email_adress),
                value = email,
                onValueChange = setEmail
            )
            16.verticalSpace()
            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = "Password",
                value = password,
                onValueChange = setPassword,
                type = RecipeTextFieldType.PASSWORD
            )
            40.verticalSpace()
            RecipeRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(56.dp), onClick = { }) {
                Text(
                    text = stringResource(id = R.string.sign_up).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp,
                    color = Color.White
                )
            }
        }
    }
}
