package todo.misapp.com.twotablesinonedb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class doSetting extends AppCompatActivity {
    Time today = new Time(Time.getCurrentTimezone());
    private DBAdapter myDB;
    private Spinner sp;
    private EditText Importe;
    private double valor;
    Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sp = (Spinner) findViewById(R.id.spActividades);
        Importe = (EditText) findViewById(R.id.edImporte);
        Importe.setText("0");
        OpenDB();




        populateListView();
        ListViewItemClick();

    }

    private void OpenDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }


    public void onClick_AddTask(View view){

        today.setToNow();
        String timestamp = today.format("%y-%m-%d %H:%M:%S");
        String mActividad = sp.getSelectedItem().toString();

        if (mActividad.equals("Capital") || mActividad.equals("Salario")|| mActividad.equals("Otros Ingresos")) {
           // Importe.setText(Importe.getText());
            valor = Double.parseDouble(Importe.getText().toString());
        }
        else
        {
           // Importe.setText("-"+Importe.getText());
            valor = Double.parseDouble("-"+Importe.getText().toString());

        }
       // String tipo ="Inicio";




     // Toast.makeText(this, "El valor del spinner es: "+mActividad+ " y el valor es:"+valor, Toast.LENGTH_LONG).show();


        if (!TextUtils.isEmpty(Importe.getText().toString())){
            OpenDB();
            myDB.insertRow3(timestamp, mActividad, valor);
           // Toast.makeText(this, "los valores son:" + timestamp + " el tipo es:" + tipo + "la actividada es:" + mActividad + "el valor es" + valor, Toast.LENGTH_LONG).show();
        }
        Importe.setText("0");

       populateListView();



    }


    public void populateListView(){
        Cursor cursor = myDB.getAllRows3();
        String[] fromFieldName = new String[] {DBAdapter.KEY_ROWID, DBAdapter.KEY_DESCRIP,DBAdapter.KEY_VALOR};
        int[] toViewIDs = new int[] {R.id.v0,R.id.v1,R.id.v2};
      //  Toast.makeText(this,"estoy en el metodo populate",Toast.LENGTH_LONG).show();
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.content_itemsofactividades, cursor,fromFieldName, toViewIDs,0 );

        ListView myList = (ListView) findViewById(R.id.listViewActividades);
        myList.setAdapter(myCursorAdapter);

    }
    public void EleminarDoc(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta operacion elimina totalmente todas las actividades capturadas ¿Desea continuar ?")
                .setTitle("Advertencia")
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
                                OpenDB();
                                myDB.deleteRow3();
                                populateListView();// metodo que se debe implementar
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void ListViewItemClick(){
        OpenDB();
        ListView myList = (ListView) findViewById(R.id.listViewActividades);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final long xId = id;

                AlertDialog.Builder builder = new AlertDialog.Builder(doSetting.this);

                builder.setMessage("Esta seguro que desea modificar los datos existentes por los seleccionados?");
                builder.setTitle("Modificar");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        updateTask(xId);
                        populateListView();

                    }
                });{

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog =  builder.create();
                    dialog.show();
                }
            }
        });
    }
    private void updateTask( long id){

        Cursor cursor =  myDB.getRow3(id);

        if (cursor.moveToFirst()){

            today.setToNow();
            String date = today.format("%y-%m-%d %H:%M:%S");
           // String s = "Inicio";
            String lv = sp.getSelectedItem().toString();
            final double v;

            if (lv.equals("Capital") || lv.equals("Salario")|| lv.equals("Otros Ingresos")) {

                v  = Double.parseDouble(Importe.getText().toString());
            }
            else
            {
                v = Double.parseDouble("-"+Importe.getText().toString());

            }


           // Double v = Double.parseDouble(Importe.getText().toString());



            myDB.updateRow3(id, date, lv, v);

        }
        cursor.close();

    }
    public void editar(View v){
        //Intent iResult = new Intent(this, editarSetting.class);
        //startActivity(iResult);
        String mensaje;
        mensaje=" Pasos:1.- Selecionar la actividad en la lista desplegable. 2.- Teclear el valor del importe. 3.- Dar click en fila de la actividad a editar.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(mensaje)
               .setTitle("Editar")
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