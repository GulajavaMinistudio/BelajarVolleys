package tes.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tes.volley.gsonparser.ArrayBerita;
import tes.volley.gsonparser.Berita;
import tes.volley.gsonparser.GsonRekuest;
import tes.volley.gsonparser.JaksonRekuest;
import tes.volley.gsonparser.MultipartsRequest;
import tes.volley.volleynet.Apis;
import tes.volley.volleynet.Konstans;
import tes.volley.volleynet.SetDataAPI;
import tes.volley.volleynet.VolleySingletons;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog dialogprogs;
    private String jsonhasil = "";

    private Button tombolmintadata;
    private Button tombolmintadatagson;
    private Button tombolmintadatajakson;
    private Button tombolmintadatamultiparts;
    private String requestLinks = "";


    private TextView tekshasil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halamanminta);

        tombolmintadata = (Button) findViewById(R.id.tombol_mintadata);
        dialogprogs = new ProgressDialog(MainActivity.this);


//        requestLinks = SetDataAPI.getDataParamURL("1", Konstans.API_POSTBARU_HALAMAN);
        requestLinks = SetDataAPI.getDataParamURL("100", Konstans.API_POSTBARU_LIMIT);

        tekshasil = (TextView) findViewById(R.id.teks_hasil);
        tombolmintadata.setOnClickListener(listenertombol);

        tombolmintadatagson = (Button) findViewById(R.id.tombol_mintadatagson);
        tombolmintadatagson.setOnClickListener(listenertombolgson);

        tombolmintadatajakson = (Button) findViewById(R.id.tombol_mintadatajakson);
        tombolmintadatajakson.setOnClickListener(listenertomboljakson);

        tombolmintadatamultiparts = (Button) findViewById(R.id.tombol_mintadatamultiparts);
        tombolmintadatamultiparts.setOnClickListener(listenertombolmultipart);

        Log.w("LINK", "LINK " + requestLinks);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        VolleySingletons.getInstance(MainActivity.this).getmReqQueue().cancelAll("GET");
        VolleySingletons.getInstance(MainActivity.this).getmReqQueue().getCache().clear();


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


    View.OnClickListener listenertombol = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cekMintaData();
        }
    };


    View.OnClickListener listenertombolgson = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cekGsonMintaData();
        }
    };


    View.OnClickListener listenertomboljakson = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cekJaksonnMintaData();
        }
    };


    View.OnClickListener listenertombolmultipart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            cekVolleyMultipartsRequests();
        }
    };


    private void cekMintaData() {

        dialogprogs = new ProgressDialog(MainActivity.this);
        dialogprogs.setMessage("Memuat data...");
        dialogprogs.setCancelable(false);

        dialogprogs.show();

        tombolmintadata.setEnabled(false);

        StringRequest strReq = new StringRequest(Request.Method.GET, requestLinks,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("SUKSES", "SUKSES HASIL");
                        tekshasil.setText("" + response);

                        dialogprogs.dismiss();
                        tombolmintadata.setEnabled(true);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                        Log.w("ERROR", "ERROR HASIL");
                        tekshasil.setText(error.getMessage() + " ");

                        dialogprogs.dismiss();
                        tombolmintadata.setEnabled(true);
                    }
                }
        ) {

            //UNTUK BODY JIKA METODENYA POST DAN HEADER JIKA METODENYA GET DAN POST
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//
//                Map<String, String> parambody = new HashMap<>();
//                parambody.put("sampelparam","isi sampel");
//
//
//                return parambody;
//            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                super.getHeaders();
//
//                Map<String, String> paramheaders = new HashMap<>();
//                paramheaders.put("Content-Type","application/json");
//                paramheaders.put("apiKey","XXXXXXX");
//
//                paramheaders.put("sampelheaders","XXXXXXX");
//
//                return paramheaders;
//            }
        };


        DefaultRetryPolicy retrycoba = new DefaultRetryPolicy(2500, 2, 1);
        strReq.setRetryPolicy(retrycoba);

        //tambahkan ke request voli
        VolleySingletons.getInstance(MainActivity.this).addToRequestQueue(strReq, "GET");

    }


    //CEK DATA SECARA GSON
    private void cekGsonMintaData() {

        dialogprogs = new ProgressDialog(MainActivity.this);
        dialogprogs.setMessage("Memuat data...");
        dialogprogs.setCancelable(false);

        dialogprogs.show();
        tombolmintadatagson.setEnabled(false);

//        GsonRekuest<ArrayBerita> gsonReq = new GsonRekuest<>(Request.Method.GET, requestLinks, ArrayBerita.class, null,
//
//                new Response.Listener<ArrayBerita>() {
//                    @Override
//                    public void onResponse(ArrayBerita response) {
//
//                        List<Berita> arrberita = response.getResult();
//                        Log.w("PANJANG", "PANJANG ARRAY GSON " + arrberita.size());
//                        tekshasil.setText("Panjang array json " + arrberita.size());
//
//
//                        dialogprogs.dismiss();
//                        tombolmintadatagson.setEnabled(true);
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        error.printStackTrace();
//                        Log.w("ERROR", "ERROR GSON");
//
//                        tekshasil.setText("Panjang array json " + error.getMessage());
//
//                        dialogprogs.dismiss();
//                        tombolmintadatagson.setEnabled(true);
//                    }
//                }
//        );



        GsonRekuest<ArrayBerita> gsonreqs = Apis.getGsonRequestAllBerita(requestLinks,
                new Response.Listener<ArrayBerita>() {
                    @Override
                    public void onResponse(ArrayBerita response) {

                        List<Berita> arrberita = response.getResult();
                        Log.w("PANJANG", "PANJANG ARRAY GSON " + arrberita.size());
                        tekshasil.setText("Panjang array json " + arrberita.size());


                        dialogprogs.dismiss();
                        tombolmintadatagson.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.w("ERROR", "ERROR GSON");

                        tekshasil.setText("Panjang array json " + error.getMessage());

                        dialogprogs.dismiss();
                        tombolmintadatagson.setEnabled(true);
                    }
                }
        );






        //tambahkan ke permintaan
        VolleySingletons.getInstance(MainActivity.this).addToRequestQueue(gsonreqs);
    }



    private void cekVolleyMultipartsRequests() {

        dialogprogs = new ProgressDialog(MainActivity.this);
        dialogprogs.setMessage("Memuat data...");
        dialogprogs.setCancelable(false);

        dialogprogs.show();
        tombolmintadatamultiparts.setEnabled(false);


        Map<String, String> headers = new HashMap<>();
        Map<String, File> bodyfileupload = new HashMap<>();
        Map<String, String> stringupload = new HashMap<>();

        stringupload.put("sampel_keys","sampel isi string untuk multiparts");


        MultipartsRequest<ArrayBerita> multipartsRequest = new MultipartsRequest<>(requestLinks, ArrayBerita.class, headers, bodyfileupload, stringupload,
                new Response.Listener<ArrayBerita>() {
                    @Override
                    public void onResponse(ArrayBerita response) {

                        List<Berita> arrberita = response.getResult();
                        Log.w("PANJANG", "PANJANG ARRAY GSON " + arrberita.size());
                        tekshasil.setText("Panjang array json " + arrberita.size());


                        dialogprogs.dismiss();
                        tombolmintadatamultiparts.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.w("ERROR", "ERROR GSON");

                        tekshasil.setText("Panjang array json " + error.getMessage());

                        dialogprogs.dismiss();
                        tombolmintadatamultiparts.setEnabled(true);
                    }
                }
        );

        VolleySingletons.getInstance(MainActivity.this).addToRequestQueue(multipartsRequest);
    }





    //CEK DATA SECARA JAKSON
    private void cekJaksonnMintaData() {


        dialogprogs = new ProgressDialog(MainActivity.this);
        dialogprogs.setMessage("Memuat data...");
        dialogprogs.setCancelable(false);

        dialogprogs.show();
        tombolmintadatajakson.setEnabled(false);


        JaksonRekuest<ArrayBerita> jaksonreq = new JaksonRekuest<>(Request.Method.GET, requestLinks, ArrayBerita.class, null,
                new Response.Listener<ArrayBerita>() {
                    @Override
                    public void onResponse(ArrayBerita response) {


                        List<Berita> arrberita = response.getResult();
                        Log.w("PANJANG", "PANJANG ARRAY JAKSON " + arrberita.size());
                        tekshasil.setText("Panjang array json " + arrberita.size());


                        dialogprogs.dismiss();
                        tombolmintadatajakson.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        Log.w("ERROR", "ERROR JAKSON");

                        tekshasil.setText("Panjang array json " + error.getMessage());

                        dialogprogs.dismiss();
                        tombolmintadatajakson.setEnabled(true);
                    }
                }
        );


        DefaultRetryPolicy retrycoba = new DefaultRetryPolicy(2500, 2, 1);
        jaksonreq.setRetryPolicy(retrycoba);

        //tambahkan ke permintaan
        VolleySingletons.getInstance(MainActivity.this).addToRequestQueue(jaksonreq);
    }


}
