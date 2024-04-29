package sv.edu.udb.vr181981.tiendavirtualsqlite.home.domain

import sv.edu.udb.vr181981.tiendavirtualsqlite.MyApp
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.models.Product
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories.ProductRepository
import sv.edu.udb.vr181981.tiendavirtualsqlite.db.repositories.UserRepository
import sv.edu.udb.vr181981.tiendavirtualsqlite.mocks.ProductMocks

class HomeController {
    companion object {
        private val productRepository = ProductRepository(MyApp.instance.applicationContext)

        init {
            populateDatabase()
        }

        private fun populateDatabase() {
            if (!productRepository.hasData()) {
                ProductMocks.fakeProducts.forEach {
                    productRepository.addProduct(it)
                }
            }
        }

        fun getAllProducts(): List<Product> {
            return productRepository.getAllProducts()
        }
    }
}