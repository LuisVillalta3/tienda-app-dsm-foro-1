@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.Orange
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.poppinsFamily

@Composable
fun OutlinedTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = "",
) {
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        maxLines = 1,
        singleLine = true,
        label = {
            Text(
                text = label, fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Orange,
            unfocusedBorderColor = Orange,
            focusedLabelColor = Orange,
            unfocusedLabelColor = Orange,
            cursorColor = Orange
        ),
        textStyle = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Orange
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = isError,
        trailingIcon = {
            if (isPassword) {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password",
                            tint = Orange
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password",
                            tint = Orange
                        )
                    }
                }
            }
        }
    )

    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.Red,
            fontFamily = poppinsFamily
        )
    }
}