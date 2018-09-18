package todo.misapp.com.twotablesinonedb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Time today = new Time(Time.getCurrentTimezone());
    DBAdapter myDB;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);


        OpenDB();
        addtable2();
       ShowTable2();

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
    }

    private void OpenDB() {

        myDB = new DBAdapter(this);
        myDB.open();

    }


    public void addtable2()
    {
        OpenDB();
        today.setToNow();
        String timestamp = today.format("%y-%m-%d %H:%M:%S");
     /*
        String mdescrip = "Probando";
        String mtype = "Ingresos";
        Double mvalor = 50.39;

        Tabla2_Modelo modelo = new Tabla2_Modelo();
        modelo.setDate(timestamp);
        modelo.setDescrip(mdescrip);
        modelo.setType(mtype);
        modelo.setValor(mvalor);
        myDB.addTabla2(modelo);
*/
    }

    public void ShowTable2()
    {
        String mfecha=" ";
        String mtype=" ";
        String mdescrip = " ";
        Double mvalor = 0d;

        Cursor fila;
        OpenDB();
        fila = myDB.getAllRows2();

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            Tabla2_Modelo modelo = new Tabla2_Modelo();
            modelo.setDate(fila.getString(1));
            modelo.setType(fila.getString(2));
            modelo.setDescrip(fila.getString(3));
            modelo.setValor(fila.getDouble(4));



        } else

        myDB.close();
    }
    public void addtable1()
    {
        Double mcapital=1000.00;
        Double msalario=1200.00;
        Double msegumedico = 19.90;
        Double msegurocarro = 132.00;
        Double mrenta=650.00;
        Double mvalor = 0.0;

        OpenDB();
        today.setToNow();
        String timestamp = today.format("%y-%m-%d %H:%M:%S");

        myDB.insertRow0(timestamp, mcapital, msalario, msegumedico, msegurocarro, mrenta, mvalor, mvalor, mvalor, mvalor, mvalor, mvalor, mvalor, mvalor, mvalor);

    }

    public void ShowTable1()
    {

        Double msalario = 0d;
        Double mcapital =0d;
        Double mseguromedico=0d;
        Double msegurocarro=0d;

        Cursor fila;
        OpenDB();
        fila = myDB.getAllRows();

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            mcapital = fila.getDouble(2);
            msalario =  fila.getDouble(3);
            mseguromedico =fila.getDouble(4);
            msegurocarro = fila.getDouble(5);



        } else

        myDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
            case R.id.btn6:
                Intent iResultado = new Intent(this, Resultado.class);
                startActivity(iResultado);
                break;
            case R.id.btn2:
            case R.id.btn7:
                Intent iRgastos = new Intent(this, Ayuda.gastos.class);
                startActivity(iRgastos);
                break;
            case R.id.btn3:
            case R.id.btn8:
                Intent iRingresos = new Intent(this, Ingresos.class);
                startActivity(iRingresos);
                break;
            case R.id.btn4:
            case R.id.btn9: {
                Intent iResult = new Intent(this, doSetting.class);
                startActivity(iResult);
                break;}
            case R.id.btn5:
            case R.id.btn10:
                Intent iRAyuda = new Intent(this, Ayuda.class);
                startActivity(iRAyuda);
                break;

        }

    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Quieres salir de la aplicacion?");
        builder.setTitle("Salida");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
    public void ayuda (View view){

       // Intent iResult = new Intent(this, Ayuda.class);
      //  startActivity(iResult);
    }
    public void resultado (View view){

        //Intent iResult = new Intent(this, resultado.class);
        //startActivity(iResult);
    }
    public void setting (){

    }
}
