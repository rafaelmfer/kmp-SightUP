package com.europa.sightup.presentation.screens.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.europa.sightup.presentation.designsystem.components.SDSBottomSheet
import com.europa.sightup.presentation.designsystem.components.data.BottomSheetEnum
import com.europa.sightup.presentation.designsystem.components.hideBottomSheetWithAnimation
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.layout.spacing
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.login_title
import sightupkmpapp.composeapp.generated.resources.sign_up_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSignUpScreen(
    bottomSheetVisible: BottomSheetEnum,
    onBottomSheetVisibilityChange: (BottomSheetEnum) -> Unit,
    onSuccessfulLogin: () -> Unit,
    navController: NavController? = null,
) {
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )

    val title = stringResource(Res.string.login_title)
    var bottomSheetTitle by remember { mutableStateOf(title) }

    SDSBottomSheet(
        isDismissible = true,
        expanded = bottomSheetVisible,
        onExpandedChange = {
            onBottomSheetVisibilityChange(it)
        },
        onDismiss = {
            viewModel.resetLoginState()
        },
        sheetState = sheetState,
        fullHeight = true,
        title = bottomSheetTitle,
        iconRightVisible = true,
        onIconRightClick = {
            scope.hideBottomSheetWithAnimation(
                sheetState = sheetState,
                onBottomSheetVisibilityChange = onBottomSheetVisibilityChange,
                onFinish = {
                    viewModel.resetLoginState()
                }
            )
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SightUPTheme.spacing.spacing_side_margin)
            ) {
                when (state) {
                    is LoginUIState.InitialState -> {
                        error = ""
                        LoginEmailSheetContent(
                            onContinueClicked = {
                                email = it
                                viewModel.checkEmail(it)
                            },
                            onGoogleClicked = {
                                viewModel.doLoginWithProvider(it)
                            },
                            errorMessage = error
                        )
                    }

                    is LoginUIState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is LoginUIState.UserFound -> {
                        error = ""
                        bottomSheetTitle = stringResource(Res.string.sign_up_title)
                        LoginPasswordSheetContent(
                            onContinueClicked = {
                                viewModel.doLogin(email, it)
                            },
                            errorMessage = error
                        )
                    }

                    is LoginUIState.PasswordError -> {
                        error = (state as LoginUIState.PasswordError).errorMessage
                        LoginPasswordSheetContent(
                            onContinueClicked = {
                                viewModel.doLogin(email, it)
                            },
                            errorMessage = error
                        )
                    }

                    is LoginUIState.LoginSuccess -> {
                        scope.hideBottomSheetWithAnimation(
                            sheetState = sheetState,
                            onBottomSheetVisibilityChange = onBottomSheetVisibilityChange,
                            onFinish = {
                                onSuccessfulLogin()
                            }
                        )
                    }

                    is LoginUIState.Error -> {
                        error = (state as LoginUIState.Error).errorMessage
                        LoginEmailSheetContent(
                            onContinueClicked = {
                                email = it
                                viewModel.checkEmail(it)
                            },
                            onGoogleClicked = {
                                viewModel.doLoginWithProvider(it)
                            },
                            errorMessage = error
                        )
                    }
                }
            }
        }
    )
}