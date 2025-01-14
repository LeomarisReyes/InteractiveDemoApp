package com.example.feature.passwordgenerator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.designsystem.components.buttons.StandardButton
import com.example.core.designsystem.components.containers.InformationBoard
import com.example.core.designsystem.utils.InteractiveTheme.colorScheme
import com.example.core.designsystem.utils.InteractiveTheme.dimens
import com.example.feature.passwordgenerator.RandomPasswordGeneratorViewModel.ViewEvent

@Composable
fun RandomPasswordGeneratorScreen(
    modifier: Modifier = Modifier
) {
    val viewModel : RandomPasswordGeneratorViewModel = viewModel()
    val state by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .background(colorScheme.generalColors.secondaryBackgroundColor)
            .padding(dimens.dimenXSmall20)
            .fillMaxSize()
            .padding(top = dimens.dimenXSmall16, start = dimens.dimenXSmall12, end = dimens.dimenXSmall12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.dimenXSmall8)
    ) {

        MainInformation()

        InformationBoard(
            title = stringResource(id = R.string.password_settings)
        ) {
            Options(
                isSelected = state.includeUpperCaseLetter,
                text = stringResource(id = R.string.include_uppercase),
                onSwitchChange = { newValue ->
                    viewModel.processEvent(ViewEvent.OnIncludeUpperCaseLetterChanged(newValue))
                }
            )

            Options(
                isSelected = state.includeNumber,
                text = stringResource(id = R.string.include_numbers),
                onSwitchChange = {  newValue ->
                    viewModel.processEvent(ViewEvent.OnIncludeNumberChanged(newValue))
                }
            )

            Options(
                isSelected = state.includeSymbol,
                text = stringResource(id = R.string.include_symbols),
                onSwitchChange = {  newValue ->
                    viewModel.processEvent(ViewEvent.OnIncludeSymbolChanged(newValue))
                }
            )

            PasswordOption(
                text = stringResource(id = R.string.password_size),
                sliderValue = state.size,
                onEvent =  viewModel::processEvent
            )
        }

        StandardButton(
            text = stringResource(
                id = R.string.generate_password
            ),
            onButtonClick = {  viewModel.processEvent(ViewEvent.OnGeneratedPassword)  }
        )

        GeneratedPassword(
            generatedPassword = state.generatedPassword,
            clipboardManager = clipboardManager
        )
    }
}

@Composable
private fun GeneratedPassword(
    modifier: Modifier = Modifier,
    generatedPassword: String,
    clipboardManager: ClipboardManager
){
    InformationBoard(
        modifier = modifier,
        title = stringResource(id = R.string.your_generated_password)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.dimenXSmall4)
        ) {
            Text(
                text = generatedPassword,
                modifier = Modifier
                    .padding(end = dimens.dimenXSmall8),
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.generalColors.mainTextColor
            )

            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(generatedPassword))
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = stringResource(id = R.string.copy_to_clipboard),
                    tint = colorScheme.generalColors.mainIconColor
                )
            }
        }
    }
}

@Composable
private fun MainInformation() {
    Image(
        painter = painterResource(id = R.drawable.ic_password_clock),
        contentDescription = stringResource(id = R.string.password_generator_title),
        modifier = Modifier.size(dimens.dimenXLarge100),
        contentScale = ContentScale.Crop
    )

    Text(
        text = stringResource(id = R.string.password_generator_title),
        style = MaterialTheme.typography.titleLarge,
        color = colorScheme.generalColors.mainTextColor
    )
    Text(
        text = stringResource(id = R.string.password_generator_description),
        style = MaterialTheme.typography.titleMedium,
        color = colorScheme.generalColors.mainTextColor
    )
}

@Composable
private fun PasswordOption(
    modifier: Modifier = Modifier,
    text: String,
    sliderValue: Float,
    onEvent: (ViewEvent) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "$text:      $sliderValue",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = dimens.dimenXSmall5),
            color = colorScheme.generalColors.mainTextColor
        )

        Text(
            text = stringResource(id = R.string.password_size_placeholder),
            style = MaterialTheme.typography.titleSmall,
            color = colorScheme.generalColors.greyColor
        )

        Slider(
            value = sliderValue,
            onValueChange = { newValue -> onEvent(ViewEvent.OnSizeChanged(newValue)) },
            valueRange = 5f..20f,
            steps = 15,
            onValueChangeFinished = {  },
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = colorScheme.generalColors.mainBackgroundColor,
                activeTrackColor = colorScheme.generalColors.mainBackgroundColor,
                inactiveTrackColor = Color.Gray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun Options(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    text: String,
    onSwitchChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall,
            color = colorScheme.generalColors.mainTextColor
        )

        Switch(
            checked = isSelected,
            onCheckedChange = onSwitchChange,
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = Color.Transparent,
                checkedBorderColor = Color.Transparent,
                checkedThumbColor = Color.White,
                checkedTrackColor = colorScheme.generalColors.mainBackgroundColor,
                uncheckedThumbColor = colorScheme.generalColors.mainBackgroundColor,
                uncheckedTrackColor = colorScheme.generalColors.inactiveColor
            )
        )
    }
}