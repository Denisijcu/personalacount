package todo.misapp.com.twotablesinonedb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.ZoomControls;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ingresos extends AppCompatActivity implements OnClickListener {
        private Spinner spIngresos;
        private EditText eImporte, eDetalles;
        private ImageView btnCamara, fotoCamara;
        private String NOMBRECARPETA="INGRESOS";
        private String NOMBREFOTO="";
        private FrameLayout frameFoto;
        private Button btnAgregar, btnEditar;
        private ListView lvIngresos;

        Time today = new Time(Time.getCurrentTimezone());
        private DBAdapter myDB;


        ZoomControls zoomControls;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);


            spIngresos= (Spinner) findViewById(R.id.spinnerIngresos);
            eImporte = (EditText) findViewById(R.id.IdImporteIngresos);
            eDetalles = (EditText)findViewById(R.id.idDetalles);
            btnCamara = (ImageView) findViewById(R.id.IdCamara);
            fotoCamara= (ImageView) findViewById(R.id.idFoto);
            frameFoto = (FrameLayout)findViewById(R.id.IdFrameFoto);
            lvIngresos = (ListView) findViewById(R.id.listViewIngresos);

            btnEditar = (Button)findViewById(R.id.IdEditar);
            btnAgregar = (Button) findViewById(R.id.IdAgregar);

            eImporte.setText("0");

            // Metodos para el ZOOM
            zoomControls = (ZoomControls) findViewById(R.id.zoomControls);


            zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float x = fotoCamara.getScaleX();
                    float y = fotoCamara.getScaleY();

                    fotoCamara.setScaleX(x + 1);
                    fotoCamara.setScaleY(y + 1);
                }
            });

            zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    float x = fotoCamara.getScaleX();
                    float y = fotoCamara.getScaleY();

                    fotoCamara.setScaleX(x - 1);
                    fotoCamara.setScaleY(y - 1);
                }
            });

            // fin de los metodos Zoom

            OpenDB();

            populateListView();


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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.IdAgregar:
                today.setToNow();
                String timestamp = today.format("%y-%m-%d %H:%M:%S");
                String tipo ="Ingresos";
                String mGastos = spIngresos.getSelectedItem().toString();
                String mImporte =eImporte.getText().toString();
                Double valor;
                valor = Double.parseDouble(mImporte);
                String mDetalles =eDetalles.getText().toString();
                String path = "/"+NOMBRECARPETA+"/"+NOMBREFOTO;
                //Toast.makeText(this, "El path es :"+path, Toast.LENGTH_SHORT).show();

                // Toast.makeText(this, "El valor del spinner es: "+mActividad+ " y el valor es:"+valor, Toast.LENGTH_LONG).show();

                if (!TextUtils.isEmpty(eImporte.getText().toString())){
                    OpenDB();
                    myDB.insertRow4(timestamp, tipo, mGastos, valor, path, mDetalles);
                    // Toast.makeText(this, "los valores son:" + timestamp + " el tipo es:" + tipo + "la actividada es:" + mGastos + "el valor es" + valor, Toast.LENGTH_LONG).show();
                }
                eImporte.setText("0");
                eDetalles.setText(" ");
                frameFoto.setVisibility(View.INVISIBLE);
                lvIngresos.setVisibility(View.VISIBLE);

                populateListView();

                break;
            case R.id.IdEditar:
                Intent iRingresos = new Intent(this, EditarIngresos.class);
                startActivity(iRingresos);
                break;

            case R.id.IdCamara:
                frameFoto.setVisibility(View.VISIBLE);
                lvIngresos.setVisibility(View.INVISIBLE);
                // zoomControls.setVisibility(View.VISIBLE);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Creamos una carpeta en la memeria del terminal
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), NOMBRECARPETA);
                imagesFolder.mkdirs();
                //añadimos el nombre de la imagen
                NOMBREFOTO=getCode()+".jpg";
                // Toast.makeText(this,"el nombre de la foto es:"+NOMBREFOTO,Toast.LENGTH_LONG).show();

                File image = new File(imagesFolder, NOMBREFOTO );
                Uri uriSavedImage = Uri.fromFile(image);
                //Le decimos al Intent que queremos grabar la imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                //Lanzamos la aplicacion de la camara con retorno (forResult)
                startActivityForResult(cameraIntent, 1);
                break;

        }


    }

    private void OpenDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() + "/" + NOMBRECARPETA + "/" + NOMBREFOTO);


            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla

            fotoCamara.setImageBitmap(bMap);
            fotoCamara.setVisibility(View.VISIBLE);


        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "pic_" + date;
        return photoCode;
    }
    public void populateListView(){
        Cursor cursor = myDB.getAllRows4();
        String[] fromFieldName = new String[] {DBAdapter.KEY_ROWID4, DBAdapter.KEY_DESCRIP4,DBAdapter.KEY_VALOR4 };
        int[] toViewIDs = new int[] {R.id.v0,R.id.v1,R.id.v2};
        //  Toast.makeText(this,"estoy en el metodo populate",Toast.LENGTH_LONG).show();
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.content_gastos_items, cursor,fromFieldName, toViewIDs,0 );

        ListView myList = (ListView) findViewById(R.id.listViewIngresos);
        myList.setAdapter(myCursorAdapter);

    }
    public void ListViewItemClick(){
        OpenDB();
        ListView myList = (ListView) findViewById(R.id.listViewActividades);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long xId = id;

                AlertDialog.Builder builder = new AlertDialog.Builder(Ingresos.this);

                builder.setMessage("Esta seguro que desea modificar los datos existentes por los seleccionados?");
                builder.setTitle("Editar");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateGastos(xId);
                        populateListView();

                    }
                });
                {

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    private void updateGastos( long id){

        Cursor cursor =  myDB.getRow2(id);

        if (cursor.moveToFirst()){

            today.setToNow();
            String timestamp = today.format("%y-%m-%d %H:%M:%S");
            String tipo ="Ingresos";
            String mGastos = spIngresos.getSelectedItem().toString();
            String mImporte =eImporte.getText().toString();
            Double valor;
            valor = Double.parseDouble("-"+mImporte);
            String mDetalles =eDetalles.getText().toString();
            String path = "/"+NOMBRECARPETA+"/"+NOMBREFOTO;





            myDB.updateRow2(id, timestamp, tipo, mGastos, valor, path, mDetalles);

        }
        cursor.close();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        OpenDB();
        populateListView();
    }
}
