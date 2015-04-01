package in.ashwanik.htmlparser.webrequesthandler;

public interface IAsyncCallback {
    // ===========================================================
    // Methods
    // ===========================================================

    public void onComplete(WebResponse responseContent);

    public void onError(String errorData);


}