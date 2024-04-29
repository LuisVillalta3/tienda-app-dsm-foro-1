package sv.edu.udb.vr181981.tiendavirtualsqlite.register.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.SessionManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.navigation.Routes
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.data.RegisterForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.data.RegisterFormErrors
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.domain.RegisterController

class RegisterViewModel : ViewModel() {
    private val _registerFormData = MutableStateFlow(RegisterForm())
    val registerFormData: StateFlow<RegisterForm> = _registerFormData.asStateFlow()

    private val _registerFormErrors = MutableStateFlow(RegisterFormErrors())
    val registerFormErrors: StateFlow<RegisterFormErrors> = _registerFormErrors.asStateFlow()

    private fun onValueChange(
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        _registerFormData.update { currentState ->
            currentState.copy(
                nombre = nombre,
                correo = email,
                password = password,
                confirmPassword = confirmPassword
            )
        }
    }

    private fun resetNombreErrors() {
        _registerFormErrors.update { currentState ->
            currentState.copy(
                nombreError = null
            )
        }
    }

    private fun resetConfirmPasswordErrors() {
        _registerFormErrors.update { currentState ->
            currentState.copy(
                confirmPasswordError = null
            )
        }
    }

    private fun resetCorreoErrors() {
        _registerFormErrors.update { currentState ->
            currentState.copy(
                emailError = null
            )
        }
    }

    private fun resetPasswordErrors() {
        _registerFormErrors.update { currentState ->
            currentState.copy(
                passwordError = null
            )
        }
    }

    private fun validateEmail(): Boolean {
        val emailError: String? =
            if (_registerFormData.value.correo.isEmpty()) "Ingrese su correo electrónico"
            else if (!Patterns.EMAIL_ADDRESS.matcher(_registerFormData.value.correo)
                    .matches()
            ) "Ingrese un correo electrónico valido"
            else null

        _registerFormErrors.update { currentState ->
            currentState.copy(
                emailError = emailError
            )
        }

        return emailError.isNullOrBlank()
    }

    private fun validateNombre(): Boolean {
        val error: String? =
            if (_registerFormData.value.correo.isEmpty()) "Ingrese su nombre"
            else null

        _registerFormErrors.update { currentState ->
            currentState.copy(
                nombreError = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validatePassword(): Boolean {
        val error: String? =
            if (_registerFormData.value.password.isEmpty()) "Ingrese una contraseña"
            else null

        _registerFormErrors.update { currentState ->
            currentState.copy(
                passwordError = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validateConfirmPassword(): Boolean {
        val error: String? =
            if (_registerFormData.value.password.isEmpty()) "Ingrese una contraseña"
            else if (_registerFormData.value.password != _registerFormData.value.confirmPassword) "Las contreseñas no coinciden"
            else null

        _registerFormErrors.update { currentState ->
            currentState.copy(
                confirmPasswordError = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun isValidData(): Boolean =
        validateEmail() && validatePassword() && validateConfirmPassword() && validateNombre()

    fun onChangeNombre(nombre: String) {
        resetNombreErrors()
        onValueChange(
            nombre = nombre,
            email = registerFormData.value.correo,
            password = registerFormData.value.password,
            confirmPassword = registerFormData.value.confirmPassword,
        )
    }

    fun onChangeCorreo(correo: String) {
        resetCorreoErrors()
        onValueChange(
            nombre = registerFormData.value.nombre,
            email = correo,
            password = registerFormData.value.password,
            confirmPassword = registerFormData.value.confirmPassword,
        )
    }

    fun onChangePassword(password: String) {
        resetPasswordErrors()
        onValueChange(
            nombre = registerFormData.value.nombre,
            email = registerFormData.value.correo,
            password = password,
            confirmPassword = registerFormData.value.confirmPassword,
        )
    }

    fun onChangeConfirmPassword(password: String) {
        resetConfirmPasswordErrors()
        onValueChange(
            nombre = registerFormData.value.nombre,
            email = registerFormData.value.correo,
            password = registerFormData.value.password,
            confirmPassword = password,
        )
    }

    fun submitForm(navController: NavController) {
        if (!isValidData()) return
        if (RegisterController.existsUserWithEmail(registerFormData.value.correo)) {
            _registerFormErrors.update { currentState ->
                currentState.copy(
                    emailError = "Este correo ya existe"
                )
            }
            return
        }

        val res = RegisterController.createNewUser(registerFormData.value)

        if (res.toInt() != -1) {
            val sessionManager = SessionManager(MyApp.instance.applicationContext)
            sessionManager.setLoggedIn(true)

            navController.navigate(Routes.HomeRoute) {
                launchSingleTop = true
                popUpTo(Routes.RegisterRoute) { inclusive = true }
                popUpTo(Routes.LoginRoute) { inclusive = true }
            }
        }
    }
}
