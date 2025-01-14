package com.example.feature.coreui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.core.designsystem.components.buttons.StandardButton
import com.example.core.designsystem.utils.InteractiveTheme.colorScheme
import com.example.core.designsystem.utils.InteractiveTheme.dimens

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel : MenuViewModel = viewModel()
    val state by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateEffect) {
        when (val effect = state.navigateEffect) {
            is MenuViewModel.ViewEffect.Navigate -> {
                if (effect.route.isNotEmpty()) {
                    navController.navigate(effect.route)
                 }
            }
        }
        viewModel.processEvent(MenuViewModel.ViewEvent.ConsumeEffect)
    }

    Column(
        modifier = modifier
            .background(colorScheme.generalColors.secondaryBackgroundColor)
            .padding(dimens.dimenXSmall16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_description),
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.generalColors.mainTextColor,
            modifier = Modifier.padding(bottom = dimens.dimenXSmall16)
        )

        StandardButton(
            text = stringResource(id = R.string.go_to_capitalizer_generator_description),
            onButtonClick = { viewModel.processEvent(MenuViewModel.ViewEvent.OnGoToCapitalizer) }
        )
        Spacer(modifier = Modifier.height(dimens.dimenXSmall20))

        StandardButton(
            text = stringResource(id = R.string.go_to_password_generator_description),
            onButtonClick = { viewModel.processEvent(MenuViewModel.ViewEvent.OnGoToPassword) }
        )
        Spacer(modifier = Modifier.height(dimens.dimenXSmall20))

        StandardButton(
            text = stringResource(id = R.string.go_to_presidents_list_description),
            onButtonClick = { viewModel.processEvent(MenuViewModel.ViewEvent.OnGoToPresidentsList) }
        )
    }

}