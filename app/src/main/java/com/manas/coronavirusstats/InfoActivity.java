package com.manas.coronavirusstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

public class InfoActivity
        extends AppCompatActivity {




    // mEntries in this case is just an ArrayList store
    private final ArrayList<Integer> mEntries = new ArrayList<Integer>();

    LineDataSet lineDataSet;
    final ArrayList<Entry> entries = new ArrayList<>();
    final ArrayList<Entry> dailyEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        String iso2 = intent.getStringExtra(Intent.EXTRA_TEXT);
        final String countryName = intent.getStringExtra("countryName");
        final String todayTotalCases = intent.getStringExtra("todayTotalCases");

        //Add Back Button to Action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fetch

        JsonArrayRequest request = new JsonArrayRequest("https://covid19-api.org/api/prediction/"+iso2,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        final String[] months = new String[14];
                        mEntries.add(Integer.parseInt(todayTotalCases));
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                entries.add(new Entry((float)i, Float.parseFloat(jsonObject.getString("cases"))));
                                months[i] = jsonObject.getString("date");
                                mEntries.add(jsonObject.getInt("cases"));
                                dailyEntries.add(new Entry((float)i, (float)mEntries.get(i+1)-(float)mEntries.get(i)));
                            }
                            catch(JSONException e) {
                                //mEntries.add("Error: " + e.getLocalizedMessage());
                            }
                        }

                        //Line Chart
                        totalPredictionChart(months, countryName);
                        casesDailyPredictionChart(months, countryName);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(InfoActivity.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    void totalPredictionChart(final String[] dates, String countryName){
        //Line Chart
        LineChart lineChart = (LineChart) findViewById(R.id.lineChart);
        lineDataSet = new LineDataSet(entries, "Total Number of cases");
        lineDataSet.setColor(ContextCompat.getColor(InfoActivity.this, R.color.color_blue));
        lineDataSet.setValueTextColor(ContextCompat.getColor(InfoActivity.this, R.color.color_black));
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setLineWidth(5f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleColor(ContextCompat.getColor(InfoActivity.this, R.color.color_green));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //final String[] months = new String[]{"Tomorrow", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7", "Day 8", "Day 9", "Day 10", "Day 11", "Day 12", "Day 13", "Day 14", };
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return dates[(int) value];
            }
        };
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);
        lineChart.getDescription().setText("AI Prediction of Total cases of next Two weeks in "+countryName);
        lineChart.getDescription().setTextSize(10f);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.animateX(2000, Easing.EaseInQuad);
        lineChart.invalidate();
    }

    void casesDailyPredictionChart(final String[] dates, String countryName){
        //Line Chart
        LineChart lineChart = (LineChart) findViewById(R.id.lineChart1);
        lineDataSet = new LineDataSet(dailyEntries, "Number of Daily Cases");
        lineDataSet.setColor(ContextCompat.getColor(InfoActivity.this, R.color.color_blue));
        lineDataSet.setValueTextColor(ContextCompat.getColor(InfoActivity.this, R.color.color_black));
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setLineWidth(5f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleColor(ContextCompat.getColor(InfoActivity.this, R.color.color_green));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return dates[(int) value];
            }
        };
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);
        lineChart.setNoDataText("Loading...");
        lineChart.getDescription().setText("AI Prediction of Daily Cases of next Two weeks in "+countryName);
        lineChart.getDescription().setTextSize(10f);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.animateX(2000, Easing.EaseInQuad);
        lineChart.invalidate();
    }

    //For Back Button to work
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}