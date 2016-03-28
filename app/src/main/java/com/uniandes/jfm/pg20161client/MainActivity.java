package com.uniandes.jfm.pg20161client;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.localserver.LocalTestServer;
import org.apache.http.protocol.HttpRequestHandler;
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

    public final static String URL_MTC = "192.168.0.30";
    public final static String PUERTO_MTC = "4000";
    public final static String APP_ID = "user04";
    private HttpRequestHandler hand;
    public int opcion1;
    public int opcion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opcion1 = 0;
        opcion2 = 0;
    }

    public void servHTTP(View view){
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Handler");
                RegisterThread r = new RegisterThread(getBaseContext());
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
            mButton.setText("ON");
            mButton.setBackgroundColor(0xFF00FF00);
            System.out.println("Opcion 1 ON");
        }
        else if(opcion1==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o1);
            mButton.setText("OFF");
            mButton.setBackgroundColor(0xFFFF0000);
            System.out.println("Opcion 1 OFF");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o1);
            mButton.setText("AUTO");
            mButton.setBackgroundColor(0xFF00BFFF);
            System.out.println("Opcion 1 AUTO");
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
            mButton.setText("ON");
            mButton.setBackgroundColor(0xFF00FF00);
            System.out.println("Opcion 2 ON");
        }
        else if(opcion2==2)//OFF
        {
            Button mButton=(Button)findViewById(R.id.button_o2);
            mButton.setText("OFF");
            mButton.setBackgroundColor(0xFFFF0000);
            System.out.println("Opcion 2 OFF");
        }
        else//AUTO
        {
            Button mButton=(Button)findViewById(R.id.button_o2);
            mButton.setText("AUTO");
            mButton.setBackgroundColor(0xFF00BFFF);
            System.out.println("Opcion 2 AUTO");
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
        String url = "http://"+URL_MTC+":"+PUERTO_MTC+"/m2m/applications"+urlFin;
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
