package sg.edu.rp.c346.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView tv , tv2;
Spinner spn , spn2;
Button btnGo;

    ArrayList<String> alWebsites;
    ArrayAdapter<String> aaWebsites;
    String[] category;
    String [] sub1;
    String [] sub2;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    WebView wvWebsite;
    String [][] sites;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        spn = findViewById(R.id.spinner);
        spn2 = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.buttonGo);
        wvWebsite = findViewById(R.id.webView);

        wvWebsite.getSettings().setJavaScriptEnabled(true);
        wvWebsite.getSettings().setDisplayZoomControls(true);
        wvWebsite.setWebViewClient(new WebViewClient());


        category = getResources().getStringArray(R.array.category);
        sub1 = getResources().getStringArray(R.array.sub_category1);
        sub2 = getResources().getStringArray(R.array.sub_category2);

        alWebsites = new ArrayList<>();

        aaWebsites = new ArrayAdapter<>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,alWebsites);
        spn2.setAdapter(aaWebsites);


        sites = new String[][]{
                {
                        "http://www.rp.edu.sg", "https://www.rp.edu.sg/student-life"
                },
                {
                        "https://www.dbs.com.sg/" , "https://www.starhub.com/"
                }
        };



        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn.setSelection(pref.getInt("Cat",0));
                int pos = spn.getSelectedItemPosition();
                if(pos == 0){
                    alWebsites.addAll(Arrays.asList(sub1));
                }
                else{
                    alWebsites.addAll(Arrays.asList(sub2));
                }

                spn2.setSelection(pref.getInt("Sub",0));

                /*
                Intent intentWeb = new Intent(getBaseContext(),Websites.class);
                intentWeb.putExtra("Category",pos);
                startActivity(intentWeb);
                */

                spn.setVisibility(View.GONE);
                spn2.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                wvWebsite.loadUrl(sites[spn.getSelectedItemPosition()][spn2.getSelectedItemPosition()]);

            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                alWebsites.clear();
                edit.putInt("Cat",position);
                edit.commit();

                switch(position){
                    case 0:
                        alWebsites.addAll(Arrays.asList(sub1));
                        break;
                    case 1:
                        alWebsites.addAll(Arrays.asList(sub2));
                        spn2.setSelection(pref.getInt("Sub",0));
                        break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit.putInt("Sub",position);
                edit.commit();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
