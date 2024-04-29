package sv.edu.udb.vr181981.tiendavirtualsqlite.register.data

data class RegisterFormErrors(
    val nombreError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)
