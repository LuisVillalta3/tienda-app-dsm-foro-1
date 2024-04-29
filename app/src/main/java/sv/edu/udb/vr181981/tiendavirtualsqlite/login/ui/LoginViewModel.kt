package sv.edu.udb.vr181981.tiendavirtualsqlite.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.SessionManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.data.LoginForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.data.LoginFormErrors
import sv.edu.udb.vr181981.tiendavirtualsqlite.login.domain.LoginController
import sv.edu.udb.vr181981.tiendavirtualsqlite.navigation.Routes
import sv.edu.udb.vr181981.tiendavirtualsqlite.utils.Utils

class LoginViewModel : ViewModel() {
    private val _loginFormData = MutableStateFlow(LoginForm())
    val loginFormData: StateFlow<LoginForm> = _loginFormData.asStateFlow()

    private val _loginFormErrors = MutableStateFlow(LoginFormErrors())
    val loginFormErrors: StateFlow<LoginFormErrors> = _loginFormErrors.asStateFlow()

    private fun onValueChange(
        email: String,
        password: String
    ) {
        _loginFormData.update { currentState ->
            currentState.copy(
                correo = email,
                password = password,
            )
        }
    }

    private fun resetCorreoErrors() {
        _loginFormErrors.update { currentState ->
            currentState.copy(
                emailError = null
            )
        }
    }

    private fun resetPasswordErrors() {
        _loginFormErrors.update { currentState ->
            currentState.copy(
                passwordError = null
            )
        }
    }

    private fun validateEmail(): Boolean {
        val emailError: String? =
            if (_loginFormData.value.correo.isEmpty()) "Ingrese su correo electrónico"
            else if (!Patterns.EMAIL_ADDRESS.matcher(_loginFormData.value.correo)
                    .matches()
            ) "Ingrese un correo electrónico valido"
            else null

        _loginFormErrors.update { currentState ->
            currentState.copy(
                emailError = emailError
            )
        }

        return emailError.isNullOrBlank()
    }

    private fun validatePassword(): Boolean {
        val error: String? =
            if (_loginFormData.value.password.isEmpty()) "Ingrese una contraseña"
            else null

        _loginFormErrors.update { currentState ->
            currentState.copy(
                passwordError = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun isValidData(): Boolean =
        validateEmail() && validatePassword()

    fun onChangeCorreo(correo: String) {
        resetCorreoErrors()
        onValueChange(
            email = correo,
            password = loginFormData.value.password,
        )
    }

    fun onChangePassword(password: String) {
        resetPasswordErrors()
        onValueChange(
            email = loginFormData.value.correo,
            password = password,
        )
    }

    fun submitForm(navController: NavController) {
        if (!isValidData()) return

        val user = LoginController.getUserByEmail(_loginFormData.value.correo)

        if (user == null) {
            _loginFormErrors.update { currentState ->
                currentState.copy(
                    emailError = "Usuario no encontrado"
                )
            }
            return
        }

        if (user.password != Utils.encryptPassword(_loginFormData.value.password)) {
            _loginFormErrors.update { currentState ->
                currentState.copy(
                    passwordError = "Contraseña incorrecta"
                )
            }
            return
        }

        val sessionManager = SessionManager(MyApp.instance.applicationContext)
        sessionManager.setLoggedIn(true)

        navController.navigate(Routes.HomeRoute) {
            launchSingleTop = true
            popUpTo(Routes.RegisterRoute) { inclusive = true }
            popUpTo(Routes.LoginRoute) { inclusive = true }
        }
    }
}
