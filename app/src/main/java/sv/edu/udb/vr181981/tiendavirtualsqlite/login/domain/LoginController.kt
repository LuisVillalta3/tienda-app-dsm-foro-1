package sv.edu.udb.vr181981.tiendavirtualsqlite.login.domain

import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.User
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories.UserRepository
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.domain.RegisterController

class LoginController {
    companion object {
        private val userRepository = UserRepository(MyApp.instance.applicationContext)

        fun getUserByEmail(email: String): User? {
            val users = userRepository.findUserByEmail(email)
            if (users.isEmpty()) return null
            return users.first()
        }
    }
}