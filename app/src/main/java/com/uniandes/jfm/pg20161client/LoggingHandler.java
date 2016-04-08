package com.uniandes.jfm.pg20161client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
//Imegn tomada de http://images.all-free-download.com/images/graphiclarge/house_maison_116081.jpg
/**
 * Created by jfm on 3/26/16.
 */
/**
 * Created by IntelliJ IDEA.
 * User: Piotrek
 * To change this template use File | Settings | File Templates.
 */
public class LoggingHandler implements HttpRequestHandler {

    private Context context;
    public LoggingHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext httpContext) throws HttpException, IOException {

        String uriString = request.getRequestLine().getUri();
        Uri uri = Uri.parse(uriString);
        System.out.println(uriString);
        if (request instanceof HttpEntityEnclosingRequest) { //test if request is a POST
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            String body = EntityUtils.toString(entity); //here you have the POST body
            System.out.println(body);
            try {
                JSONObject j0 = new JSONObject(body);
                String v0 = j0.get("notify").toString();
                JSONObject j1 = new JSONObject(v0);
                String v1 = j1.get("representation").toString();
                JSONObject j2 = new JSONObject(v1);
                String v2 = j2.get("$t").toString();
                System.out.println("Medida: " + v2);
                Intent i = new Intent("some.action");
                i.putExtra("valor", v2);
                context.sendBroadcast(i);
                //TextView lab = (TextView)((Activity)context).findViewById(R.id.medida1);
                //lab.setText("Temp. Cocina: "+lab+"º");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
