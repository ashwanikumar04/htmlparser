package in.ashwanik.htmlparser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.ashwanik.htmlparser.common.BaseApplication;
import in.ashwanik.htmlparser.entities.BlogPost;
import in.ashwanik.htmlparser.entities.BlogResponse;
import in.ashwanik.htmlparser.parsers.BlogPostParser;
import in.ashwanik.htmlparser.webrequesthandler.BaseHttpRequest;
import in.ashwanik.htmlparser.webrequesthandler.IAsyncCallback;
import in.ashwanik.htmlparser.webrequesthandler.WebResponse;


public class MainActivity extends FragmentActivity {

    Button button;
    ListView listView;
    BlogResponse blogPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BaseHttpRequest request = new BaseHttpRequest(MainActivity.this, "http://blog.ashwanik.in/search?max-results=50");
                    request.setHtmlParser(new BlogPostParser());
                    IAsyncCallback callback = new IAsyncCallback() {
                        @Override
                        public void onComplete(WebResponse responseContent) {
                            blogPosts = (BlogResponse) responseContent.getTypedObject();
                            listView.setAdapter(new BlogPostAdapter(MainActivity.this));
                        }

                        @Override
                        public void onError(String errorData) {
                            Toast.makeText(MainActivity.this, errorData, Toast.LENGTH_SHORT).show();
                        }
                    };
                    request.execute(callback, BaseApplication.getInstance().getRequestQueue());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Some error occurred.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                            .parse(blogPosts.getPosts().get(i).getURL())));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    static class ViewHolder {
        TextView post;
    }

    public class BlogPostAdapter extends BaseAdapter {
        Activity context;
        ArrayList<BlogPost> arrayList;
        LayoutInflater inflater;

        public BlogPostAdapter(Activity context) {
            super();
            this.context = context;
            this.arrayList = blogPosts.getPosts();
            inflater = context.getLayoutInflater();
        }

        public int getCount() {
            return arrayList.size();
        }

        public BlogPost getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            BlogPost metaData = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.r_blogpost, parent, false);
                holder = new ViewHolder();

                holder.post = (TextView) convertView
                        .findViewById(R.id.post);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.post.setText(metaData.getText());
            return convertView;
        }

    }
}
