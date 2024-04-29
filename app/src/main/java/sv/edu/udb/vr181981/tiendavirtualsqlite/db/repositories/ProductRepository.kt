package sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories

import android.content.Context
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.DbManager
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.Product
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.User

class ProductRepository(context: Context) {
    private val dbManager: DbManager<Product> = DbManager(context, Product::class.java)

    fun addProduct(product: Product): Long {
        return dbManager.insert(product)
    }

    fun getAllProducts(): List<Product> {
        return dbManager.getAll()
    }

    fun hasData(): Boolean {
        return dbManager.countData() > 0
    }
}