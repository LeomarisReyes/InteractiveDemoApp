package com.example.feature.presidents.list


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.designsystem.components.containers.InformationBoard
import com.example.core.designsystem.components.inputs.CustomInputField
import com.example.core.designsystem.utils.InteractiveTheme.colorScheme
import com.example.core.models.presidents.ColombiaPresident
import com.example.feature.presidents.R
import com.example.core.designsystem.utils.InteractiveTheme.dimens

@Composable
fun PresidentListScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: PresidentListViewModel = hiltViewModel()
    val state by viewModel.viewStateFlow.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && state.searchDescription.isEmpty()) {
            val totalItems = state.presidents.size
            val nextPage = totalItems / 10 + 1
            viewModel.processEvent(PresidentListViewModel.ViewEvent.LoadMoreData(nextPage))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.processEvent(PresidentListViewModel.ViewEvent.LoadData)
    }

    LaunchedEffect(state.navigateEffect) {
        when (val effect = state.navigateEffect) {
            is PresidentListViewModel.ViewEffect.Navigate -> {
                if (effect.route.isNotEmpty()) {
                    navController.navigate(effect.route)
                }
            }
        }
        viewModel.processEvent(PresidentListViewModel.ViewEvent.ConsumeEffect)
    }


    LazyColumn(
        modifier = modifier
            .background(colorScheme.generalColors.secondaryBackgroundColor)
            .fillMaxSize(),
        contentPadding = PaddingValues(dimens.dimenXSmall16),
        state = listState
    ) {
        if (state.loading == PresidentListViewModel.LoadingState.LoadingScreen) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimens.dimenXSmall16),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {

            item {
                CustomInputField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.searchDescription,
                    onValueChange = {
                        viewModel.processEvent(PresidentListViewModel.ViewEvent.OnSearchPresident(it))
                    },
                    placeholder = stringResource(id = R.string.president_searchbar_placeholder)
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.colombian_presidents_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.generalColors.mainTextColor,
                    modifier = Modifier.padding(vertical = dimens.dimenXSmall16)
                )
            }
            items(items = state.presidents, key = { it.id }) { item ->
                InformationBoard(
                    modifier = Modifier.height(dimens.dimenXLarge200)
                        .padding(bottom = dimens.dimenXSmall16),
                    title = item.politicalParty
                ) {
                    PresidentItem(
                        president = item,
                        onItemSelected = {
                            viewModel.processEvent(
                                PresidentListViewModel.ViewEvent.OnItemSelected(
                                    itemId = item.id
                                )
                            )
                        }
                    )
                }
            }

            if(state.loading == PresidentListViewModel.LoadingState.LoadingPartially) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimens.dimenXSmall16),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun PresidentItem(
    modifier: Modifier = Modifier,
    president: ColombiaPresident,
    onItemSelected: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onItemSelected()
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = president.image,
                contentDescription = stringResource(id = R.string.president_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(dimens.dimenXLarge100)
            )

            Column {
                Text(
                    text = "${president.name} ${president.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorScheme.generalColors.mainTextColor
                )
                Text(
                    text = "${stringResource(id = R.string.start_period_date)}  ${president.startPeriodDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.generalColors.mainTextColor
                )

                Text(
                    text = "${stringResource(id = R.string.end_period_date)}  ${president.endPeriodDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.generalColors.mainTextColor
                )

            }
        }
    }

}
