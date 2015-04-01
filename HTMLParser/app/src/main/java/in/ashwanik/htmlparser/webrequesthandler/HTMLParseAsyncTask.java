package in.ashwanik.htmlparser.webrequesthandler;

import android.os.AsyncTask;

import in.ashwanik.htmlparser.entities.BaseObject;


public class HTMLParseAsyncTask extends AsyncTask<String, Void, Void> {

    private BaseObject baseObject;
    private BaseHttpRequest currentRequest;

    public BaseHttpRequest getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(BaseHttpRequest currentRequest) {
        this.currentRequest = currentRequest;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        if (getCurrentRequest().getHtmlParser() != null) {
            baseObject = getCurrentRequest().getHtmlParser().parseHTML(params[0]);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        getCurrentRequest().dismissProgressDialog();
        try {
            WebResponse webResponse = new WebResponse(baseObject);
            getCurrentRequest().getCallback().onComplete(webResponse);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}