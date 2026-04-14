package dev.alexmester.impl.presentation.interests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.alexmester.impl.presentation.interests.mvi.InterestsIntent
import dev.alexmester.impl.presentation.interests.mvi.InterestsSideEffect
import dev.alexmester.impl.presentation.interests.mvi.InterestsState
import dev.alexmester.impl.presentation.interests.mvi.InterestsViewModel
import dev.alexmester.ui.R
import dev.alexmester.ui.components.buttons.LaskBackButton
import dev.alexmester.ui.components.buttons.LaskChipButton
import dev.alexmester.ui.components.buttons.LaskChipButtonVariants
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun InterestsScreen(
    onBack: () -> Unit,
    viewModel: InterestsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                InterestsSideEffect.NavigateBack -> onBack()
            }
        }
    }

    InterestsScreenContent(
        state = state,
        onIntent = viewModel::handleIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InterestsScreenContent(
    state: InterestsState,
    onIntent: (InterestsIntent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    LaskBackButton(onClick = { onIntent(InterestsIntent.Back) })
                },
                title = {
                    Text(
                        text = stringResource(R.string.profile_menu_interests),
                        style = MaterialTheme.LaskTypography.h5,
                        color = MaterialTheme.LaskColors.textPrimary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.LaskColors.brand_blue10,
                ),
            )
        },
        containerColor = MaterialTheme.LaskColors.backgroundPrimary,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = state.inputText,
                    onValueChange = { onIntent(InterestsIntent.OnInputChange(it)) },
                    textStyle = MaterialTheme.LaskTypography.body2,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = "Write you interests",
                            style = MaterialTheme.LaskTypography.body2,
                            color = MaterialTheme.LaskColors.textSecondary,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Interests,
                            contentDescription = null,
                            tint = MaterialTheme.LaskColors.textSecondary,
                        )
                    },
                    trailingIcon = {
                        if (state.inputText.isNotEmpty()) {
                            IconButton(onClick = { onIntent(InterestsIntent.OnInputChange("")) }) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = MaterialTheme.LaskColors.textSecondary,
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.LaskColors.brand_blue,
                        unfocusedBorderColor = MaterialTheme.LaskColors.brand_blue10,
                        focusedTextColor = MaterialTheme.LaskColors.textPrimary,
                        unfocusedTextColor = MaterialTheme.LaskColors.textPrimary,
                        cursorColor = MaterialTheme.LaskColors.brand_blue,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (state.canAdd) {
                                onIntent(InterestsIntent.Add)
                                keyboard?.hide()
                            }
                        }
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )

                TextButton(
                    onClick = {
                        if (state.canAdd) {
                            onIntent(InterestsIntent.Add)
                            keyboard?.hide()
                        }
                    },
                    enabled = state.canAdd,
                ) {
                    Text(
                        text = "Add",
                        style = MaterialTheme.LaskTypography.button1,
                        color = if (state.canAdd)
                            MaterialTheme.LaskColors.brand_blue
                        else
                            MaterialTheme.LaskColors.textSecondary,
                    )
                }
            }

            // ── Added Interests ───────────────────────────────────────────
            if (state.interests.isNotEmpty()) {
                Text(
                    text = "You Interests",
                    style = MaterialTheme.LaskTypography.footnote,
                    color = MaterialTheme.LaskColors.textSecondary,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    state.interests.forEach { keyword ->
                        LaskChipButton(
                            modifier = Modifier,
                            text = keyword,
                            variant = LaskChipButtonVariants.Interests,
                            onDismiss = { onIntent(InterestsIntent.Remove(keyword)) }
                        )
                    }
                }
            }
        }
    }
}
