package in.ashwanik.htmlparser.parsers;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import in.ashwanik.htmlparser.entities.BaseObject;
import in.ashwanik.htmlparser.entities.BlogPost;
import in.ashwanik.htmlparser.entities.BlogResponse;
import in.ashwanik.htmlparser.webrequesthandler.IHTMLParser;

public class BlogPostParser implements IHTMLParser {
    @Override
    public BaseObject parseHTML(String htmlToParse) {
        BlogResponse response = new BlogResponse();
        try {
            Document doc = Jsoup.parse(htmlToParse);
            response.setPosts(new ArrayList<BlogPost>());
            BlogPost post;
            Elements containers = doc.select(".entry-header");
            for (Element container : containers) {
                Elements anchors = container.select("a");
                for (Element anchor : anchors) {
                    post = new BlogPost();
                    post.setURL(anchor.attr("href"));
                    post.setText(anchor.text());
                    response.getPosts().add(post);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return response;
    }
}
