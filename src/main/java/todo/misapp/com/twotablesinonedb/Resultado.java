package todo.misapp.com.twotablesinonedb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Resultado extends AppCompatActivity {
    private DBAdapter myDB;
    private int idReg = 0;
    private Cursor fila;
    protected Double gastos, ingresos;
    private FrameLayout frm;
    EditText eSaldoR;

    Time today = new Time(Time.getCurrentTimezone());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        frm = (FrameLayout) findViewById(R.id.IdFrame);
        frm.setVisibility(View.VISIBLE);
        eSaldoR = (EditText)findViewById(R.id.SaldoResultado);
        OpenDB();
        // aqui voy a crear una tabla temporal a partir de la tabla setting, luego la borro y la lleno con los registros del resultado
        today.setToNow();
        String fecha = today.format("%y-%m-%d %H:%M:%S");
        //myDB.insertRow3(fecha,"INGRESOS",0);
        //myDB.insertRow3(fecha,"GASTOS",0);

        String sqlup = "CREATE TEMP TABLE tempo AS select * from bookS";
        String sqlup1 = "UPDATE bookS set debitos =(select sum(valor) from bookD) where descrip = 'GASTOS';";
        String sqlup2 = "UPDATE bookS set creditos =(select sum(valor) from bookI) where descrip = 'INGRESOS';";
       // , creditos =(select  sum(valor) from  bookI)";


        myDB.db.execSQL(sqlup);
        myDB.db.execSQL(sqlup1);
        myDB.db.execSQL(sqlup2);





        String temp = "tempo";
        fila = myDB.db.rawQuery("select _id, date,descrip, valor, debitos, creditos, saldo from tempo ", null);
        myDB.db.delete(temp, null, null);

        // Estrutura de la tabla resultado que es el propio setting
        String f1,f2,f3;
        f1= "date";
        f2= "descrip";



        String c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13;
        c1 = "CAPITAL";
        c2 = "INGRESOS";
        c3 = "   Salario";
        c4 = "   Otros Ingresos";
        c5 = "GASTOS";
        c6 = "   Renta";
        c7 = "   Alimentos";
        c8 = "   Seguro Medico";
        c9 = "   Seguro del Carro";
        c10 = "   Celular";
        c11 = "   Cable - Internet";
        c12 = "   Gasolina";
        c13 = "   Otros Gastos";



        ContentValues initialValues = new ContentValues();
        initialValues.put(f1, fecha);
        initialValues.put(f2, c1);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c2);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c3);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c4);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c5);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);
        initialValues.put(f1, fecha);
        initialValues.put(f2, c6);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c7);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c8);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c9);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);
        initialValues.put(f1, fecha);
        initialValues.put(f2, c10);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c11);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);

        initialValues.put(f1, fecha);
        initialValues.put(f2, c12);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);
        initialValues.put(f1, fecha);
        initialValues.put(f2, c13);
        // Insert de data into the dabase.
        myDB.db.insert(temp, null, initialValues);


        // Actualizo los registros Otros los setting mas los registros Otros Gastos y Otros Ingresos con las tablas de Gastos e Ingresos
                          // CAPITAL E INGRESOS
        String sqlupc1 = "UPDATE tempo set creditos  =(select valor from bookS where descrip='Capital') where descrip = 'CAPITAL';";
        String sqlupc100 = "UPDATE tempo set saldo  = creditos where descrip = 'CAPITAL';";
        String sqlupc2 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Salario') where descrip = '   Salario';";
        String sqlupc3 = "UPDATE tempo set valor  =(select sum(valor) from bookI) where descrip = '   Otros Ingresos';";
        String sqlupc4 = "UPDATE tempo set creditos = (select sum(valor) from tempo where valor>0 ) where descrip ='INGRESOS';";
        String sqlupc101 = "UPDATE tempo set saldo  = (select sum(creditos) from tempo) where descrip = 'INGRESOS';";

                          // GASTOS
        String sqlupc5= "UPDATE tempo set valor  =(select valor from bookS where descrip='Renta') where descrip = '   Renta';";
        String sqlupc6 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Alimentos') where descrip = '   Alimentos';";
        String sqlupc7 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Seguro Medico') where descrip = '   Seguro Medico';";
        String sqlupc8= "UPDATE tempo set valor  =(select valor from bookS where descrip='Seguro del Carro') where descrip = '   Seguro del Carro';";
        String sqlupc9 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Telefono') where descrip = '   Celular';";
        String sqlupc10 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Cable - Internet') where descrip = '   Cable - Internet';";
        String sqlupc11 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Gasolina') where descrip = '   Gasolina';";
       // String sqlupc12 = "UPDATE tempo set valor  =(select valor from bookS where descrip='Otros Gastos') where descrip = '   Otros Gastos';";
        String sqlupc12 = "UPDATE tempo set valor  =(select sum(valor) from bookD) where descrip = '   Otros Gastos';";
        String sqlupc13 = "UPDATE tempo set debitos = (select sum(valor) from tempo where valor<0 ) where descrip ='GASTOS';";
        String sqlupc14 = "UPDATE tempo set saldo = (select sum(creditos) from tempo )+(select sum(debitos) from tempo ) where descrip ='GASTOS';";

        //String Resultado = "Select sum(debitos) , sum(creditos) from tempo;";


        // ACTUALIZAR LA TABLA RESULTADO
        myDB.db.execSQL(sqlupc1);
        myDB.db.execSQL(sqlupc100);
        myDB.db.execSQL(sqlupc2);
        myDB.db.execSQL(sqlupc3);
        myDB.db.execSQL(sqlupc4);
        myDB.db.execSQL(sqlupc101);
        myDB.db.execSQL(sqlupc5);
        myDB.db.execSQL(sqlupc6);
        myDB.db.execSQL(sqlupc7);
        myDB.db.execSQL(sqlupc8);
        myDB.db.execSQL(sqlupc9);
        myDB.db.execSQL(sqlupc10);
        myDB.db.execSQL(sqlupc11);
        myDB.db.execSQL(sqlupc12);
        myDB.db.execSQL(sqlupc13);
        myDB.db.execSQL(sqlupc14);

        //myDB.db.execSQL(Resultado);

        //Double gastos, ingresos ;
        Cursor resulta = myDB.db.rawQuery("select sum(debitos), sum(creditos) from tempo ", null);


        Double sRes=0.0;

        if (resulta.moveToFirst()) {
             gastos = resulta.getDouble(0);
             ingresos = resulta.getDouble(1);
             //Toast.makeText(this, "los gastos son: "+gastos+ " y los ingresos: "+ingresos, Toast.LENGTH_LONG).show();
            sRes=(ingresos + gastos);
           // double sRess = Math.round(sRes);
             if (sRes<0)
                  eSaldoR.setTextColor(Color.RED);
            else
                 eSaldoR.setTextColor(Color.BLACK);


             eSaldoR.setText(Double.toString(Math.round(sRes)));
        } else {
            Toast.makeText(this, "No hay registros", Toast.LENGTH_LONG).show();
        }





        populateListView();
    }

    private void OpenDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }
    public void populateListView(){


        String[] fromFieldName = new String[] {DBAdapter.KEY_ROWID,DBAdapter.KEY_DESCRIP, DBAdapter.KEY_VALOR, DBAdapter.KEY_DEBITOS, DBAdapter.KEY_CREDITOS, DBAdapter.KEY_SALDO};
        int[] toViewIDs = new int[] {R.id.r0,R.id.r1,R.id.r2, R.id.r3, R.id.r4, R.id.r5};
      //   Toast.makeText(this, "estoy en el metodo populate", Toast.LENGTH_LONG).show();

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_resultadoitem, fila,fromFieldName, toViewIDs,0 );

        ListView myList = (ListView) findViewById(R.id.listViewResultado);
        myList.setAdapter(myCursorAdapter);


    }

    public void piestadistico (View view){
        Intent iRpie = new Intent(this, PieEstadistico.class);
        iRpie.putExtra("debitos", gastos);
        iRpie.putExtra("creditos",ingresos);
        startActivity(iRpie);
    }

    public void cerrarfragme (View v){
        frm.setVisibility(View.INVISIBLE);
    }
    public void abrirfragme (View v){ frm.setVisibility(View.VISIBLE);
    }
    public void EnProceso(View v){

        String mensaje;
        mensaje="Lo sentimos, este metodo esta en proceso.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(mensaje)
                .setTitle("En proceso")
                .setCancelable(false)
                .setNeutralButton("Aceptar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
