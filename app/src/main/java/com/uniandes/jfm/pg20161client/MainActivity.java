package com.uniandes.jfm.pg20161client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.localserver.LocalTestServer;
import org.apache.http.protocol.HttpRequestHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

//Info registro
/*
hostDir = "157.253.205.40"
portNum = "4000"
appId = "user"
*/

public class MainActivity extends ActionBarActivity {

    public final static String PUERTO_MTC = "4000";
    public final static String APP_ID = "user";
    private HttpRequestHandler hand;
    public String url_mtc;
    public int opcion1;
    public int opcion2;
    public int opcion3;
    public int opcion4;
    public int opcion5;
    public int opcion6;
    public String ip;
    MainActivity esta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        esta = this;
        opcion1 = 0;
        opcion2 = 0;
        opcion3 = 0;
        opcion4 = 0;
        opcion5 = 0;
        opcion6 = 0;
        EditText mEditTIPMTC=(EditText)findViewById(R.id.mtc_ip_input);
        mEditTIPMTC.setText("157.253.205.40");
        WifiManager mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        ip = Formatter.formatIpAddress(mWifiInfo.getIpAddress());
        System.out.println("- - - - - - - - IP: - - - - - - - - -");
        System.out.println(ip);
        MyReceiver receiver = new MyReceiver(new Handler()); // Create the receiver
        registerReceiver(receiver, new IntentFilter("some.action")); // Register receiver

