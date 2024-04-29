package sv.edu.udb.vr181981.tiendavirtualsqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

class DbManager<T : Any>(context: Context, clazz: Class<T>) {
    private val DATABASE_NAME = "my_database"
    private val TABLE_NAME: String
    private val DATABASE_VERSION = 2

    private var database: SQLiteDatabase

    private val myclazz = clazz

    init {
        // Obtenemos el nombre de la clase T en tiempo de ejecución para usarlo como nombre de tabla
        TABLE_NAME = clazz.simpleName

        // Inicializamos la base de datos
        val dbHelper = DatabaseHelper(context, clazz)
        database = dbHelper.writableDatabase
    }

    fun insert(item: T): Long {
        val contentValues = getContentValues(item)
        return database.insert(TABLE_NAME, null, contentValues)
    }

    fun update(item: T, whereClause: String, whereArgs: Array<String>?): Int {
        val contentValues = getContentValues(item)
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>?): Int {
        return database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getAll(): List<T> {
        val items = mutableListOf<T>()
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val item = mapCursorToObject(it)
                items.add(item)
            }
        }
        return items
    }

    fun countData(): Int {
        val cursor = database.query(TABLE_NAME, null, null, null, null, null, null)
        return cursor.count
    }

    fun findBy(prop: String, value: Any): List<T> {
        val items = mutableListOf<T>()

        val selection = "$prop = ?"
        val selectionArgs = arrayOf(value.toString())
        val cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val item = mapCursorToObject(it)
                items.add(item)
            }
        }
        return items
    }

    private fun mapCursorToObject(cursor: Cursor): T {
        // Implementa lógica para convertir Cursor a objeto de tipo T
        val instance = myclazz.kotlin.createInstance()
        val columns = cursor.columnNames
        val fields = myclazz.declaredFields

        for (field in fields) {
            if (columns.contains(field.name)) {
                val columnIndex = cursor.getColumnIndex(field.name)
                field.isAccessible = true
                when (field.type) {
                    Int::class.java, Integer::class.java -> field.set(instance, cursor.getInt(columnIndex))
                    Long::class.java, java.lang.Long::class.java -> field.set(instance, cursor.getLong(columnIndex))
                    String::class.java -> field.set(instance, cursor.getString(columnIndex))
                    Double::class.java -> field.set(instance, cursor.getDouble(columnIndex))
                    // Añade más casos según los tipos de datos que necesites manejar
                    // Puedes manejar más tipos de datos como Double, Float, etc.
                    else -> throw UnsupportedOperationException("Tipo de dato no soportado: ${field.type}")
                }
            }
        }

        return instance
    }

    private fun getContentValues(item: T): ContentValues {
        // Implementa lógica para convertir objeto de tipo T a ContentValues
        // Ejemplo: contentValues.put("column_name", item.property)
        val contentValues = ContentValues()

        // Obtener las propiedades de la data class usando reflexión
        val properties = item!!::class.members
        for (prop in properties) {
            val propName = prop.name
            val propValue = item!!::class.members
                .filterIsInstance<KProperty1<T, *>>()
                .firstOrNull { it.name == propName }
                ?.get(item)

            // Agregar el nombre y valor de la propiedad al ContentValues
            when (propValue) {
                is String -> contentValues.put(propName, propValue)
                is Int -> contentValues.put(propName, propValue)
                is Long -> contentValues.put(propName, propValue)
                is Float -> contentValues.put(propName, propValue)
                is Double -> contentValues.put(propName, propValue)
            }
        }

        return contentValues
    }

    private fun getColumnType(type: KType): String {
        return when (type.classifier) {
            String::class -> "TEXT"
            Int::class -> "INTEGER"
            Long::class -> "INTEGER"
            Float::class -> "REAL"
            Double::class -> "REAL"
            // Añade otros tipos de datos según sea necesario
            else -> "TEXT" // Tipo por defecto si no se reconoce
        }
    }

    private fun getPropertiesTypeString(clazz: Class<T>): String {
        val instance = clazz.kotlin.createInstance()
        val properties = instance::class.memberProperties
        return properties.filter { it.name != "id" }.joinToString(", ") { prop ->
            val propName = prop.name
            val propType = getColumnType(prop.returnType)
            "$propName $propType"
        }
    }

    private inner class DatabaseHelper(context: Context, val clazz: Class<T>) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, name Text, email Text, password Text)")
            db.execSQL("CREATE TABLE Product (id INTEGER PRIMARY KEY AUTOINCREMENT, name Text, price REAL)")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // Maneja la actualización de la base de datos si es necesario
            db.execSQL("DROP TABLE IF EXISTS User")
            db.execSQL("DROP TABLE IF EXISTS Product")
            onCreate(db)
        }
    }
}