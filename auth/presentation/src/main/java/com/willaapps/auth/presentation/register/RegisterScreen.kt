@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.auth.domain.PasswordValidationState
import com.willaapps.auth.domain.UserDataValidator
import com.willaapps.auth.presentation.R
import com.willaapps.auth.presentation.register.components.PasswordRequirement
import com.willaapps.core.presentation.designsystem.CheckIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwPasswordTextField
import com.willaapps.core.presentation.designsystem.components.JwTextField
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onBackClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnSignInClick -> onSignInClick()
                RegisterAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GradientBall(
            gradientColor = Color(0xFF119DA4),
            offsetY = 200f
        )
        Column {
            JwToolbar(
                text = stringResource(id = R.string.register),
                onBackClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 100f,
                            topEnd = 100f
                        )
                    )
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                JwTextField(
                    state = state.username,
                    endIcon = if (state.isUsernameValid) {
                        CheckIcon
                    } else null,
                    hint = stringResource(R.string.enter_your_username),
                    title = stringResource(R.string.username),
                    additionalInfo = stringResource(R.string.username_can_t_be_empty),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                JwTextField(
                    state = state.email,
                    endIcon = if (state.isEmailValid) {
                        CheckIcon
                    } else null,
                    hint = stringResource(R.string.enter_your_email),
                    title = stringResource(R.string.email),
                    additionalInfo = stringResource(R.string.must_be_a_valid_email),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                JwPasswordTextField(
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = {
                        onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                    },
                    hint = stringResource(R.string.enter_your_password),
                    title = stringResource(R.string.password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordRequirement(
                    text = stringResource(
                        id = R.string.at_least_x_characters,
                        UserDataValidator.MIN_PASSWORD_LENGTH
                    ),
                    isValid = state.passwordValidationState.hasMinLength
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(R.string.at_least_one_number),
                    isValid = state.passwordValidationState.hasNumber
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(R.string.contains_lowercase_character),
                    isValid = state.passwordValidationState.hasLowerCaseCharacter
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRequirement(
                    text = stringResource(R.string.contrains_uppercase_character),
                    isValid = state.passwordValidationState.hasUpperCaseCharacter
                )
                Spacer(modifier = Modifier.weight(1f))
                ActionButton(
                    text = stringResource(id = R.string.sign_up),
                    isLoading = state.isRegistering,
                    enabled = state.canRegister && !state.isRegistering,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAction(RegisterAction.OnSignUpClick)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF5E5E5E),
                            fontSize = 14.sp
                        )
                    ) {
                        append(stringResource(R.string.already_have_an_account) + " ")
                    }
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(R.string.sign_in)
                    )
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF121211),
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append(stringResource(id = R.string.sign_in))
                    }
                }
                ClickableText(
                    text = annotatedString,
                    style = TextStyle(
                        textAlign = TextAlign.Center
                    ),
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            onAction(RegisterAction.OnSignInClick)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    JustWords2Theme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true,
                    hasMinLength = true,
                    hasLowerCaseCharacter = true,
                    hasUpperCaseCharacter = true
                )
            ),
            onAction = {}
        )
    }
}