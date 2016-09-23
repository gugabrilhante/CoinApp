package gustavo.brilhante.coinapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import gustavo.brilhante.coinapp.interfaces.JsonCallback;


/**
 * Created by Gustavo on 14/09/2016.
 */
public class GenericGetAsyncRequest extends AsyncTask<String, Integer, Boolean> {

    ProgressDialog progDailog;
    public Context context;
    JsonCallback delegate;
    String jsonResultStr = "";
    String ArgStr = "";
    String idLoja = "";
    String dialogMessage = "Carregando";
    PowerManager.WakeLock wakeLock;
    boolean result;
    boolean showDialog= true;

    ArrayList<String> dataDownloaded = new ArrayList<String>();

    //ArrayList<String> listDialogNames;

    boolean varSync = false;

    public GenericGetAsyncRequest(Context context, JsonCallback delegate) {
        this.context = context;
        this.delegate = delegate;
        varSync = true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(wakeLock!=null)wakeLock.acquire();
        if(showDialog) {
            if (progDailog != null) {
                progDailog = null;
            }
            progDailog = new ProgressDialog(context, progDailog.THEME_TRADITIONAL);
            progDailog.setMessage("Carregando...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.show();
        }
        varSync = true;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        varSync = false;
        //progDailog.setMessage("Armazenando os dados");
        if(!result && !dataDownloaded.get(0).isEmpty())dataDownloaded.set(0, ""+false);
        delegate.callback(dataDownloaded);
        if(showDialog)progDailog.dismiss();
        if(wakeLock!=null)wakeLock.release();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        /*if(values[0]<listDialogNames.size())progDailog.setMessage("Baixando " + listDialogNames.get(values[0]));
        else progDailog.setMessage("Armazenando dados, essa operação pode levar alguns minutos.");*/
    }

    @Override
    protected Boolean doInBackground(String... params) {
        JSONArray array = new JSONArray();
        for(int i=0; i<params.length; i++){
            publishProgress(new Integer[] {i});
            Log.i("download", "baixando "+ params[i]);
            dataDownloaded.add(getRequest(params[i]));
            Log.i("downloaded", "baixado "+ dataDownloaded.get(i));
        }
        if(params.length==array.length())return true;
        else return false;
    }

    private String getRequest(String url){
        result = false;
        HttpResponse response = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet;
        StringBuilder builder = new StringBuilder();

        String jsonResult = "";

        try {
            String urlFinal="";
            if(!ArgStr.isEmpty())urlFinal = url + "/" + ArgStr;
            else urlFinal = url;

            httpGet = new HttpGet(urlFinal);

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-Type", "application/json");

            response = httpclient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;

                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }
                jsonResult = builder.toString();
                result = true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            if(wakeLock!=null)wakeLock.release();
        } catch (IOException e) {
            Log.i("exception", e.getMessage());
            if(wakeLock!=null)wakeLock.release();
        }

        return jsonResult;
    }


    public boolean isVarSync() {
        return varSync;
    }

    public void setVarSync(boolean varSync) {
        this.varSync = varSync;
    }

    public void setArgStr(String argStr) {
        ArgStr = argStr;
    }

    public void setWakeLock(PowerManager.WakeLock wakeLock) {
        this.wakeLock = wakeLock;
    }

    void callDialog(String message){
        progDailog.setMessage(message);
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();
    }

    public void setIdLoja(String idLoja) {
        this.idLoja = idLoja;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}