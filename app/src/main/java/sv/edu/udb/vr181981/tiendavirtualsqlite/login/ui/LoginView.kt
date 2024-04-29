package sv.edu.udb.vr181981.tiendavirtualsqlite.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.data.LoginForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.data.LoginFormErrors
import sv.edu.udb.vr181981.tiendavirtualsqlite.navigation.Routes
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.ui.RegisterViewModel
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components.CustomSpacer
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components.OutlinedTextInput
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.strings.Strings
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.Orange
import sv.edu.udb.vr181981.tiendavirtualsqlite.ui.theme.poppinsFamily

@Composable
fun LoginView(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
    val loginFormData by viewModel.loginFormData.collectAsState()
    val loginFormErrors by viewModel.loginFormErrors.collectAsState()

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
            GrayText(Strings.iniciaSesionText)
            CustomSpacer()
            EmailInput(
                viewModel,
                loginFormData,
                loginFormErrors
            )
            CustomSpacer(height = 15.dp)
            PasswordInput(
                viewModel,
                loginFormData,
                loginFormErrors
            )
            CustomSpacer(height = 20.dp)
            LoginButton(viewModel, navController)
            CustomSpacer(height = 20.dp)
            Divider(color = Color.Gray.copy(alpha = .1f))
            CustomSpacer(height = 10.dp)
            GrayText(Strings.notienescuenta)
            CustomSpacer()
            RegisterButton(navController)
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
        painter = painterResource(id = R.drawable.runner),
        contentDescription = Strings.techStorm,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EmailInput(
    viewModel: LoginViewModel,
    loginFormData: LoginForm,
    loginFormErrors: LoginFormErrors
) {
    OutlinedTextInput(
        value = loginFormData.correo,
        onValueChange = { viewModel.onChangeCorreo(it) },
        label = Strings.emailInputLabel,
        keyboardType = KeyboardType.Email,
        isError = !loginFormErrors.emailError.isNullOrBlank(),
        errorMessage = loginFormErrors.emailError
    )
}

@Composable
private fun PasswordInput(
    viewModel: LoginViewModel,
    loginFormData: LoginForm,
    loginFormErrors: LoginFormErrors
) {
    OutlinedTextInput(
        value = loginFormData.password,
        onValueChange = { viewModel.onChangePassword(it) },
        label = Strings.passwordInputLabel,
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isError = !loginFormErrors.passwordError.isNullOrBlank(),
        errorMessage = loginFormErrors.passwordError
    )
}

@Composable
private fun LoginButton(viewModel: LoginViewModel, navController: NavHostController) {
    Button(
        onClick = { viewModel.submitForm(navController) },
        colors = ButtonDefaults.buttonColors(containerColor = Orange),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = Strings.loginBtnText,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
private fun GrayText(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        lineHeight = 14.sp
    )
}

@Composable
private fun RegisterButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(Routes.RegisterRoute) },
        colors = ButtonDefaults.buttonColors(containerColor = Orange.copy(alpha = .2f)),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = Strings.registerBtnText,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Orange,
            modifier = Modifier.padding(5.dp)
        )
    }
}
