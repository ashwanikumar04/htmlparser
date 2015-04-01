package in.ashwanik.htmlparser.webrequesthandler;


import in.ashwanik.htmlparser.entities.BaseObject;

public interface IHTMLParser {
    BaseObject parseHTML(String htmlToParse);
}