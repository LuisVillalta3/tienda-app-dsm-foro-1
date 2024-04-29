package sv.edu.udb.vr181981.tiendavirtualsqlite.register.domain

import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.User
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories.UserRepository
import sv.edu.udb.vr181981.tiendavirtualsqlite.register.data.RegisterForm
import sv.edu.udb.vr181981.tiendavirtualsqlite.utils.Utils

class RegisterController {
    companion object {
        private val userRepository = UserRepository(MyApp.instance.applicationContext)

        fun createNewUser(userFormData: RegisterForm): Long {
            val newUser = User(
                name = userFormData.nombre,
                email = userFormData.correo,
                password = Utils.encryptPassword(userFormData.password),
                id = null
            )

            return userRepository.addUser(newUser)
        }

        fun existsUserWithEmail(email: String): Boolean {
            val users = userRepository.findUserByEmail(email)
            if (users.isEmpty()) return false
            return true
        }
    }
}