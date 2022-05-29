package com.example.ontapdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Minh_Sqlite extends SQLiteOpenHelper {

    public static final String DB_NAME = "Minh_Sqlite";
    public static final int DB_VERSION = 1;
    //Tên bảng
    public static final String TableName = "ContactTable";
    //Tên các cột trong bảng
    public static final String Id = "ID";
    public static final String Name = "Name";
    public static final String Phone = "Phone";
    public static final String Img = "Image";

    public Minh_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //tạo câu lệnh sql để tạo bảng
        String sqlCreate = "Create table if not exists " + TableName + " ("
                + Id + " Integer Primary Key, "
                + Name + " Text,"
                + Phone +" Text,"
                + Img + " Integer)";

        //chạy câu lệnh truy vấn sql
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //xóa bảng đã có
        db.execSQL("Drop table if exists "+ TableName);
        //tạo lại
        onCreate(db);
    }

    public void addContact(Contact_Minh contact_minh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id,contact_minh.getId());
        values.put(Name, contact_minh.getFullName());
        values.put(Phone, contact_minh.getPhoneNumber());
        values.put(Img, contact_minh.getImgID());
        db.insert(TableName, null, values);
        db.close();
    }

    public ArrayList<Contact_Minh> getAllContact(){
        ArrayList<Contact_Minh> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName + " Order by Name ASC ";
        //Lấy đối tượng csdl sql lite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu lệnh truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql, null);
        //tạo ArrayList để trả về;
        if(cursor != null){
            while(cursor.moveToNext()){
                Contact_Minh ct = new Contact_Minh(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));
                list.add(ct);
            }
        }
        return list;

    }

    public boolean contactExistsOrNot(int contactID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TableName+" where ID = ?", new String[]{String.valueOf(contactID)});
        if(cursor.getCount() > 0)
        {
            db.close();
            cursor.close();
            return true;
        }
        else {
            db.close();
            cursor.close();
            return false;
        }
    }

    public boolean updateFood(int id, Contact_Minh ct){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, ct.getFullName());
        values.put(Phone, ct.getPhoneNumber());
        values.put(Img, ct.getImgID());
        int result = db.update(TableName, values,  Id + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return result != -1;
    }


    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TableName + " Where Id = "+ id;
        db.execSQL(sql);
        db.close();
    }
}
