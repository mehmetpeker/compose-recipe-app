package com.mehmetpeker.recipe.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.common.RecipeRoundedButtonType
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_HOME
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_LOGIN
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_ONBOARDING
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_REGISTER
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = koinViewModel()
) {
    //TransparentSystemBars()
    LaunchedEffect(viewModel.user?.isRemembered) {
        if (viewModel.user?.isRemembered == true) {
            navController.navigate(ROUTE_HOME) {
                popUpTo(navController.graph.startDestinationRoute ?: ROUTE_ONBOARDING) {
                    inclusive = true
                }
            }
        }
    }
    BaseScreen(viewModel = viewModel, navController = navController) {
        OnboardingContent(
            onLoginClicked = { navController.navigate(ROUTE_LOGIN) },
            onRegisterClicked = { navController.navigate(ROUTE_REGISTER) }
        )
    }
}

@Composable
private fun OnboardingContent(
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Box(modifier = Modifier.padding(it)) {
            Image(
                painter = painterResource(id = R.drawable.bg_splash),
                contentDescription = "Onboarding Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.brand_name), style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.scaledSp,
                        fontFamily = RecipeFontFamily.playfairFamily
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.heightIn(min = 16.dp))
                Text(
                    text = "Sağlıklı yemek pişirmenize yardımcı olur", style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        fontFamily = RecipeFontFamily.poppinsFamily
                    )
                )
                Spacer(modifier = Modifier.heightIn(min = 40.dp))
                RecipeRoundedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    type = RecipeRoundedButtonType.Primary,
                    onClick = onRegisterClicked,
                ) {
                    Text(
                        text = "Üye Ol",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = RecipeFontFamily.poppinsFamily
                        )
                    )
                }
                16.verticalSpace()
                RecipeRoundedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    type = RecipeRoundedButtonType.Secondary,
                    onClick = onLoginClicked,
                ) {
                    Text(
                        text = "Giriş Yap",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = RecipeFontFamily.poppinsFamily
                        )
                    )
                }
            }
        }
    }
}