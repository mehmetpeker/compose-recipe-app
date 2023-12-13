package com.mehmetpeker.recipe.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.mehmetpeker.recipe.R

object RecipeFontFamily {
    val poppinsFamily = FontFamily(
        Font(R.font.poppins_thin, FontWeight.Thin, FontStyle.Normal),
        Font(R.font.poppins_thin_italic, FontWeight.Thin, FontStyle.Italic),
        Font(R.font.poppins_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
        Font(R.font.poppins_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.poppins_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.poppins_medium_italic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.poppins_light, FontWeight.Light, FontStyle.Normal),
        Font(R.font.poppins_light_italic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.poppins_extra_light, FontWeight.ExtraLight, FontStyle.Normal),
        Font(R.font.poppins_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(R.font.poppins_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(R.font.poppins_extra_bold_talic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(R.font.poppins_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.poppins_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.poppins_black, FontWeight.Black, FontStyle.Normal),
        Font(R.font.poppins_black_italic, FontWeight.Black, FontStyle.Italic),
    )
    val playfairFamily = FontFamily(
        Font(R.font.playfair_display_bold,FontWeight.Bold,FontStyle.Normal)
    )
}