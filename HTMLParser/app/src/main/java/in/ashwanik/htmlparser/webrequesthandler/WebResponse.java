package in.ashwanik.htmlparser.webrequesthandler;


import in.ashwanik.htmlparser.entities.BaseObject;

public class WebResponse {

    public BaseObject getTypedObject() {
        return baseObject;
    }

    private BaseObject baseObject;

    public WebResponse(BaseObject currentBaseObject) {
        baseObject = currentBaseObject;
    }


}
