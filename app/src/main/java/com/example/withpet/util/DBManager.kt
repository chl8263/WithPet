package com.example.withpet.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.withpet.vo.hospital.HospitalSearchDTO

class DBManager(var context: Context) : SQLiteOpenHelper(context, "history", null, 1) {

    init {
        createHospitalHistoryTable()
    }

    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun createHospitalHistoryTable() {
        val db = writableDatabase
        db.execSQL("CREATE TABLE IF NOT EXISTS  HISTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT NOT NULL, ADDRESS TEXT NOT NULL , GU TEXT NOT NULL, DONG TEXT NOT NULL , LATITUDE TEXT NOT NULL , LONGITUDE TEXT NOT NULL, HOSPITALUID TEXT NOT NULL, TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
        db.close()
    }

    fun selectHospitalHistory(): ArrayList<HospitalSearchDTO> {
        val db = readableDatabase

        var list = arrayListOf<HospitalSearchDTO>()

        val cursor = db.rawQuery("select * from HISTORY order by TIMESTAMP DESC", null)

        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext())
                list.add(
                    HospitalSearchDTO(
                        name = cursor.getString(1),
                        address = cursor.getString(2),
                        gu = cursor.getString(3),
                        dong = cursor.getString(4),
                        latitude = cursor.getString(5),
                        longitude = cursor.getString(6),
                        hospitalUid = cursor.getString(7)
                    )
                )
        }

        db.close()
        return list
    }

    fun insertHospitalHistory(item: HospitalSearchDTO) {
        val db = writableDatabase

        db.beginTransaction()

        Log.e("!@!@!@", item)

        try {


            val cursor = db.rawQuery("select * from HISTORY where HOSPITALUID = \"${item.hospitalUid}\"", null)

            if (cursor != null && cursor.getCount() != 0) {

                cursor.moveToFirst()
                db.delete("HISTORY", "ID = ${cursor.getInt(0)}", null)

            }else {

                val cursor2 = db.rawQuery("select count(*) from HISTORY", null)
                if (cursor2 != null && cursor2.getCount() != 0) {

                    cursor2.moveToFirst()
                    var count = cursor2.getInt(0)

                    if (count >= 3) {
                        val cursor = db.rawQuery("select * from HISTORY order by TIMESTAMP ASC", null)
                        cursor.moveToFirst()
                        db.delete("HISTORY", "ID = ${cursor.getInt(0)}", null)
                    }
                }

            }

            var contentValue = ContentValues()
            contentValue.put("NAME", item.name)
            contentValue.put("ADDRESS", item.address)
            contentValue.put("GU", item.gu)
            contentValue.put("DONG", item.dong)
            contentValue.put("LATITUDE", item.latitude)
            contentValue.put("LONGITUDE", item.longitude)
            contentValue.put("HOSPITALUID", item.hospitalUid)
            db.insert("HISTORY", null, contentValue)

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}