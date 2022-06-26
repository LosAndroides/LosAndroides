package com.losandroides.learn.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.losandroides.learn.R
import com.losandroides.learn.domain.model.Item
import com.losandroides.learn.ui.theme.LosAndroidesTheme

@Composable
fun ItemListScreen(
    viewModelState: MainViewModel.ViewState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        },
        scaffoldState = scaffoldState,
    ) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            when (viewModelState) {
                is MainViewModel.ViewState.Content -> ItemList(viewModelState.items)
                is MainViewModel.ViewState.Error -> {
                    val context = LocalContext.current
                    LaunchedEffect(scaffoldState.snackbarHostState) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.resources.getString(R.string.error_fetching_data)
                        )
                    }
                }
                is MainViewModel.ViewState.Loading -> Loader()
            }
        }
    }
}

@Composable
fun ItemList(itemList: List<Item>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .testTag("itemList"),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
    ) {
        items(itemList) { item ->
            Text(text = item.title)
        }
    }
}

@Composable
fun Loader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag("loader")
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsListPreview() {
    val itemList = listOf(
        Item(
            title = "Item 1"
        ),
        Item(
            title = "Item 2"
        ),
        Item(
            title = "Item 3"
        ),
        Item(
            title = "Item 4"
        ),
    )
    LosAndroidesTheme {
        ItemList(itemList = itemList)
    }
}
