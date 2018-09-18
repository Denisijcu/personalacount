package todo.misapp.com.twotablesinonedb;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class PieEstadistico  extends ActionBarActivity {
    private PieChart pieChart;
    float debe,haber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        debe =  (float) bundle.getDouble("debitos");
        haber=  (float) bundle.getDouble("creditos");


        setContentView(R.layout.activity_pie_estadistico);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(30f);
        pieChart.setDrawingCacheEnabled(true);
        pieChart.setRotationEnabled(true);
        //pieChart.setDrawYValues(true);
        pieChart.setDrawingCacheBackgroundColor(10);
        pieChart.animateXY(1500, 1500);

		/*creamos una lista para los valores Y*/
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        valsY.add(new Entry(debe,0));
        valsY.add(new Entry(haber,1));

 		/*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();
        valsX.add("Gastos");
        valsX.add("Ingresos");

 		/*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.red_flat));
        colors.add(getResources().getColor(R.color.green_flat));

 		/*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        set1.setSliceSpace(3f);
        set1.setColors(colors);

		/*seteamos los valores de X*/
        PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }




}