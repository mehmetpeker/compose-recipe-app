package com.mehmetpeker.recipe.presentation.authentication.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar

@Composable
fun LoginScreen() {
    BaseScreen(viewModel = LoginViewModel(), navController = rememberNavController()) {
        LoginScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent() {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.log_in,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {

            RecipeTextField(hintText = "Email Adress", value = email, onValueChange = {
                email = it
            })
            Spacer(modifier = Modifier.height(16.dp))
            RecipeTextField(hintText = "Password", value = password, onValueChange = {
                password = it
            }, type = RecipeTextFieldType.PASSWORD)
        }
    }
}
