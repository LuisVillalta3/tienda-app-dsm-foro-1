package sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories

import android.content.Context
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.DbManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.User

class UserRepository(context: Context) {
    private val dbManager: DbManager<User> = DbManager(context, User::class.java)

    fun addUser(user: User): Long {
        return dbManager.insert(user)
    }

    fun findUserByEmail(email: String): List<User> {
        return dbManager.findBy("email", email)
    }
}