package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.GraphMaking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.danishali.assignment03.myapplication.R;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Accounts.LoginActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Adapters.RecyclerViewAdapter;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Bookingsystem.Booking;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.DashBoard.MainActivity;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login.LoginLocalDAO;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Models.MedicalHistory;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.QRGenerator.QR;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.RestServices.VolleyRequestHandler;
import com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Vitals.Vitals;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class graphplot extends AppCompatActivity {
    private float x1,y1,x2,y2;
    private TapBarMenu tapBarMenu;
    private ImageView qr;
    private ImageView bookings;
    private LoginLocalDAO loginLocalDAO;
    private VolleyRequestHandler volleyRequestHandler;
    private GraphView graph;
    private  LineGraphSeries<DataPoint> series;
    private LineGraphSeries<DataPoint> series2;
    private   LineGraphSeries<DataPoint> series3;
    private ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphplot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tapBarMenu = (TapBarMenu) findViewById(R.id.tapBarMenugraph);
        qr = (ImageView) findViewById(R.id.qr);
        bookings = (ImageView) findViewById(R.id.bookings);
        loginLocalDAO = new LoginLocalDAO(this);
        volleyRequestHandler = new VolleyRequestHandler(this);
        series = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        series3 = new LineGraphSeries<DataPoint>();
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(graphplot.this, QR.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                graphplot.this.startActivity(mainswitch);
                onStop();

            }
        });

        bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(graphplot.this, Booking.class);
                mainswitch.putExtra("ID",loginLocalDAO.getLogin().getId());
                graphplot.this.startActivity(mainswitch);
                onStop();

            }
        });
        logout = (ImageView)findViewById(R.id.item1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainswitch = new Intent(graphplot.this, LoginActivity.class);
                loginLocalDAO.resetDb();
                graphplot.this.startActivity(mainswitch);
                onStop();

            }
        });


        graph = (GraphView) findViewById(R.id.graph);

        series.setTitle("Heart Rate");
        series2.setTitle("Respiratory Rate");
        series3.setTitle("Cholesterol");




        volleyRequestHandler.JSONobjectHandler("vitals/top/get/heartrate/graph/"+loginLocalDAO.getLogin().getId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                Log.i("check",jsonObject.toString());


                                series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                                        new DataPoint(Double.parseDouble(jsonObject.getString("vitalheartrate")), Double.parseDouble(jsonObject.getString("time").substring(11,13)))


                                });

                                series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                                        new DataPoint(Double.parseDouble(jsonObject.getString("vitalrespiratoryrate")), Double.parseDouble(jsonObject.getString("time").substring(11,13)))


                                });
                                series3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                                        new DataPoint(Double.parseDouble(jsonObject.getString("vitalcholesterol")), Double.parseDouble(jsonObject.getString("time").substring(11,13)))


                                });



                                graph.addSeries(series);
                                graph.setTitle("Vitals Graph");
                                series.setColor(Color.BLUE);
                                series.setDrawDataPoints(true);
                                series.setDataPointsRadius(15);
                                series.setThickness(5);


                                graph.addSeries(series2);
                                series2.setColor(Color.RED);
                                series2.setDrawDataPoints(true);
                                series2.setDataPointsRadius(15);
                                series2.setThickness(5);


                                graph.addSeries(series3);
                                series3.setColor(Color.BLACK);
                                series3.setDrawDataPoints(true);
                                series3.setDataPointsRadius(15);
                                series3.setThickness(5);




                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());

                    }
                });





//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 0),
//                new DataPoint(20, 20),
//                new DataPoint(40, 40),
//                new DataPoint(60, 60),
//                new DataPoint(80, 80),
//                new DataPoint(100,  100),
//                new DataPoint(120, 120),
//                new DataPoint(140, 140),
//                new DataPoint(160, 160),
//                new DataPoint(180, 180),
//                new DataPoint(200, 200),
//                new DataPoint(220, 220),
//                new DataPoint(240, 240),
//                new DataPoint(260, 260),
//                new DataPoint(280, 280),
//                new DataPoint(300, 300),
//
//        });
//        graph.addSeries(series);
//        graph.setTitle("Vitals Graph");
//        series.setColor(Color.GREEN);
//        series.setDrawDataPoints(true);
//        series.setDataPointsRadius(10);
//        series.setThickness(8);



//        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(10, 30),
//                new DataPoint(20, 33),
//                new DataPoint(24, 66),
//                new DataPoint(34, 23),
//                new DataPoint(40, 50)
//        });
//        graph.addSeries(series2);
//        series.setColor(Color.BLACK);
//        series.setDrawDataPoints(true);
//        series.setDataPointsRadius(10);
//        series.setThickness(8);
//
//        BarGraphSeries<DataPoint> barGraphSeries = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 10),
//                new DataPoint(10, 50),
//                new DataPoint(20, 30),
//                new DataPoint(30, 20),
//                new DataPoint(40, 60)
//        });
//        graph.addSeries(barGraphSeries);
//        barGraphSeries.setSpacing(10);

//        barGraphSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });
//
//        barGraphSeries.setSpacing(10);

//// draw values on top
//        barGraphSeries.setDrawValuesOnTop(true);
//        barGraphSeries.setValuesOnTopColor(Color.RED);
////series.setValuesOnTopSize(50);
//
//        // set second scale
//        graph.getSecondScale().addSeries(series2);
//// the y bounds are always manual for second scale
//        graph.getSecondScale().setMinY(0);
//        graph.getSecondScale().setMaxY(100);
//        series2.setColor(Color.RED);
//        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
//
//        // legend
//        series.setTitle("foo");
//        series2.setTitle("bar");
//        graph.getLegendRenderer().setVisible(true);
//        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//
//
//        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    // show normal x values
//                    return super.formatLabel(value, isValueX);
//                } else {
//                    // show currency for y values
//                    return super.formatLabel(value, isValueX) + " €";
//                }
//            }
//        });

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();

             if(x1 >  x2){
                Intent i = new Intent(graphplot.this, MainActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_heart) {
            Log.i("Heart", loginLocalDAO.getLogin().toString());
            Intent mainswitch = new Intent(graphplot.this, Vitals.class);
            graphplot.this.startActivity(mainswitch);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
