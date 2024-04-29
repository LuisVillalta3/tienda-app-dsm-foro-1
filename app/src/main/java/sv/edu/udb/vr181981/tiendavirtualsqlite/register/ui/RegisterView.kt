package sv.edu.udb.vr181981.tiendavirtualsqlite.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.tiendavirtualsqlite.R
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.data.RegisterForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.data.RegisterFormErrors
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components.CustomSpacer
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components.OutlinedTextInput
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.strings.Strings
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.Orange
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.poppinsFamily

@Composable
fun RegisterView(navController: NavHostController, viewModel: RegisterViewModel = viewModel()) {
    val registerFormData by viewModel.registerFormData.collectAsState()
    val registerFormErrors by viewModel.registerFormErrors.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        RunnerImage()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            ImageContainer()
            CustomSpacer(height = 20.dp)
            GrayText()
            CustomSpacer()
            NameInput(viewModel, registerFormData, registerFormErrors)
            CustomSpacer(height = 15.dp)
            EmailInput(viewModel, registerFormData, registerFormErrors)
            CustomSpacer(height = 15.dp)
            PasswordInput(viewModel, registerFormData, registerFormErrors)
            CustomSpacer(height = 15.dp)
            ConfirmPasswordInput(viewModel, registerFormData, registerFormErrors)
            CustomSpacer(height = 20.dp)
            RegisterButton(viewModel, navController)
        }
    }
}

@Composable
private fun ImageContainer() {
    Image(
        painter = painterResource(id = R.drawable.techstorm),
        contentDescription = Strings.techStorm,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun RunnerImage() {
    Image(
        painter = painterResource(id = R.drawable.runner2),
        contentDescription = Strings.techStorm,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EmailInput(
    viewModel: RegisterViewModel,
    registerFormData: RegisterForm,
    registerFormErrors: RegisterFormErrors
) {
    OutlinedTextInput(
        value = registerFormData.correo,
        onValueChange = { viewModel.onChangeCorreo(it) },
        label = Strings.emailInputLabel,
        keyboardType = KeyboardType.Email,
        isError = !registerFormErrors.emailError.isNullOrBlank(),
        errorMessage = registerFormErrors.emailError
    )
}

@Composable
private fun PasswordInput(
    viewModel: RegisterViewModel,
    registerFormData: RegisterForm,
    registerFormErrors: RegisterFormErrors
) {
    OutlinedTextInput(
        value = registerFormData.password,
        onValueChange = { viewModel.onChangePassword(it) },
        label = Strings.passwordInputLabel,
        keyboardType = KeyboardType.Password,
        isPassword = true,
        errorMessage = registerFormErrors.passwordError,
        isError = !registerFormErrors.passwordError.isNullOrBlank()
    )
}

@Composable
private fun NameInput(
    viewModel: RegisterViewModel,
    registerFormData: RegisterForm,
    registerFormErrors: RegisterFormErrors
) {
    OutlinedTextInput(
        value = registerFormData.nombre,
        onValueChange = { viewModel.onChangeNombre(it) },
        label = Strings.nombreInputLabel,
        keyboardType = KeyboardType.Text,
        isError = !registerFormErrors.nombreError.isNullOrBlank(),
        errorMessage = registerFormErrors.nombreError
    )
}

@Composable
private fun ConfirmPasswordInput(
    viewModel: RegisterViewModel,
    registerFormData: RegisterForm,
    registerFormErrors: RegisterFormErrors
) {
    OutlinedTextInput(
        value = registerFormData.confirmPassword,
        onValueChange = { viewModel.onChangeConfirmPassword(it) },
        label = Strings.confirmPasswordInputLabel,
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isError = !registerFormErrors.confirmPasswordError.isNullOrBlank(),
        errorMessage = registerFormErrors.confirmPasswordError
    )
}

@Composable
private fun RegisterButton(viewModel: RegisterViewModel, navController: NavHostController) {
    Button(
        onClick = { viewModel.submitForm(navController) },
        colors = ButtonDefaults.buttonColors(containerColor = Orange),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = Strings.registerBtnText,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun GrayText() {
    Text(
        text = Strings.registrarseText,
        fontSize = 12.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        lineHeight = 14.sp
    )
}
