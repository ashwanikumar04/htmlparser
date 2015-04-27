package in.ashwanik.htmlparser.webrequesthandler;

public interface IAsyncCallback {
     void onComplete(WebResponse responseContent);
     void onError(String errorData);
}