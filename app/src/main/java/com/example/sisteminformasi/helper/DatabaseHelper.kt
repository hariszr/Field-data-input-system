package com.example.sisteminformasi.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.sisteminformasi.model.Users
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper (context: Context) :
SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "simultan.db"
        private const val TABLE_NAME = "database_users"

        private const val COLUMN_ID = "id"
        private const val COLUMN_PROVINSI = "prop"
        private const val COLUMN_KABUPATEN = "kab"
        private const val COLUMN_KECAMATAN = "kec"
        private const val COLUMN_DESA = "desa"
        private const val COLUMN_GAB_KODE = "gab_kode"
        private const val COLUMN_NAMA_POKTAN = "nama_poktan"

        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "kodwil"

    }

    private val DB_PATH: String = context.getDatabasePath(DATABASE_NAME).absolutePath

    init {
        copyDatabaseFromAssets(context)
    }

    private fun copyDatabaseFromAssets(context: Context) {
        try {
            val inputStream = context.assets.open(DATABASE_NAME)
            val outputStream = FileOutputStream(DB_PATH)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    fun getUser(username: String, password: String): Users? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?", arrayOf(username, password))

        var user: Users? = null

        if (cursor.moveToFirst()) {
            user = Users(
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                prop = cursor.getString(cursor.getColumnIndex(COLUMN_PROVINSI)),
                kab = cursor.getString(cursor.getColumnIndex(COLUMN_KABUPATEN)),
                kec = cursor.getString(cursor.getColumnIndex(COLUMN_KECAMATAN)),
                desa = cursor.getString(cursor.getColumnIndex(COLUMN_DESA)),
                nama_poktan = cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_POKTAN)),
                gab_kode = cursor.getString(cursor.getColumnIndex(COLUMN_GAB_KODE)),
                username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                kodwil = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                // tambahkan kolom lainnya jika diperlukan
            )
        }

        cursor.close()
        return user
    }

//    private fun copyDatabaseFromAssets(context: Context) {
//        val dbFile = File(DB_PATH)
//        if (!dbFile.exists()) {
//            try {
//                val inputStream = context.assets.open(DATABASE_NAME)
//                val outputStream = FileOutputStream(dbFile)
//
//                val buffer = ByteArray(1024)
//                var length: Int
//                while (inputStream.read(buffer).also { length = it } > 0) {
//                    outputStream.write(buffer, 0, length)
//                }
//
//                outputStream.flush()
//                outputStream.close()
//                inputStream.close()
//
//                // Tambahkan log untuk memverifikasi bahwa database berhasil disalin
//                Log.d("DatabaseHelper", "Database copied to $DB_PATH")
//            } catch (e: IOException) {
//                e.printStackTrace()
//
//                // Tambahkan penanganan eksepsi yang lebih spesifik di sini
//                Log.e("DatabaseHelper", "Error copying database: ${e.message}")
//
//                // Contoh penanganan eksepsi dengan memunculkan Toast dengan pesan kesalahan
//                showToast(context, "Gagal menyalin database: ${e.message}")
//
//                // Atau lemparkan pengecualian yang lebih spesifik
//                throw DatabaseCopyException("Error copying database", e)
//            }
//        }
////        else {
////            Log.d("DatabaseHelper", "Database tidak copied to $DB_PATH")
////        }
//    }

    // Tambahkan kelas DatabaseCopyException
    class DatabaseCopyException(message: String, cause: Throwable) : RuntimeException(message, cause)

    // Tambahkan fungsi untuk menampilkan Toast
    fun showToast(context: Context, message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

//    override fun onCreate(db: SQLiteDatabase?) {
//        val createTableQuery =
//            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "$COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
//        db?.execSQL(createTableQuery)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db)
//    }

    fun addUser(username: String, kodwil: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USERNAME, username)
        contentValues.put(COLUMN_PASSWORD, kodwil)

        return db.insert(TABLE_NAME, null, contentValues)
    }

    override fun onCreate(db: SQLiteDatabase?) {
//        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
    }

//    @SuppressLint("Recycle")
//    fun getUser(username: String, kodwil: String): Boolean {
//        val db = this.readableDatabase
//        val columns = arrayOf(COLUMN_ID)
//        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
//        val selectionArgs = arrayOf(username, kodwil)
//
//        val cursor: Cursor = db.query(
//            TABLE_NAME,
//            columns,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//
//        val cursorCount = cursor.count
//        cursor.close()
//
//        // Log atau cetak nilai count untuk memeriksa hasil
//        Log.d("DatabaseHelper", "getUser count: $cursorCount")
//
//        Log.d("DatabaseHelper", "getUser query: ${db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)}")
//        Log.d("DatabaseHelper", "getUser selectionArgs: ${selectionArgs.joinToString(", ")}")
//        return cursorCount > 0
//    }
}