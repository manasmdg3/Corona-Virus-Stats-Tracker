package com.manas.coronavirusstats;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity
                implements  AdapterView.OnItemSelectedListener {

    // Create the object of TextView
    TextView tvCountry, tvCases, tvRecovered,
            tvTests, tvActive,
            tvTodayCases, tvTotalDeaths,
            tvTodayDeaths,
            tvCasesPerOneMillion, tvDeathsPerOneMillion, tvTestsPerOneMillion;

    //Create object of Button
    Button btInfo, btHelp, btPrediction;

    String url, todayCases, todayDeaths, logoUrl, sCountry, iso2;

    //Initialize ArrayList of Countries
    ArrayList<String> countryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Button
        btInfo = (Button) findViewById(R.id.bt_about);
        btHelp = (Button) findViewById(R.id.bt_help);
        btPrediction = (Button) findViewById(R.id.bt_prediction);

        btPrediction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                fetchISO2();
                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called SecondActivity with the following code:

                /*Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("iso2", iso2);
                // start the activity connect to the specified class
                startActivity(intent);*/
            }
        });

        btInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called SecondActivity with the following code:

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });

        btHelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called SecondActivity with the following code:

                Intent intent = new Intent(MainActivity.this, HelpActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        */

        //Set Adapter
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,countryList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get Selected Value
                sCountry = parent.getItemAtPosition(position).toString();
                if(sCountry.equalsIgnoreCase("world")) {
                    url = "https://corona.lmao.ninja/v2/all";
                    tvCountry.setText(sCountry);
                }
                else{
                    url = "https://corona.lmao.ninja/v2/countries/" + sCountry;
                    tvCountry.setText(sCountry);
                }
                Toast.makeText(parent.getContext(), "Wait. Updating Information.", Toast.LENGTH_SHORT).show();
                fetchdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                url = "https://corona.lmao.ninja/v2/all";
                fetchdata();
            }
        });

        // Link those objects with their respective id's
           // that we have given in .XML file
        tvCountry
                = findViewById(R.id.tvCountry);
        tvCases
                = findViewById(R.id.tvCases);
        tvRecovered
                = findViewById(R.id.tvRecovered);
        tvTests
                = findViewById(R.id.tvTests);
        tvActive
                = findViewById(R.id.tvActive);
        tvTodayCases
                = findViewById(R.id.tvTodayCases);
        tvTotalDeaths
                = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths
                = findViewById(R.id.tvTodayDeaths);
        tvCasesPerOneMillion
                = findViewById(R.id.tvCasesPerOneMillion);
        tvDeathsPerOneMillion
                = findViewById(R.id.tvDeathsPerOneMillion);

        //Adding Country Names to CountryList
        countryList.add("World");
        countryList.add("Afghanistan");
        countryList.add("Albania");
        countryList.add("Algeria");
        countryList.add("Andorra");
        countryList.add("Angola");
        countryList.add("Anguilla");
        countryList.add("Antigua and Barbuda");
        countryList.add("Argentina");
        countryList.add("Armenia");
        countryList.add("Aruba");
        countryList.add("Australia");
        countryList.add("Austria");
        countryList.add("Azerbaijan");
        countryList.add("Bahamas");
        countryList.add("Bahrain");
        countryList.add("Bangladesh");
        countryList.add("Barbados");
        countryList.add("Belarus");
        countryList.add("Belgium");
        countryList.add("Belize");
        countryList.add("Benin");
        countryList.add("Bermuda");
        countryList.add("Bhutan");
        countryList.add("Bolivia");
        countryList.add("Bosnia");
        countryList.add("Botswana");
        countryList.add("Brazil");
        countryList.add("British Virgin Islands");
        countryList.add("Brunei");
        countryList.add("Bulgaria");
        countryList.add("Burkina Faso");
        countryList.add("Burundi");
        countryList.add("Cabo Verde");
        countryList.add("Cambodia");
        countryList.add("Cameroon");
        countryList.add("Canada");
        countryList.add("Caribbean Netherlands");
        countryList.add("Cayman Islands");
        countryList.add("Central African Republic");
        countryList.add("Chad");
        countryList.add("Channel Islands");
        countryList.add("Chile");
        countryList.add("China");
        countryList.add("Colombia");
        countryList.add("Comoros");
        countryList.add("Congo");
        countryList.add("Costa Rica");
        countryList.add("Croatia");
        countryList.add("Cuba");
        countryList.add("Curaçao");
        countryList.add("Cyprus");
        countryList.add("Czechia");
        countryList.add("Côte d'Ivoire");
        countryList.add("DRC");
        countryList.add("Denmark");
        countryList.add("Diamond Princess");
        countryList.add("Djibouti");
        countryList.add("Dominica");
        countryList.add("Dominican Republic");
        countryList.add("Ecuador");
        countryList.add("Egypt");
        countryList.add("El Salvador");
        countryList.add("Equatorial Guinea");
        countryList.add("Eritrea");
        countryList.add("Estonia");
        countryList.add("Ethiopia");
        countryList.add("Falkland Islands (Malvinas)");
        countryList.add("Faroe Islands");
        countryList.add("Fiji");
        countryList.add("Finland");
        countryList.add("France");
        countryList.add("French Guiana");
        countryList.add("French Polynesia");
        countryList.add("Gabon");
        countryList.add("Gambia");
        countryList.add("Georgia");
        countryList.add("Germany");
        countryList.add("Ghana");
        countryList.add("Gibraltar");
        countryList.add("Greece");
        countryList.add("Greenland");
        countryList.add("Grenada");
        countryList.add("Guadeloupe");
        countryList.add("Guatemala");
        countryList.add("Guinea-Bissau");
        countryList.add("Guyana");
        countryList.add("Haiti");
        countryList.add("Honduras");
        countryList.add("Hong Kong");
        countryList.add("Hungary");
        countryList.add("Iceland");
        countryList.add("India");
        countryList.add("Indonesia");
        countryList.add("Iran");
        countryList.add("Iraq");
        countryList.add("Ireland");
        countryList.add("Isle of Man");
        countryList.add("Israel");
        countryList.add("Italy");
        countryList.add("Jamaica");
        countryList.add("Japan");
        countryList.add("Jordan");
        countryList.add("Kazakhstan");
        countryList.add("Kenya");
        countryList.add("Kuwait");
        countryList.add("Kyrgyzstan");
        countryList.add("Lao People's Democratic Republic");
        countryList.add("Latvia");
        countryList.add("Lebanon");
        countryList.add("Lesotho");
        countryList.add("Liberia");
        countryList.add("Libyan Arab Jamahiriya");
        countryList.add("Liechtenstein");
        countryList.add("Liechtenstein");
        countryList.add("Luxembourg");
        countryList.add("MS Zaandam");
        countryList.add("Macao");
        countryList.add("Macedonia");
        countryList.add("Madagascar");
        countryList.add("Malawi");
        countryList.add("Malaysia");
        countryList.add("Maldives");
        countryList.add("Mali");
        countryList.add("Malta  ");
        countryList.add("Martinique");
        countryList.add("Mauritania");
        countryList.add("Mauritius");
        countryList.add("Mayotte");
        countryList.add("Mexico");
        countryList.add("Moldova");
        countryList.add("Monaco");
        countryList.add("Mongolia");
        countryList.add("Montenegro");
        countryList.add("Montserrat");
        countryList.add("Morocco");
        countryList.add("Mozambique");
        countryList.add("Myanmar");
        countryList.add("Namibia");
        countryList.add("Nepal");
        countryList.add("Netherlands");
        countryList.add("New Caledonia");
        countryList.add("New Zealand");
        countryList.add("Nicaragua");
        countryList.add("Niger");
        countryList.add("Nigeria");
        countryList.add("Norway");
        countryList.add("Oman");
        countryList.add("Pakistan");
        countryList.add("Palestine");
        countryList.add("Panama");
        countryList.add("Papua New Guinea");
        countryList.add("Paraguay");
        countryList.add("Peru");
        countryList.add("Philippines");
        countryList.add("Poland");
        countryList.add("Portugal");
        countryList.add("Qatar");
        countryList.add("Romania");
        countryList.add("Russia");
        countryList.add("Rwanda");
        countryList.add("Réunion");
        countryList.add("S. Korea");
        countryList.add("Saint Kitts and Nevis");
        countryList.add("Saint Lucia");
        countryList.add("Saint Martin");
        countryList.add("Saint Pierre Miquelon");
        countryList.add("Saint Vincent and the Grenadines");
        countryList.add("San Marino");
        countryList.add("Sao Tome and Principe");
        countryList.add("Saudi Arabia");
        countryList.add("Senegal");
        countryList.add("Serbia");
        countryList.add("Seychelles");
        countryList.add("Sierra Leone");
        countryList.add("Singapore");
        countryList.add("Sint Maarten");
        countryList.add("Slovakia");
        countryList.add("Slovenia");
        countryList.add("Somalia");
        countryList.add("South Africa");
        countryList.add("South Sudan");
        countryList.add("Spain");
        countryList.add("Sri Lanka");
        countryList.add("St. Barth");
        countryList.add("Sudan");
        countryList.add("Suriname");
        countryList.add("Swaziland");
        countryList.add("Sweden");
        countryList.add("Switzerland");
        countryList.add("Syrian Arab Republic");
        countryList.add("Taiwan");
        countryList.add("Tajikistan");
        countryList.add("Tanzania");
        countryList.add("Thailand");
        countryList.add("Timor-Leste");
        countryList.add("Togo");
        countryList.add("Trinidad and Tobago");
        countryList.add("Tunisia");
        countryList.add("Turkey");
        countryList.add("Turks and Caicos Islands");
        countryList.add("UAE");
        countryList.add("UK");
        countryList.add("USA");
        countryList.add("Uganda");
        countryList.add("Ukraine");
        countryList.add("Uruguay");
        countryList.add("Uzbekistan");
        countryList.add("Venezuela");
        countryList.add("Vietnam");
        countryList.add("Western Sahara");
        countryList.add("Yemen");
        countryList.add("Zambia");
        countryList.add("Zimbabwe");

        // Creating a method fetchdata()
        //fetchdata();
    }

    private void fetchdata()
    {

        StringRequest request
                = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        // Handle the JSON object and
                        // handle it inside try and catch
                        try {

                            // Creating object of JSONObject
                            JSONObject jsonObject
                                    = new JSONObject(
                                    response.toString());

                            // Set the data in text view
                            // which are available in JSON format
                            // Note that the parameter inside
                            // the getString() must match
                            // with the name given in JSON format
                            //getResources().getString(R.string.plus)

                            tvCasesPerOneMillion.setText(
                                    jsonObject.getString(
                                            "casesPerOneMillion"));
                            tvDeathsPerOneMillion.setText(
                                    jsonObject.getString(
                                            "deathsPerOneMillion"));
                            anni(tvCases, jsonObject.getString("cases"));
                            anni(tvRecovered, jsonObject.getString("recovered"));
                            anni(tvTests, jsonObject.getString("tests"));
                            anni(tvActive, jsonObject.getString("active"));
                            anni(tvTotalDeaths, jsonObject.getString("deaths"));
                            todayCases = "+"+jsonObject.getString("todayCases");
                            todayDeaths = "+"+jsonObject.getString("todayDeaths");
                            tvTodayCases.setText(todayCases);
                            tvTodayDeaths.setText(todayDeaths);

                            if (!sCountry.equalsIgnoreCase("world"))
                                btPrediction.setVisibility(View.VISIBLE);
                            else
                                btPrediction.setVisibility(View.INVISIBLE);

                            //Get Country Code
                            iso2 = jsonObject.getString("iso2");

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(
                                MainActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue
                = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //fetchData foe iso2 value
    private void fetchISO2()
    {

        StringRequest request
                = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        // Handle the JSON object and
                        // handle it inside try and catch
                        try {

                            // Creating object of JSONObject
                            JSONObject jsonObject
                                    = new JSONObject(
                                    response.toString());

                            //Get Country Info JSONObject
                            JSONObject countryInfo = jsonObject.getJSONObject("countryInfo");

                            //Get Country Code
                            iso2 = countryInfo.getString("iso2");

                            // Intents are objects of the android.content.Intent type. Your code can send them
                            // to the Android system defining the components you are targeting.
                            // Intent to start an activity called SecondActivity with the following code:
                            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                            intent.putExtra(Intent.EXTRA_TEXT, iso2);
                            intent.putExtra("countryName", sCountry);
                            intent.putExtra("todayTotalCases", jsonObject.getString("cases"));
                            // start the activity connect to the specified class
                            startActivity(intent);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(
                                MainActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue
                = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //Number Increase Animation
    private void anni(final TextView view, String value){
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0,Integer.parseInt(value));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
