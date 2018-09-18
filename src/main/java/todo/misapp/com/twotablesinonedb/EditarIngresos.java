package todo.misapp.com.twotablesinonedb;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class EditarIngresos extends AppCompatActivity {

    private Button btnInicio, btnSiguiente, btnAnterior, btnUltimo;
    private Button btnAceptar, btnEliminar, btnAmpliar;
    private EditText DescIngresos, EditarValor, EditarDetalle;
    private ImageView imagen;
    private double mpase;
    private String path = "";
    private int idReg = 0;
    protected String ArchivoFoto = "";
    private String message = " ";
    private DBAdapter myDB;
    Cursor fila;
    Time today = new Time(Time.getCurrentTimezone());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ingresos);
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

        btnInicio = (Button) findViewById(R.id.IdInicio);
        btnSiguiente = (Button) findViewById(R.id.IdSiguiente);
        btnAnterior = (Button) findViewById(R.id.IdAnterior);
        btnUltimo = (Button) findViewById(R.id.IdUltimo);

        btnAceptar  = (Button) findViewById(R.id.IdBtnAceptar);
        btnEliminar = (Button) findViewById(R.id.IdBtnEliminar);
        btnAmpliar = (Button) findViewById(R.id.IdBtnCancelar);

        DescIngresos = (EditText) findViewById(R.id.idDescIngresos);
        EditarValor = (EditText) findViewById(R.id.IdEditarValor);
        EditarDetalle = (EditText) findViewById(R.id.idEditarDetalle);
        imagen = (ImageView) findViewById(R.id.idFoto);

        OpenDB();
        try {

            fila = myDB.db.rawQuery("select _id, date,type, descrip, valor, imagen, detalle from bookI ", null);
            if (fila.move(0)){
            if (fila.moveToFirst()) {

                idReg = fila.getInt(0);
                DescIngresos.setText(fila.getString(3));
                EditarValor.setText(String.valueOf(fila.getDouble(4)));
                EditarDetalle.setText(fila.getString(6));
                path = fila.getString(5);

                Bitmap bMap = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + path);

                imagen.setImageBitmap(bMap);

            } else {
                Toast.makeText(this, "No hay mas registros", Toast.LENGTH_LONG).show();
                EditarValor.setText("0.00");
            }}
           else
           {
               fila.moveToNext();
           }
        } catch (Exception e)

        {
            message = e.getMessage();
        }



    }
    private void OpenDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }

    public void inicio(View v)
    {
       // OpenDB();
       // fila = myDB.db.rawQuery("select _id, date,type, descrip, valor, imagen, detalle from bookI ", null);
        try {
            if (fila.move(0)){
            if (fila.moveToFirst()) {
                idReg = fila.getInt(0);

                DescIngresos.setText(fila.getString(3));
                EditarValor.setText(String.valueOf(fila.getDouble(4)));
                EditarDetalle.setText(fila.getString(6));
                path = fila.getString(5);

                Bitmap bMap = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + path);

                imagen.setImageBitmap(bMap);
            } else {
                Toast.makeText(this, "No hay mas Ingresos", Toast.LENGTH_LONG).show();
            }}
            else
            {
                fila.moveToNext();
            }
        } catch (Exception e){
            message=e.getMessage();
        }

    }

    public void fin (View view)
    {
       // fila = myDB.db.rawQuery("select _id, date,type, descrip, valor, imagen, detalle from bookI ", null);
        try {
            if (fila.move(0)){
            if (fila.moveToLast()) {
                idReg = fila.getInt(0);
                DescIngresos.setText(fila.getString(3));
                EditarValor.setText(String.valueOf(fila.getDouble(4)));
                EditarDetalle.setText(fila.getString(6));
                path = fila.getString(5);
                ArchivoFoto = path;

                Bitmap bMap = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + path);

                imagen.setImageBitmap(bMap);

            } else {
                Toast.makeText(this, "No hay mas gastos", Toast.LENGTH_LONG).show();
            }}
            else
            {
                fila.moveToPrevious();
            }
        }   catch (Exception e){

            message=e.getMessage();
        }

    }
    public void siguiente (View view)
    {
        try {

            if (fila.moveToNext()) {
                idReg = fila.getInt(0);
                DescIngresos.setText(fila.getString(3));
                EditarValor.setText(String.valueOf(fila.getDouble(4)));
                EditarDetalle.setText(fila.getString(6));
                path = fila.getString(5);
                ArchivoFoto = path;

                Bitmap bMap = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory() + path);

                imagen.setImageBitmap(bMap);

            } else {
                Toast.makeText(this, "No hay mas Ingresos", Toast.LENGTH_LONG).show();
            }

        }  catch (Exception e){
            message=e.getMessage();
        }

    }
    public void anterior (View view)
    {

        try {

            if (fila.move(0)) {


                if (fila.moveToPrevious()) {

                    idReg = fila.getInt(0);
                    DescIngresos.setText(fila.getString(3));
                    EditarValor.setText(String.valueOf(fila.getDouble(4)));
                    EditarDetalle.setText(fila.getString(6));
                    path = fila.getString(5);
                    ArchivoFoto = path;

                    Bitmap bMap = BitmapFactory.decodeFile(
                            Environment.getExternalStorageDirectory() + path);

                    imagen.setImageBitmap(bMap);
                } else {
                    Toast.makeText(this, "No hay mas Ingresos", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                fila.moveToPrevious();
            }
        } catch (Exception e){
            message=e.getMessage();
        }



    }

    public void Ampliar(View v) {
        try {
            if (fila.move(0)) {

                abrirVentana();
            }
            else {
                fila.moveToPrevious();

                 abrirVentana();

            }
        } catch (Exception e){
            message=e.getMessage();
      }



    }


    public void abrirVentana() {

        path = fila.getString(5);
        ArchivoFoto = path;

        Intent i = new Intent(this, Ampliar.class);
        i.putExtra("archivo", ArchivoFoto);
        startActivity(i);
    }
    public void EleminarRegistro(View v) {

        //Toast.makeText(this, "La foto a eliminar es "+Environment.getExternalStorageDirectory()+path, Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta seguro que desea eliminar este registro ?")
                .setTitle("Eliminar")
                .setCancelable(false)
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // para borrar la foto correspondiente a este articulo /IMAGENS/Nombredelafoto

                                try {

                                    String mpath = Environment.getExternalStorageDirectory() + path;
                                    File file = new File(mpath);
                                    Boolean deleted = file.delete();
                                } catch (Exception e) {
                                    message = e.getMessage();
                                }

                                try {
                                    OpenDB();
                                    myDB.deleteRow4(idReg);
                                    DescIngresos.setText("");
                                    EditarValor.setText("0.00");
                                    EditarDetalle.setText("");
                                    fila = myDB.db.rawQuery("select _id, date,type, descrip, valor, imagen, detalle from bookI ", null);
                                    if (fila.moveToFirst()){
                                        idReg = fila.getInt(0);
                                        DescIngresos.setText(fila.getString(3));
                                        EditarValor.setText(String.valueOf(fila.getDouble(4)));
                                        EditarDetalle.setText(fila.getString(6));
                                        path = fila.getString(5);
                                        ArchivoFoto = path;

                                        Bitmap bMap = BitmapFactory.decodeFile(
                                                Environment.getExternalStorageDirectory() + path);

                                        imagen.setImageBitmap(bMap);
                                    }
                                    else {
                                        message = "No hay mas registros";
                                    }
                                }

                                catch(
                                        Exception e
                                        )

                                {
                                    message = e.getMessage();
                                }
                                //return "EXCEPTION:" + message;

                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void aceptar(View v){

                today.setToNow();
                String timestamp = today.format("%y-%m-%d %H:%M:%S");
                String tipo = "Ingresos";
                String v1 = DescIngresos.getText().toString();
                double v2 = Double.parseDouble(EditarValor.getText().toString());
                String v3 = EditarDetalle.getText().toString();

                ContentValues registro = new ContentValues();
                registro.put("date", timestamp);
                registro.put("descrip", v1);
                registro.put("valor", v2);
                registro.put("detalle", v3);


                int cant = myDB.db.update("bookI", registro, "_id=" + idReg, null);


                myDB.db.close();
                if (cant == 1)
                    Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(this, "no existe ingresos de este tipo",
                            Toast.LENGTH_SHORT).show();


                OpenDB();
                fila = myDB.db.rawQuery("select _id, date,type, descrip, valor, imagen, detalle from bookI ", null);

                fin(null);

    }

/*

    @Override
    protected void onPause() {
        super.onPause();
       /*
        if (fila.move(0)){


        if (fila.moveToFirst()){
            idReg = fila.getInt(0);
            path = fila.getString(5);
            ArchivoFoto = path;

        }
            else {

           return;
        }
        }

        Toast.makeText(this, "Estoy en OnPause",Toast.LENGTH_LONG).show();
    }*/
}
