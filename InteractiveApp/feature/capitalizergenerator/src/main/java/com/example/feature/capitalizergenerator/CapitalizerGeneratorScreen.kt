package com.example.feature.capitalizergenerator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.core.designsystem.components.inputs.CustomInputField
import com.example.core.designsystem.utils.InteractiveTheme.colorScheme
import com.example.core.designsystem.utils.InteractiveTheme.dimens

@Composable
fun CapitalizerGeneratorScreen(
    modifier: Modifier = Modifier
){
    val viewModel : CapitalizerGeneratorViewModel = viewModel()
    val state by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .background(colorScheme.generalColors.secondaryBackgroundColor)
            .padding(dimens.dimenXSmall20)
            .fillMaxSize()
            .padding(top = dimens.dimenXSmall16, start = dimens.dimenXSmall12, end = dimens.dimenXSmall12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.dimenXSmall20)
    ) {
        MainInformation()

        InformationBoard(
            title = stringResource(id = R.string.phrase_settings)
        ) {
            CustomInputField(
                value = state.phraseToCapitalized,
                onValueChange = {
                    viewModel.processEvent(CapitalizerGeneratorViewModel.ViewEvent.OnPhraseChanged(it))
                },
                 placeholder =  stringResource(id = R.string.capitalizer_input_placeholder)
            )
        }

        StandardButton(
            text = stringResource(
                id = R.string.capitalized_description
            ),
            onButtonClick = {
                if(state.phraseToCapitalized.isNotEmpty())
                    viewModel.processEvent(CapitalizerGeneratorViewModel.ViewEvent.OnCapitalizedPhrase)
              }
        )

        CapitalizedPhrase(
            capitalizedPhrase = state.capitalizedPhrase,
            clipboardManager = clipboardManager
        )
    }
}

@Composable
private fun MainInformation() {
    Image(
        painter = painterResource(id = R.drawable.ic_capitalize_image),
        contentDescription = stringResource(id = R.string.capitalizer_image),
        modifier = Modifier.size(dimens.dimenXLarge120),
        contentScale = ContentScale.Crop
    )

    Text(
        text = stringResource(id = R.string.capitalizer_title),
        style = MaterialTheme.typography.titleLarge,
        color = colorScheme.generalColors.mainTextColor
    )
    Text(
        text = stringResource(id = R.string.capitalizer_description),
        style = MaterialTheme.typography.titleMedium,
        color = colorScheme.generalColors.mainTextColor
    )
    Spacer(modifier = Modifier.height(dimens.dimenXSmall12))
}

@Composable
private fun CapitalizedPhrase(
    modifier: Modifier = Modifier,
    capitalizedPhrase: String,
    clipboardManager: ClipboardManager
) {
    InformationBoard(
        modifier = modifier,
        title = stringResource(id = R.string.you_capitalizer_phrase_descriptiln)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.dimenXSmall8)
        ) {
            Text(
                text = capitalizedPhrase,
                modifier = Modifier
                    .padding(end = dimens.dimenXSmall8),
                style = MaterialTheme.typography.titleMedium,
                color = colorScheme.generalColors.mainTextColor
            )

            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(capitalizedPhrase))
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription =  stringResource(id = R.string.copy_to_clipboard_description),
                    tint = colorScheme.generalColors.mainIconColor
                )
            }
        }
    }
}