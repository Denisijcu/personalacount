package todo.misapp.com.twotablesinonedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.util.Log.w;

/**
 * Created by Julio on 10/20/2015.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter"; // Used for loggin database version


    //  table #1
    // field names:

    public static final String KEY_ROWID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CAPITAL = "capital";
    public static final String KEY_SALBASIC= "sal_basic";
    public static final String KEY_OVERTIMES= "overtimes";
    public static final String KEY_OTHERSINS= "otherins";
    public static final String KEY_LEASE= "lease";
    public static final String KEY_CARENSURENCE= "car_ensurence";
    public static final String KEY_MEDENSURENCE = "med_ensurence";
    public static final String KEY_ELECTRICITY= "electricity";
    public static final String KEY_CABLE= "cable";
    public static final String KEY_GAS= "gas";
    public static final String KEY_FOODS = "foods";
    public static final String KEY_OTHEROUTS = "otherouts";
    public static final String KEY_DEBITOS= "debitos";
    public static final String KEY_CREDITOS= "creditos";
    public static final String KEY_SALDO= "saldo";
    //public static final String KEY_ACTIVIDADES="actividades"; // esta variable es para usarla en la tabla Actividades

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_DATE, KEY_CAPITAL, KEY_SALBASIC, KEY_OVERTIMES, KEY_OTHERSINS, KEY_LEASE, KEY_CARENSURENCE, KEY_MEDENSURENCE, KEY_ELECTRICITY, KEY_CABLE, KEY_GAS, KEY_FOODS, KEY_OTHEROUTS, KEY_DEBITOS, KEY_CREDITOS};

    // Columns Number for each field Names:

    public static final int COL_ROWID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_CAPITAL = 2;
    public static final int COL_SALBASIC = 3;
    public static final int COL_OVERTIMES = 4;
    public static final int COL_OTHERINS = 5;
    public static final int COL_LEASE = 6;
    public static final int COL_CARENSURENCE = 7;
    public static final int COL_MEDENSURENCE= 8;
    public static final int COL_ELECTRICITY = 9;
    public static final int COL_CABLE = 10;
    public static final int COL_INTERNET = 11;
    public static final int COL_GAS = 12;
    public static final int COL_FOODS = 13;
    public static final int COL_OTHEROUTS= 14;
    public static final int COL_DEBITOS = 15;
    public static final int COL_CREDITOS = 16;



    // table #2

    public static final String KEY_ROWID2 = "_id";
    public static final String KEY_DATE2 = "date";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESCRIP= "descrip";
    public static final String KEY_VALOR= "valor";
    public static final String KEY_IMAGEN= "imagen";
    public static final String KEY_DETALLE= "detalle";
    public static final String[] ALL_KEYS2 = new String[]{KEY_ROWID2, KEY_DATE2, KEY_TYPE, KEY_DESCRIP, KEY_VALOR, KEY_IMAGEN, KEY_DETALLE};

    public static final int COL_ROWID2 = 0;
    public static final int COL_DATE2 = 1;
    public static final int COL_TYPE = 2;
    public static final int COL_DESCRIP = 3;
    public static final int COL_VALOR = 4;
    public static final int COL_IMAGEN = 5;
    public static final int COL_DETALLE = 6;


    /// Tabla # 3  Setting

    public static final String[] ALL_ACT = new String[]{KEY_ROWID, KEY_DATE, KEY_DESCRIP,  KEY_DEBITOS, KEY_CREDITOS, KEY_SALDO, KEY_VALOR};



    // Tabla # 4  Ingresos



    public static final String KEY_ROWID4 = "_id";
    public static final String KEY_DATE4 = "date";
    public static final String KEY_TYPE4 = "type";
    public static final String KEY_DESCRIP4= "descrip";
    public static final String KEY_VALOR4= "valor";
    public static final String KEY_IMAGEN4= "imagen";
    public static final String KEY_DETALLE4= "detalle";
    public static final String[] ALL_KEYS4 = new String[]{KEY_ROWID4, KEY_DATE4, KEY_TYPE4, KEY_DESCRIP4, KEY_VALOR4, KEY_IMAGEN4, KEY_DETALLE4};

    public static final int COL_ROWID4 = 0;
    public static final int COL_DATE4 = 1;
    public static final int COL_TYPE4 = 2;
    public static final int COL_DESCRIP4 = 3;
    public static final int COL_VALOR4 = 4;
    public static final int COL_IMAGEN4 = 5;
    public static final int COL_DETALLE4 = 6;

    // DataBase Info:

    public static final String DATABASE_NAME = "dbCPBooks";
    public static final String Db_TABLE1= "bookM";
    public static final String Db_TABLE2= "bookD";
    public static final String Db_TABLE3= "bookS";
    public static final String Db_TABLE4= "bookI";
    public static final int DATABASE_VERSION = 2;

    // SQL statement to create database

    private static final String DATABASE_CREATE_SQL1 = "CREATE TABLE " + Db_TABLE1 + " ( " + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " TEXT, " + KEY_CAPITAL + " REAL,  "  + KEY_SALBASIC + " REAL,  " + KEY_OVERTIMES + " REAL,  " + KEY_OTHERSINS+ " REAL,  " + KEY_LEASE + " REAL,  " + KEY_CARENSURENCE+ " REAL,  " + KEY_MEDENSURENCE + " REAL,  " + KEY_ELECTRICITY + " REAL,  " + KEY_CABLE + " REAL,  "  + KEY_GAS + " REAL,  " + KEY_FOODS + " REAL,  " +KEY_OTHEROUTS+ " REAL,  " + KEY_DEBITOS + " REAL, " + KEY_CREDITOS + " REAL " + ");";

    private static final String DATABASE_CREATE_SQL2 = "CREATE TABLE " + Db_TABLE2 + " ( " + KEY_ROWID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE2 + " TEXT, " + KEY_TYPE + " TEXT NOT NULL,  " +KEY_DESCRIP+ " TEXT, " + KEY_VALOR + " REAL,  " + KEY_IMAGEN+ " TEXT, "+ KEY_DETALLE+ " TEXT " + ");";

    private static final String DATABASE_CREATE_SQL4 = "CREATE TABLE " + Db_TABLE4 + " ( " + KEY_ROWID4 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE4 + " TEXT, " + KEY_TYPE4 + " TEXT NOT NULL,  " +KEY_DESCRIP4+ " TEXT, " + KEY_VALOR4 + " REAL,  " + KEY_IMAGEN4 + " TEXT, "+ KEY_DETALLE4 + " TEXT " + ");";

    private static final String DATABASE_CREATE_SQL3 = "CREATE TABLE " + Db_TABLE3 + " ( " + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " TEXT, " +KEY_DESCRIP+ " TEXT, " + KEY_DEBITOS + " REAL, "+ KEY_CREDITOS + " REAL, " + KEY_SALDO + " REAL, " + KEY_VALOR + " REAL  " + ");";





    private final Context context;
    private DatabaseHelper myDBHelper;
    public SQLiteDatabase db;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);

    }

    // Open the database connection
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close de database connection
    public void close() {
        myDBHelper.close();
    }

    // Add a newt set of values to be inserted into the database
    /// table #1
    public long insertRow0( String date, double capital, double salario, double overtime, double otherins,
                           double lease, double carensurence, double medensurence, double electricity,
                           double cable, double gas,  double foods, double otherouts,
                           double debitos, double creditos) {

        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_CAPITAL, capital);
        initialValues.put(KEY_SALBASIC, salario);
        initialValues.put(KEY_OVERTIMES, overtime);
        initialValues.put(KEY_OTHERSINS, otherins);
        initialValues.put(KEY_LEASE, lease);
        initialValues.put(KEY_CARENSURENCE, carensurence);
        initialValues.put(KEY_MEDENSURENCE, medensurence);
        initialValues.put(KEY_ELECTRICITY, electricity);
        initialValues.put(KEY_CABLE, cable);
        initialValues.put(KEY_GAS, gas);
        initialValues.put(KEY_FOODS, foods);
        initialValues.put(KEY_OTHEROUTS, otherouts);
        initialValues.put(KEY_DEBITOS, debitos);
        initialValues.put(KEY_CREDITOS, creditos);

        // Insert de data into the dabase.

        return db.insert(Db_TABLE1, null, initialValues);


    }

    public long insertRow3( String date, String descrip, double valor) {

        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_DATE, date);
       // initialValues.put(KEY_TYPE, tipo);
        initialValues.put(KEY_DESCRIP, descrip);
        initialValues.put(KEY_VALOR, valor);

        // Insert de data into the dabase.

        return db.insert(Db_TABLE3, null, initialValues);


    }
    public long insertRow2( String date, String type, String descrip, double valor, String pathimagen, String detalle) {

        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_DATE2, date);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_DESCRIP, descrip);
        initialValues.put(KEY_VALOR, valor);
        initialValues.put(KEY_IMAGEN, pathimagen);
        initialValues.put(KEY_DETALLE, detalle);


        // Insert de data into the dabase.
        return db.insert(Db_TABLE2, null, initialValues);


    }
    public long insertRow4( String date, String type, String descrip, double valor, String pathimagen, String detalle) {

        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_DATE4, date);
        initialValues.put(KEY_TYPE4, type);
        initialValues.put(KEY_DESCRIP4, descrip);
        initialValues.put(KEY_VALOR4, valor);
        initialValues.put(KEY_IMAGEN4, pathimagen);
        initialValues.put(KEY_DETALLE4, detalle);


        // Insert de data into the dabase.
        return db.insert(Db_TABLE4, null, initialValues);


    }

    public long addTabla2( Tabla2_Modelo modelo) {

        ContentValues initialValues = new ContentValues();


        initialValues.put(KEY_DATE2, modelo.get_id());
        initialValues.put(KEY_TYPE, modelo.getDate());
        initialValues.put(KEY_DESCRIP, modelo.getDescrip());
        initialValues.put(KEY_VALOR, modelo.getValor());

        // Insert de data into the dabase.

        return db.insert(Db_TABLE2, null, initialValues);


    }


    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {

        String where = KEY_ROWID + "=" + rowId;
        return db.delete(Db_TABLE1, where, null) != 0;
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow2(long rowId) {

        String where = KEY_ROWID + "=" + rowId;
        return db.delete(Db_TABLE2, where, null) != 0;
    }


    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow3() {

        //String where = KEY_ROWID + "=" + rowId;
        return db.delete(Db_TABLE3, null, null) != 0;
    }


    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow4(long rowId) {

        String where = KEY_ROWID + "=" + rowId;
        return db.delete(Db_TABLE4, where, null) != 0;
    }


    public void deleteAll() {

        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database
    // tabla #1
    public Cursor getAllRows() {
        String where = null;
        Cursor c = db.query(true, Db_TABLE1, ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }
    // Tabla # 2
    public Cursor getAllRows2() {
        String where = null;
        Cursor c = db.query(true, Db_TABLE2, ALL_KEYS2, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }
    // Tabla # 3
    public Cursor getAllRows3() {
        String where = null;
        Cursor c = db.query(true, Db_TABLE3, ALL_ACT, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }




    // Tabla # 4
    public Cursor getAllRows4() {
        String where = null;
        Cursor c = db.query(true, Db_TABLE4, ALL_KEYS4, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }

    // Get a specific row ( by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, Db_TABLE1, ALL_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row ( by rowId)
    public Cursor getRow2(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, Db_TABLE2, ALL_KEYS2,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Get a specific row ( by rowId)
    public Cursor getRow3(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, Db_TABLE3, ALL_ACT,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Change an existing row to be equal to new data

    public boolean updateRow(long rowId, String date, double capital, double salario, double overtime,
                             double otherins, double lease, double carensurence, double medensurence,
                             double electricity, double cable,  double gas,  double foods, double otherouts,
                             double debitos, double creditos)

    {

        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_CAPITAL, capital);
        newValues.put(KEY_SALBASIC, salario);
        newValues.put(KEY_OVERTIMES, overtime);
        newValues.put(KEY_OTHERSINS, otherins);
        newValues.put(KEY_LEASE, lease);
        newValues.put(KEY_CARENSURENCE, carensurence);
        newValues.put(KEY_MEDENSURENCE, medensurence);
        newValues.put(KEY_ELECTRICITY, electricity);
        newValues.put(KEY_CABLE, cable);
        newValues.put(KEY_GAS, gas);
        newValues.put(KEY_FOODS, foods);
        newValues.put(KEY_OTHEROUTS, otherouts);
        newValues.put(KEY_DEBITOS, debitos);
        newValues.put(KEY_CREDITOS, creditos);

        // Insert it into the database
        return db.update(Db_TABLE1, newValues, where, null) != 0;

    }

    public boolean updateRow3(long rowId, String date, String descrip, double valor)

    {

        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DATE, date);
      //  newValues.put(KEY_TYPE, tipo);
        newValues.put(KEY_DESCRIP, descrip);
        newValues.put(KEY_VALOR, valor);

        // Insert it into the database
        return db.update(Db_TABLE3, newValues, where, null) != 0;
    }






    public boolean updateRow2(long rowId, String date, String tipo, String descrip, double valor, String path, String detalle)

    {

        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_TYPE, tipo);
        newValues.put(KEY_DESCRIP, descrip);
        newValues.put(KEY_VALOR, valor);
        newValues.put(KEY_IMAGEN, path);
        newValues.put(KEY_DETALLE, detalle);


        // Insert it into the database
        return db.update(Db_TABLE2, newValues, where, null) != 0;
    }






    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {


            _db.execSQL(DATABASE_CREATE_SQL1);
            _db.execSQL(DATABASE_CREATE_SQL2);
            _db.execSQL(DATABASE_CREATE_SQL3);
            _db.execSQL(DATABASE_CREATE_SQL4);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            w(TAG, "Upgrading application's dabase from version" + oldVersion + "to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + Db_TABLE1);
            _db.execSQL("DROP TABLE IF EXISTS " + Db_TABLE2);
            _db.execSQL("DROP TABLE IF EXISTS " + Db_TABLE3);
            _db.execSQL("DROP TABLE IF EXISTS " + Db_TABLE4);
            // Recreate new database

            onCreate(_db);


        }


    }


}
