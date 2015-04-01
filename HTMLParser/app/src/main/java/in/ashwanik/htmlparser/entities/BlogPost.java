package in.ashwanik.htmlparser.entities;

public class BlogPost {
    private String text;
    private String URL;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "text='" + text + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
