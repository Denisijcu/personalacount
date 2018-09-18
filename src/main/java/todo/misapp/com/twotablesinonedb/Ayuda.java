package todo.misapp.com.twotablesinonedb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ZoomControls;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ayuda extends Activity implements View.OnTouchListener {
    private String mpath = "";
    private ImageView im;
    ZoomControls zoomControls;

    // para el zoom con el touch
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    // fin del touch para las variable
    private final String htmlText =" <body> \n" +
            "  \n" +
            "     <h1>Contabilidad Personal (v.1.0)</h1> \n" +
            "     <h2>AYUDA</h2>\n" +
            "     <p>Esta sencilla aplicacion se encarga de registrar todas las operaciones economica que  frecuentemente realiza  cualquier individuo en cualquier parte del mundo</p>\n" +
            "     <p>Esta app, tiene dos formas de controlar estas operaciones. La primera, es que se puede planificar todos los ingresos y gastos que continuamente son fijos y se puede conocer los resultados. Es una\n" +
            "        manera sencilla y muy practica. Y la segunda es registrar los gastos y los ingresos en el momento que se realizan. Al final, se obtendra el resultado. Como tambien un registros de todas los operaciones.</p>\n" +
            "      <h3> Como empieza esta app? </h3>\n" +
            "       <p> Se comienza con los SETTING, que no es mas que el inicio o configuracion de la app. Aqui se actualizan todas las actividades. Comenzando con el Capital que se tiene al comenzar la operacion. Esto es \n" +
            "        el valor del saldo de su cuenta de banco o la cantidad de dinero con que se cuenta. A continuacion El salario, y otros ingresos y le siguen los gastos. Esto es los valores y de los ingresos y gastos que son fijos. Que \n" +
            "        se repiten mes tras mes. Esto se hace una sola vez. En caso de cambios se pueden actualizar. Una vez terminado con los SETTING se puede capturar los Ingresos y de igualmente los Gastos.. Esta app cuenta con el servicio\n" +
            "        de la camara.. Puede tomar fotos de todos los recibos y documentos que den fe a los datos capturados. Y finalmente los resultados. Que solo es de consulta. Te da toda la informacion necesaria que necesitas.\n" +
            "\n" +
            "    </p>\n" +
            "     </p>\n" +
            "   </body>\n" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

            TextView htmlTextView = (TextView) findViewById(R.id.html_text);


            htmlTextView.setText(Html.fromHtml(htmlText, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                            int id;
                            if (source.equals("scr4.png")) {
                                    id = R.drawable.scr4;
                            } else {
                                    return null;
                            }

                            Drawable d = getResources().getDrawable(id);
                            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                            return d;
                    }
            }, null));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y); /*
                                     * create the transformation in the matrix
                                     * of points
                                     */
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist;
                    /*
                     * setting the scaling of the matrix...if scale > 1 means
                     * zoom in...if scale < 1 means zoom out
                     */
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true;
    }
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        //return Math.sqrt(x * x + y * y);
        // return FloatMath.sqrt(x*x+y*y);
        return (float) Math.sqrt((double) (x*x+y*y));
    }


    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Event", sb.toString());
    }

    public static class gastos extends AppCompatActivity implements View.OnClickListener {
        private Spinner spGastos;
        private EditText eImporte, eDetalles;
        private ImageView btnCamara, fotoCamara;
        private String NOMBRECARPETA="GASTOS";
        private String NOMBREFOTO="";
        private FrameLayout frameFoto;
        private Button btnAgregar, btnEditar;
        private ListView lvGastos;

        Time today = new Time(Time.getCurrentTimezone());
        private DBAdapter myDB;



       ZoomControls zoomControls;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gastos);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setVisibility(View.VISIBLE);



            spGastos = (Spinner) findViewById(R.id.spinnerGastos);
            eImporte = (EditText) findViewById(R.id.IdImporteGastos);
            eDetalles = (EditText)findViewById(R.id.idDetalles);
            btnCamara = (ImageView) findViewById(R.id.IdCamara);
            fotoCamara= (ImageView) findViewById(R.id.idFoto);
            frameFoto = (FrameLayout)findViewById(R.id.IdFrameFoto);
            lvGastos = (ListView) findViewById(R.id.listViewGastos);

            btnEditar = (Button)findViewById(R.id.IdEditar);
            btnAgregar = (Button) findViewById(R.id.IdAgregar);

            eImporte.setText("0");

            OpenDB();

            populateListView();
           // ListViewItemClick();



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

        }

        private void OpenDB(){

            myDB = new DBAdapter(this);
            myDB.open();

        }


        public void onClick(View v) {
            switch (v.getId()){
                case R.id.IdAgregar:
                    today.setToNow();
                    String timestamp = today.format("%y-%m-%d %H:%M:%S");
                    String tipo ="Gastos";
                    String mGastos = spGastos.getSelectedItem().toString();
                    String mImporte =eImporte.getText().toString();
                    Double valor;
                    valor = Double.parseDouble("-"+mImporte);
                    String mDetalles =eDetalles.getText().toString();
                    String path = "/"+NOMBRECARPETA+"/"+NOMBREFOTO;
                    //Toast.makeText(this, "El path es :"+path, Toast.LENGTH_SHORT).show();

                    // Toast.makeText(this, "El valor del spinner es: "+mActividad+ " y el valor es:"+valor, Toast.LENGTH_LONG).show();

                    if (!TextUtils.isEmpty(eImporte.getText().toString())){
                        OpenDB();
                        myDB.insertRow2(timestamp, tipo, mGastos, valor, path, mDetalles);
                       // Toast.makeText(this, "los valores son:" + timestamp + " el tipo es:" + tipo + "la actividada es:" + mGastos + "el valor es" + valor, Toast.LENGTH_LONG).show();
                    }
                    eImporte.setText("0");
                    eDetalles.setText(" ");
                    frameFoto.setVisibility(View.INVISIBLE);
                    lvGastos.setVisibility(View.VISIBLE);

                    populateListView();

                     break;
                case R.id.IdEditar:
                    Intent iRgastos = new Intent(this, EditarGastos.class);
                    startActivity(iRgastos);
                    break;

                case R.id.IdCamara:
                    frameFoto.setVisibility(View.VISIBLE);
                    lvGastos.setVisibility(View.INVISIBLE);
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

    /*
                case R.id.btn4:
                case R.id.btn9: {
                    Intent iResult = new Intent(this, doSetting.class);
                    startActivity(iResult);
                    break;}
                case R.id.btn5:
                case R.id.btn10:
                    Toast.makeText(this, "Este es mi  boton ayuda", Toast.LENGTH_SHORT).show();
                    break;
    */
            }


        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            //Comprovamos que la foto se a realizado
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
            Cursor cursor = myDB.getAllRows2();
            String[] fromFieldName = new String[] {DBAdapter.KEY_ROWID, DBAdapter.KEY_DESCRIP,DBAdapter.KEY_VALOR };
            int[] toViewIDs = new int[] {R.id.v0,R.id.v1,R.id.v2};
            //  Toast.makeText(this,"estoy en el metodo populate",Toast.LENGTH_LONG).show();
            SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.content_gastos_items, cursor,fromFieldName, toViewIDs,0 );

            ListView myList = (ListView) findViewById(R.id.listViewGastos);
            myList.setAdapter(myCursorAdapter);

        }
        public void ListViewItemClick(){
            OpenDB();
            ListView myList = (ListView) findViewById(R.id.listViewActividades);
            myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final long xId = id;

                    AlertDialog.Builder builder = new AlertDialog.Builder(gastos.this);

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
                String tipo ="Gastos";
                String mGastos = spGastos.getSelectedItem().toString();
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
            populateListView();
        }
    }
}
