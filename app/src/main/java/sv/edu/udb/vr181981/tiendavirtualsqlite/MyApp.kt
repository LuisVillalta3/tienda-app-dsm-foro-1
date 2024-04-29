package sv.edu.udb.vr181981.tiendavirtualsqlite

import android.app.Application

class MyApp: Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}