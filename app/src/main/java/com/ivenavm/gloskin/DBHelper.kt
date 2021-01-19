package com.ivenavm.gloskin

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import java.sql.SQLException
import java.sql.SQLTimeoutException
class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
    companion object{
        val DATABASE_NAME = "myaps.db"
        val DATABASE_VERSION = 1
        private val SQL_CREATE_USER = "CREATE TABLE " + DBInfo.UserInput.TABLE_NAME + "("+DataInfo.UserTable.COL_EMAIL+" VARCHAR(200) PRIMARY KEY, " + DataInfo.UserTable.COL_PASS + " TEXT, " +DataInfo.UserTable.COL_FULLNAME + " TEXT, " + DataInfo.UserTable.COL_JENKAL + " VARCHAR(200), " + DataInfo.UserTable.COL_ALAMAT + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DataInfo.UserTable.TABLE_NAME
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion:
    Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    @Throws(SQLiteConstraintException::class)
    fun insertData(emailin: String, passin: String, usernamein: String, fullnamein: String): Boolean {
        val db = writableDatabase
        val namatablet = DataInfo.UserTable.TABLE_NAME
        val emailt = DataInfo.UserTable.COL_EMAIL
        val passt = DataInfo.UserTable.COL_PASS
        val usernamet = DataInfo.UserTable.TABLE_NAME
        val fullnamet = DataInfo.UserTable.COL_FULLNAME
        var sql = "INSERT INTO "+ namatablet +"("+emailt+", "+passt+", "+usernamet+", "+fullnamet+") " +
                "VALUES('"+emailin+"', '"+passin+"', '"+usernamein+"', '"+fullnamein+"')"
        db.execSQL(sql)
        return true
    }
    fun fullData():ArrayList<DBModel> {
//        val users = ArrayList<DBModel>()
        val users = arrayListOf<DBModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM "+DBInfo.UserInput.TABLE_NAME, null)
        } catch (e: android.database.SQLException) {
            db.execSQL(SQL_CREATE_USER)
            return ArrayList()
        }

        var emailt: String
        var passt: String
        var usernamet: String
        var fullnamet: String
        if (cursor!!.moveToFirst()){
            while (cursor.isAfterLast==false){
                emailt = cursor.getString(cursor.getColumnIndex(DBInfo.UserInput.COL_EMAIL))
                passt = cursor.getString(cursor.getColumnIndex(DBInfo.UserInput.COL_PASS))
                usernamet = cursor.getString(cursor.getColumnIndex(DBInfo.UserInput.COL_USERNAME))
                fullnamet = cursor.getString(cursor.getColumnIndex(DBInfo.UserInput.COL_NAME))

                users.add(DBModel(emailt, passt, usernamet, fullnamet))
                cursor.moveToNext()
            }
        }
        return  users
    }
    fun RegisterUser(emailin: String, passin:String, fullnamein: String, jenkalin: String, alamatin: String) {
        val db = writableDatabase
        val namatable = DataInfo.UserTable.TABLE_NAME
        val emailt = DataInfo.UserTable.COL_EMAIL
        val passt = DataInfo.UserTable.COL_PASS
        val fullnamet = DataInfo.UserTable.COL_FULLNAME
        val jenkalt = DataInfo.UserTable.COL_JENKAL
        val alamatt = DataInfo.UserTable.COL_ALAMAT
        var sql = "INSERT INTO " + namatable + " (" + emailt + ", " + passt+ ", " + fullnamet + ", " + jenkalt + ", " + alamatt + ") VALUES('" + emailin+ "', '" + passin + "', '" + fullnamein + "', '" + jenkalin + "', '" + alamatin+ "')"
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
            cursor = db.rawQuery("SELECT COUNT("+ DataInfo.UserTable.COL_EMAIL
                    +") as jumlah FROM "+ DataInfo.UserTable.TABLE_NAME + " WHERE " +
                    DataInfo.UserTable.COL_EMAIL + "=='" + emailin +"' AND " +
                    DataInfo.UserTable.COL_PASS + "=='" + passin + "'" , null)
        } catch (e: android.database.SQLException) {
//            db.execSQL(SQL_CREATE_USER)
//            return "Tabel Dibuat"
        }
        if (cursor!!.moveToFirst()){
            jumlah =
                cursor.getString(cursor.getColumnIndex(DataInfo.UserTable.COL_JUMLAH))
        }
        return jumlah
    }
    fun deleteData(emailin: String){
        val db = writableDatabase
        val namatablet = DataInfo.UserTable.TABLE_NAME
        val emailt = DataInfo.UserTable.COL_EMAIL
        val sql = "DELETE FROM " +namatablet+ " WHERE "+emailt+"='"+emailin+"'"
        db.execSQL(sql)
    }
    fun updateData(emailin: String, passin: String, usernamein: String, fullnamein: String){
        val db = writableDatabase
        val namatablet = DataInfo.UserTable.TABLE_NAME
        val emailt = DataInfo.UserTable.COL_EMAIL
        val passt = DataInfo.UserTable.COL_PASS
        val usernamet = DataInfo.UserTable.TABLE_NAME
        val fullnamet = DataInfo.UserTable.COL_FULLNAME
        var sql = "UPDATE "+ namatablet + " SET "+
                usernamet+"='"+usernamein+"', "+fullnamet+"='"+fullnamein+"', "+passt+"='"+passin+"' "+
                "WHERE "+emailt+"='"+emailin+"'"
        db.execSQL(sql)
    }
}