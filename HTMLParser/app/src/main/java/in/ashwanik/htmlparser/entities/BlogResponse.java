package in.ashwanik.htmlparser.entities;

import java.util.ArrayList;

public class BlogResponse extends BaseObject {

    ArrayList<BlogPost> posts;

    public ArrayList<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<BlogPost> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "BlogResponse{" +
                "posts=" + posts +
                '}';
    }
}
