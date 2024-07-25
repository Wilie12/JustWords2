@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.TextObfuscationMode
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.InvisibleIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.R
import com.willaapps.core.presentation.designsystem.VisibleIcon

@Composable
fun JwPasswordTextField(
    state: TextFieldState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    hint: String,
    title: String?,
    modifier: Modifier = Modifier
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        if (title != null) {
            Text(
                text = title,
                color = Color(0xFF121211),
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        BasicSecureTextField(
            state = state,
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.Hidden
            },
            keyboardType = KeyboardType.Password,
            textStyle = LocalTextStyle.current.copy(
                color = Color(0xFF121211),
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(Color(0xFF121211)),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        Color(0xFF5E5E5E).copy(
                            alpha = 0.5f
                        )
                    } else {
                        Color(0xFFBDBFB5)
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        Color(0xFF121211)
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = Color(0xFF5E5E5E).copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        innerBox()
                    }
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (!isPasswordVisible) {
                                InvisibleIcon
                            } else {
                                VisibleIcon
                            },
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.hide_password)
                            } else {
                                stringResource(id = R.string.show_password)
                            },
                            tint = Color(0xFF121211)
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun JwPasswordTextFieldPreview() {
    JustWords2Theme {
        JwPasswordTextField(
            state = rememberTextFieldState(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = { },
            hint = "Enter your password",
            title = "Password",
            modifier = Modifier.fillMaxWidth()
        )
    }
}