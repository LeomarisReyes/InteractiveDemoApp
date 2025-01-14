package com.example.core.designsystem.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.core.designsystem.utils.InteractiveTheme.colorScheme
import com.example.core.designsystem.utils.InteractiveTheme.dimens

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .padding(dimens.dimenXSmall8),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedPlaceholderColor = colorScheme.generalColors.greyColor,
            focusedBorderColor = colorScheme.generalColors.mainBackgroundColor,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = colorScheme.generalColors.greyColor,
            focusedTextColor = colorScheme.generalColors.mainTextColor
        ),
        shape = RoundedCornerShape(dimens.dimenXSmall6)
    )
}