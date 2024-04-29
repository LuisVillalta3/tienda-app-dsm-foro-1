package sv.edu.udb.vr181981.tiendavirtualsqlite.db.models

data class User(
    val id: Int? = null,
    val name: String = "",
    val email: String = "",
    val password: String = ""
)
