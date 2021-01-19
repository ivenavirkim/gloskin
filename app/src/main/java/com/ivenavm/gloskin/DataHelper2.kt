package com.ivenavm.gloskin

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataHelper2(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
         val DATABASE_NAME = "myaps.db"
         val DATABASE_VERSION = 1
         private val SQL_CREATE_USER = "CREATE TABLE " + DataInfo.UserTable.TABLE_NAME + "(" + DataInfo.UserTable.COL_EMAIL +" VARCHAR(200) PRIMARY KEY, " + DataInfo.UserTable.COL_PASS + " TEXT, " + DataInfo.UserTable.COL_FULLNAME +" TEXT, " + DataInfo.UserTable.COL_JENKAL + " VARCHAR(200), " + DataInfo.UserTable.COL_ALAMAT + "TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws (SQLiteConstraintException::class)
    fun registerUser(emailin: String, passin: String, fullnamein: String, jenkalin: String, alamatin:String){
        val db = writableDatabase
        val nametable = DataInfo.UserTable.TABLE_NAME
        val emailt = DataInfo.UserTable.COL_EMAIL
        val passt = DataInfo.UserTable.COL_PASS
        val fullnamet = DataInfo.UserTable.COL_FULLNAME
        val jenkalt = DataInfo.UserTable.COL_JENKAL
        val alamatt = DataInfo.UserTable.COL_ALAMAT
       val  sql = "INSERT INTO " + nametable + " (" + emailt + ", " + passt + ", " + fullnamet + ", " + jenkalt + ", " + alamatt + ") VALUES('" + emailin + "', '" + passin + "', '" + fullnamein + "', '" + jenkalin + "', '" + alamatin + "')"
        db.execSQL(sql)
    }
    fun cekUser(emailin: String): String {
        val db = writableDatabase
        var cursor: Cursor? = null
        var jumlah = ""
        try {
            cursor = db.rawQuery("SELECT COUNT("+ DataInfo.UserTable.COL_EMAIL +") as jumlah FROM "+ DataInfo.UserTable.TABLE_NAME + " WHERE " + DataInfo.UserTable.COL_EMAIL + "=='" + emailin +"'" , null)
        } catch (e: android.database.SQLException) {
            db.execSQL(SQL_CREATE_USER)
            return "Tabel Dibuat"
        }
        if (cursor!!.moveToFirst()){
            jumlah = cursor.getString(cursor.getColumnIndex(DataInfo.UserTable.COL_JUMLAH))
        }
        return jumlah
    }
    fun cekLogin(emailin: String, passin: String): String {
        val db = writableDatabase
        var cursor: Cursor? = null
        var jumlah = ""
        try {
            cursor = db.rawQuery("SELECT COUNT("+ DataInfo.UserTable.COL_EMAIL +") as jumlah FROM "+ DataInfo.UserTable.TABLE_NAME + " WHERE " + DataInfo.UserTable.COL_EMAIL + "=='" + emailin +"' AND " + DataInfo.UserTable.COL_PASS + "=='" + passin + "'" , null)
        } catch (e: android.database.SQLException) {
            db.execSQL(SQL_CREATE_USER)
            return "Tabel Dibuat"
        }
        if (cursor!!.moveToFirst()){
            jumlah = cursor.getString(cursor.getColumnIndex(DataInfo.UserTable.COL_JUMLAH))
        }
        return jumlah
    }
}