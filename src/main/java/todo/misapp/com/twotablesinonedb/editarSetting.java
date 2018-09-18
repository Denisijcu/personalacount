package todo.misapp.com.twotablesinonedb;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class editarSetting extends AppCompatActivity  {
    private Spinner spp;
    private EditText mEditar, eActividad;
    private double mpase;
    DBAdapter myDB;
    Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // spp.setOnItemSelectedListener(this);
        spp = (Spinner) findViewById(R.id.sp2);
        mEditar = (EditText) findViewById(R.id.IdEditarValor);
        eActividad = (EditText) findViewById(R.id.eActividad);

        OpenDB();


        fila = myDB.db.rawQuery("select _id, date,type, descrip, valor from actividades ", null);

        if (fila.moveToFirst()) {

            //  Toast.makeText(this, "Esto dentro", Toast.LENGTH_LONG).show();

            eActividad.setText(fila.getString(3));
            mEditar.setText(String.valueOf(fila.getDouble(4)));
            // mEditar.setText(fila.getString(3));


        } else {
            Toast.makeText(this, "No hay mas actividades", Toast.LENGTH_LONG).show();
        }
    }


        public void inicio(View v)
        {
            if (fila.moveToFirst())
            {


                eActividad.setText(fila.getString(3));
                mEditar.setText(String.valueOf(fila.getDouble(4)));
            }
            else
            {
                Toast.makeText(this, "No hay mas actividades", Toast.LENGTH_LONG).show();
            }

        }

        public void fin (View view)
        {
            if (fila.moveToLast())
            {


                eActividad.setText(fila.getString(3));
                mEditar.setText(String.valueOf(fila.getDouble(4)));

            }
            else
            {
                Toast.makeText(this, "No hay mas actividades", Toast.LENGTH_LONG).show();
            }


        }
        public void siguiente (View view)
        {
            if (fila.moveToNext())
            {

                eActividad.setText(fila.getString(3));
                mEditar.setText(String.valueOf(fila.getDouble(4)));

              //  spp.setPromptId(R.id.eActividad);

            }
            else
            {
                Toast.makeText(this, "No hay mas actividades", Toast.LENGTH_LONG).show();
            }


        }
        public void anterior (View view)
        {
            if (fila.moveToPrevious())
            {

                eActividad.setText(fila.getString(3));
                mEditar.setText(String.valueOf(fila.getDouble(4)));
            }
            else
            {
                Toast.makeText(this, "No hay mas actividades", Toast.LENGTH_LONG).show();
            }


        }



    private void OpenDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }

    public void cambiarActividad(View v)
    {
       spp = (Spinner)findViewById(R.id.sp2);
        eActividad.setText(spp.getSelectedItem().toString());


    }

    public void aceptar(View v){
       // OpenDB();
        spp = (Spinner)findViewById(R.id.sp2);
        eActividad.setText(spp.getSelectedItem().toString());
        mEditar = (EditText) findViewById(R.id.IdEditarValor);
        String v1 = eActividad.getText().toString();
        double v2 = Double.parseDouble(mEditar.getText().toString());
        long mId = fila.getLong(0);
        Toast.makeText(this, "El valor del _id es:"+mId, Toast.LENGTH_SHORT)
                .show();

        ContentValues registro = new ContentValues();
        registro.put("descrip", v1);
        registro.put("valor", v2);

        int cant = myDB.db.update("actividades", registro, "_id="+mId, null);
        // int cant = bd.update("transfs", registro, " style=" + "'" + style + "'", null);

       myDB.db.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe un estilo con dicho documento",
                    Toast.LENGTH_SHORT).show();


         OpenDB();
         fila = myDB.db.rawQuery("select _id, date,type, descrip, valor from actividades ", null);
         fin(null);
    }
    public void onBackPressed() {
        myDB.close();
        finish();



    }

}
