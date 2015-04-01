package in.ashwanik.htmlparser.webrequesthandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseHttpRequest {

    ProgressDialog progressDialog;
    int responseCode;
    IAsyncCallback callback;
    Response.Listener<String> stringResponseListener;
    private IHTMLParser htmlParser;
    private String url;
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            StringWriter errors = new StringWriter();
            error.printStackTrace(new PrintWriter(errors));
            dismissProgressDialog();
            callback.onError(error.toString());
        }
    };
    private Context context;

    public BaseHttpRequest(Activity localActivity,
                           String url) {

        context = localActivity;
        this.url = url;
        setListeners();

    }

    public IHTMLParser getHtmlParser() {
        return htmlParser;
    }

    public void setHtmlParser(IHTMLParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    public String getCompleteUrl() {
        return url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public IAsyncCallback getCallback() {
        return callback;
    }

    public Context getContext() {
        return context;
    }

    private void setListeners() {
        stringResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HTMLParseAsyncTask task = new HTMLParseAsyncTask();
                task.setCurrentRequest(BaseHttpRequest.this);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, response);
            }
        };
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void execute(IAsyncCallback callback, RequestQueue requestQueue) {
        this.callback = callback;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        dismissProgressDialog();
        progressDialog.show();
        addToRequestQueue(requestQueue, getStringRequest());
    }

    public <X> void addToRequestQueue(RequestQueue requestQueue, Request<X> req) {
        requestQueue.add(req);
    }

    Request<String> getStringRequest() {
        return new StringRequest(Request.Method.GET, url, stringResponseListener, errorListener);
    }

}
