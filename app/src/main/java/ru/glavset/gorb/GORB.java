package ru.glavset.gorb;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class GORB extends AppCompatActivity {
    public Context context;
    public static String NickNameString = "";
    public static String NickNameDefault = "Введите ваш ник";
    private String MyCode64String = "";
    public static final String APP_PREFERENCES = "AFsettings";
    public static final String APP_PREFERENCES_nickName = "counter";
    public static SharedPreferences mSettings;

    public static TextView textView;
    private TextView ResultC;
    private TextView ResultC64;
    private TextView ResultCesar;

    public RequestQueue queue;
    public String urla = "";
    public String url = "";
    public String NameTo = "";
    public String Lat = "";
    public String Lng = "";
    private LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_april_first);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.NickName);
        ResultC = (TextView) findViewById(R.id.ResultCode);
        ResultC64 = (TextView) findViewById(R.id.ResultCodeBase64);
        ResultCesar = (TextView) findViewById(R.id.ResultCodeCesar);
        textView.setHint(NickNameDefault);
        Button changeNickButton = (Button) findViewById(R.id.NickNameButton);
        changeNickButton.setOnClickListener(myButtonListener);
        Button genButton = (Button) findViewById(R.id.gen_button);
        genButton.setOnClickListener(myButtonListener);
        Button copyClipardButton = (Button) findViewById(R.id.copy_button);
        copyClipardButton.setOnClickListener(myButtonListener);
        queue = Volley.newRequestQueue(this);
        urla = "XXX.XXX.XXX.XXX";
        url = "";

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


    }

    public View.OnClickListener myButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.NickNameButton:
                    Intent intent = new Intent(GORB.this, InputNick.class);
                    startActivity(intent);

                    break;
                case R.id.gen_button:
                    if ((NickNameString.length() != 0) & (NickNameString != NickNameDefault)) {
                        //Criteria criteria = new Criteria();
                        //String bestProvider = locationManager.getBestProvider(criteria, false);
                        if (ActivityCompat.checkSelfPermission(GORB.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GORB.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                        if(location != null) {
                            double currentLongitude = location.getLongitude();
                            double currentLatitude = location.getLatitude();
                            int Longitude = (int) Math.floor(currentLongitude*1000000);
                            int Latitude = (int) Math.floor(currentLatitude*1000000);
                            url = urla + NickNameString + "\",\"Lat\":\"" + Latitude + "\",\"Lng\":\"" + Longitude + "\"}";
                        } else {
                            url = urla + NickNameString + "\"}";
                        }


                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                    NameTo = response.getString("Name");
                                    Lat = response.getString("Lat");
                                    Lng = response.getString("Lng");

                                    String formaTime = "dd-MMM-yyyy hh:mm:ss ZZZZ";
                                    SimpleDateFormat sdf = new SimpleDateFormat(formaTime, Locale.US);
                                    String dateTime = sdf.format(new Date(System.currentTimeMillis()));

                                    float x = Float.parseFloat(Lat)/1000000;
                                    float y = Float.parseFloat(Lng)/1000000;

                                    String Nike = "@" + NameTo +" ";
                                            String myCodeString = " From: @" + NickNameString + " Time stamp:" + dateTime + " Reswue data send: " + x + "," + y + " " + CodeString.getCode();
                                    String cesarString = Cesar.Decrypt(myCodeString);
                                    byte[] data = cesarString.getBytes("UTF-8");
                                    MyCode64String = Nike + Base64.encodeToString(data,Base64.NO_WRAP);

                                    ResultCesar.setText(Nike + cesarString);
                                    ResultC64.setText(MyCode64String);
                                    ResultC64.setTextColor(Color.BLUE);
                                    ResultC.setText(Nike + myCodeString);
                                    ResultC.setTextColor(Color.BLUE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Jopa:",error.toString());

                            }
                        });
                        queue.add(jsObjRequest);


                        break;
                    }
                case R.id.copy_button:
                    if (MyCode64String.length() != 0) {
                        ClipboardManager clipboard = (ClipboardManager)
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Copied Text", MyCode64String);
                        clipboard.setPrimaryClip(clip);

                        launchIngress(GORB.this);
                    }


                    break;


                default:
                    break;
            }

        }
    };


    public static void launchIngress(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.nianticproject.ingress", "com.nianticproject.ingress.NemesisActivity"));
                context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_nickName, NickNameString);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.contains(APP_PREFERENCES_nickName)) {
            NickNameString = mSettings.getString(APP_PREFERENCES_nickName, "");
            textView.setText(NickNameString);
            textView.setTextColor(Color.BLUE);
        }
    }




}
