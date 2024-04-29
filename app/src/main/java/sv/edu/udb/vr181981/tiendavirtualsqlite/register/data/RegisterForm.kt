package sv.edu.udb.vr181981.tiendavirtualsqlite.register.data

data class RegisterForm(
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