        //sendBroadcast(new Intent("some.action")); // Send an example Intent
    }

    public class MyReceiver extends BroadcastReceiver {

        private final Handler handler; // Handler used to execute code on the UI thread

        public MyReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            // Post the UI updating code to our Handler
            handler.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Recibiendo...");
                    String dato = intent.getStringExtra("valor");
                    System.out.println(dato);
                    byte[] valueDecoded= Base64.decode(dato, Base64.DEFAULT);
                    System.out.println("Decodificado...");
                    String datoDeco = new String(valueDecoded);
                    System.out.println(datoDeco);
                    System.out.println("Decodificado2...");
                    String datoDeco2 = datoDeco.split("binaryContent")[1];
                    System.out.println(datoDeco2);
                    System.out.println("Decodificado3...");
                    String datoDeco3 = datoDeco2.split("\":\"")[1];
                    System.out.println(datoDeco3);
                    System.out.println("Decodificado4...");
                    String datoDeco4 = datoDeco3.split("\",\"")[0];
                    System.out.println(datoDeco4);
                    System.out.println("Decodificado5...");
                    byte[] valueDecoded2= Base64.decode(datoDeco4, Base64.DEFAULT);
                    String datoDeco5 = new String(valueDecoded2);
                    System.out.println(datoDeco5);
                    String valor = "";
                    try {
                        JSONObject j = new JSONObject(datoDeco5);
                        valor = j.getString("cmd");
                        System.out.println("Valor cmd: "+valor);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(valor.equals("onLamp"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorLamp1);
                        lab.setText("Lámpara 1 ON");
                        lab.setTextColor(Color.rgb(0, 255, 0));
                    }
                    else if(valor.equals("offLamp"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorLamp1);
                        lab.setText("Lámpara 1 OFF");
                        lab.setTextColor(Color.rgb(255, 0, 0));
                    }
                    else if(valor.equals("onFan"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorFan);
                        lab.setText("Ventilador ON");
                        lab.setTextColor(Color.rgb(0, 255, 0));
                    }
                    else if(valor.equals("offFan"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorFan);
                        lab.setText("Ventilador OFF");
                        lab.setTextColor(Color.rgb(255, 0, 0));
                    }
                    else if(valor.equals("onMW"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorHorno);
                        lab.setText("Horno ON");
                        lab.setTextColor(Color.rgb(0, 255, 0));
                    }
                    else if(valor.equals("offMW"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorHorno);
                        lab.setText("Horno OFF");
                        lab.setTextColor(Color.rgb(255, 0, 0));
                    }
                    else if(valor.equals("onWM"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorWM);
                        lab.setText("Lavadora ON");
                        lab.setTextColor(Color.rgb(0, 255, 0));
                    }
                    else if(valor.equals("offWM"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorWM);
                        lab.setText("Lavadora OFF");
                        lab.setTextColor(Color.rgb(255, 0, 0));
                    }
                    else if(valor.equals("onFF"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorLamp2);
                        lab.setText("Lámpara 2 ON");
                        lab.setTextColor(Color.rgb(0, 255, 0));
                    }
                    else if(valor.equals("offFF"))
                    {
                        TextView lab=(TextView)findViewById(R.id.sensorLamp2);
                        lab.setText("Lámpara 2 OFF");
                        lab.setTextColor(Color.rgb(255, 0, 0));
                    }
                }
            });
        }
    }

    public void servHTTP(View view){
        new Thread(new Runnable() {
            public void run() {
                //Registrar
                String json = "{\n" +
                        "  \"subscription\": {\n" +
                        "    \"contact\": \"http://"+ip+":8080\",\n" +
                        "    \"filterCriteria\": {\n" +
                        "      \"sizeUntil\": 30\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";
                String urlAdd = "/"+APP_ID+"/containers/cont1/contentInstances/subscriptions";
                try {
                    enviarHTTP(urlAdd,json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Handler");
                RegisterThread r = new RegisterThread(esta,ip);
                r.startThread();
            }
        }).start();
    }

    public void op1(View view) {
        if(opcion1==3)
        {
            opcion1=0;
        }
        opcion1++;
        if(opcion1==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o1);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzv);
            mButton.setBackground(drawable);
            System.out.println("Opcion 1 ON");
            pushDato("onLamp");
        }
        else if(opcion1==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o1);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzr);
            mButton.setBackground(drawable);
            System.out.println("Opcion 1 OFF");
            pushDato("offLamp");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o1);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luza);
            mButton.setBackground(drawable);
            System.out.println("Opcion 1 AUTO");
            //pushDato("1-AUTO");
        }
    }

    public void op2(View view) {
        if(opcion2==3)
        {
            opcion2=0;
        }
        opcion2++;
        if(opcion2==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o2);
            //mButton.setText("ON");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.ventiladorv);
            mButton.setBackground(drawable);
            System.out.println("Opcion 2 ON");
            pushDato("onFan");
        }
        else if(opcion2==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o2);
            //mButton.setText("OFF");
            //mButton.setBackgroundColor(0xFFFF0000);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.ventiladorr);
            mButton.setBackground(drawable);
            System.out.println("Opcion 2 OFF");
            pushDato("offFan");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o2);
            //mButton.setText("AUTO");
            //mButton.setBackgroundColor(0xFF00BFFF);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.ventiladora);
            mButton.setBackground(drawable);
            System.out.println("Opcion 2 AUTO");
        }
    }

    public void op3(View view) {
        if(opcion3==3)
        {
            opcion3=0;
        }
        opcion3++;
        if(opcion3==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o3);
            //mButton.setText("ON");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzv);
            mButton.setBackground(drawable);
            System.out.println("Opcion 3 ON");
            pushDato("onFF");
        }
        else if(opcion3==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o3);
            //mButton.setText("OFF");
            //mButton.setBackgroundColor(0xFFFF0000);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzr);
            mButton.setBackground(drawable);
            System.out.println("Opcion 3 OFF");
            pushDato("offFF");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o3);
            //mButton.setText("AUTO");
            //mButton.setBackgroundColor(0xFF00BFFF);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luza);
            mButton.setBackground(drawable);
            System.out.println("Opcion 3 AUTO");
        }
    }

    public void op4(View view) {
        if(opcion4==3)
        {
            opcion4=0;
        }
        opcion4++;
        if(opcion4==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o4);
            //mButton.setText("ON");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.hornov);
            mButton.setBackground(drawable);
            System.out.println("Opcion 4 ON");
            pushDato("onMW");
        }
        else if(opcion4==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o4);
            //mButton.setText("OFF");
            //mButton.setBackgroundColor(0xFFFF0000);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.hornor);
            mButton.setBackground(drawable);
            System.out.println("Opcion 4 OFF");
            pushDato("offMW");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o4);
            //mButton.setText("AUTO");
            //mButton.setBackgroundColor(0xFF00BFFF);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.hornoa);
            mButton.setBackground(drawable);
            System.out.println("Opcion 4 AUTO");
        }
    }

    public void op5(View view) {
        if(opcion5==3)
        {
            opcion5=0;
        }
        opcion5++;
        if(opcion5==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o5);
            //mButton.setText("ON");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.lavadorav);
            mButton.setBackground(drawable);
            System.out.println("Opcion 5 ON");
            pushDato("onWM");
        }
        else if(opcion5==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o5);
            //mButton.setText("OFF");
            //mButton.setBackgroundColor(0xFFFF0000);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.lavadorar);
            mButton.setBackground(drawable);
            System.out.println("Opcion 5 OFF");
            pushDato("offWM");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o5);
            //mButton.setText("AUTO");
            //mButton.setBackgroundColor(0xFF00BFFF);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.lavadoraa);
            mButton.setBackground(drawable);
            System.out.println("Opcion 5 AUTO");
        }
    }

    public void op6(View view) {
        if(opcion6==3)
        {
            opcion6=0;
        }
        opcion6++;
        if(opcion6==1)//ON
        {
            Button mButton=(Button)findViewById(R.id.button_o6);
            //mButton.setText("ON");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzv);
            mButton.setBackground(drawable);
            System.out.println("Opcion 6 ON");
        }
        else if(opcion6==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o6);
            //mButton.setText("OFF");
            //mButton.setBackgroundColor(0xFFFF0000);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luzr);
            mButton.setBackground(drawable);
            System.out.println("Opcion 6 OFF");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o6);
            //mButton.setText("AUTO");
            //mButton.setBackgroundColor(0xFF00BFFF);
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.luza);
            mButton.setBackground(drawable);
            System.out.println("Opcion 6 AUTO");
        }
    }

    public void push(View view) {
        System.out.println("- - - - - - - - BOTON - - - - - - - -");
        //Registro
        //new TareaRed().execute();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //Push
                    String json = "{\"cmd\":\"1\"}";
                    String urlAdd = "/"+APP_ID+"/containers/cont1/contentInstances";
                    enviarHTTP(urlAdd,json);
                }
                catch(Exception e)
                {
                    System.out.println(e.fillInStackTrace());
                }
            }
        }).start();
    }

    public void pushDato(final String dato) {
        System.out.println("- - - - - - - - BOTON - - - - - - - -");
        //Registro
        //new TareaRed().execute();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //Push
                    String json = "{\"cmd\":\""+dato+"\"}";
                    String urlAdd = "/"+APP_ID+"/containers/cont1/contentInstances";
                    enviarHTTP(urlAdd,json);
                }
                catch(Exception e)
                {
                    System.out.println(e.fillInStackTrace());
                }
            }
        }).start();
    }

    public void set(View view) {
        System.out.println("- - - - - - - - BOTON - - - - - - - -");
        //Registro
        //new TareaRed().execute();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //Registrar
                    String json = "{\"application\":{\"appId\":\""+APP_ID+"\"}}";
                    String urlAdd = "";
                    enviarHTTP(urlAdd,json);
                    //create access rights
                    json = "{\"accessRight\": {\n" +
                            "    \"id\": \"ar1\",\n" +
                            "    \"selfPermissions\": {\"permission\": [{\n" +
                            "      \"id\": \""+APP_ID+"\",\n" +
                            "      \"permissionFlags\": {\n" +
                            "        \"flag\": [\"READ\", \"WRITE\", \"CREATE\", \"DELETE\"]\n" +
                            "      },\n" +
                            "      \"permissionHolders\": {\n" +
                            "        \"applicationIDs\": {\"applicationID\": [\""+APP_ID+"\"]}\n" +
                            "      }\n" +
                            "    }, {\n" +
                            "      \"id\": \"otherApss\",\n" +
                            "      \"permissionFlags\": {\n" +
                            "        \"flag\": [\"READ\"]\n" +
                            "      },\n" +
                            "      \"permissionHolders\": {\n" +
                            "        \"applicationIDs\": {\"applicationID\": [\"na1\"]}\n" +
                            "      }\n" +
                            "    }]},\n" +
                            "    \"permissions\": {\"permission\": [{\n" +
                            "      \"id\": \""+APP_ID+"\",\n" +
                            "      \"permissionFlags\": {\n" +
                            "        \"flag\": [\"READ\", \"WRITE\", \"CREATE\", \"DELETE\"]\n" +
                            "      },\n" +
                            "      \"permissionHolders\": {\n" +
                            "       \"applicationIDs\": {\"applicationID\": [\""+APP_ID+"\"]}\n" +
                            "      }\n" +
                            "    }, {\n" +
                            "      \"id\": \"otherApss\",\n" +
                            "      \"permissionFlags\": {\n" +
                            "        \"flag\": [\"READ\"]\n" +
                            "      },\n" +
                            "      \"permissionHolders\": {\n" +
                            "        \"applicationIDs\": {\"applicationID\": [\"na1\"]}\n" +
                            "      }\n" +
                            "    }]}\n" +
                            "  }\n" +
                            "}";
                    urlAdd = "/"+APP_ID+"/accessRights";
                    enviarHTTP(urlAdd,json);
                    //update access rights
                    //json = "{\"accessRightID\":\"/m2m/applications/"+APP_ID+"/accessRights/ar1\"}";
                    //urlAdd = "/"+APP_ID+"/accessRightID";;
                    //enviarHTTP(urlAdd,json);

                    //Actualizar SearchStrings
                    //json = "{\"searchStrings\":{\"searchString\": [\"user\", \"temperature\"]}}";
                    //urlAdd = "/"+APP_ID+"/searchStrings";;
                    //enviarHTTP(urlAdd,json);

                    //Create a container
                    json = "{\n" +
                            "  \"container\": {\n" +
                            "    \"id\": \"cont1\",\n" +
                            "    \"accessRightID\": \"/m2m/applications/"+APP_ID+"/accessRights/ar1\"\n" +
                            "  }\n" +
                            "}";
                    urlAdd = "/"+APP_ID+"/containers";;
                    enviarHTTP(urlAdd,json);
                }
                catch(Exception e)
                {
                    System.out.println(e.fillInStackTrace());
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void enviarHTTP(String urlFin, String jsonMandar) throws Exception{
        //Crea URL
        EditText mEditTIPMTC=(EditText)findViewById(R.id.mtc_ip_input);
        url_mtc = mEditTIPMTC.getText().toString();
        String url = "http://"+url_mtc+":"+PUERTO_MTC+"/m2m/applications"+urlFin;
        System.out.println("URL: " + url);
        URL object = new URL(url);
        //Crea Conexion
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("POST");
        //Crea JSON
        String dataI = jsonMandar;
        System.out.println("Objeto a mandar: "+dataI);
        //Manda JSON
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(dataI);
        wr.flush();
        //
        //Se recibe
        //
        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        System.out.println("Respuesta:");
        System.out.println(HttpResult);
        System.out.println("Respuesta esperada:");
        System.out.println(HttpURLConnection.HTTP_OK);
        if (HttpResult == HttpURLConnection.HTTP_CREATED) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("- - - - - - - - - - - - - - - - - -");
            System.out.println("" + sb.toString());
            System.out.println("- - - - - - - - - - - - - - - - - -");
        } else {
            System.out.println(con.getResponseMessage());
        }
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
}
