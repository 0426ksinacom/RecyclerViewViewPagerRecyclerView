package com.ht.testlist.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ht.testlist.adapter.MainAdapter;
import com.ht.testlist.adapter.PagerListAdapter;
import com.ht.testlist.fragment.PagerFragment;
import com.ht.testlist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2018/8/22 0022
 * My email : logisong@163.com
 * The role of this :
 */
public class MainActivity extends AppCompatActivity implements MainAdapter.PagerChangeListener{

    private List<String> data = new ArrayList<>();
    private List<PagerFragment> fragments = new ArrayList<>();
    private MainAdapter mainAdapter;
    private PagerFragment currentFragment;

    public RecyclerView rv;
    public static int rvYPosition=-10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data.add("推荐");
        data.add("男装");
        data.add("女装");
        data.add("童装");
        data.add("鞋子");
        rv = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        rv.setNestedScrollingEnabled(true);
        //状态栏高度
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            statusBarHeight =getResources().getDimensionPixelSize(resourceId);
        }
        //屏幕高度
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        for (int i = 0; i <data.size() ; i++) {
            fragments.add(PagerFragment.newInstance(data.get(i))) ;
        }
        final float scale = dm.density;
        int i = (int) (54 * scale + 0.5f);
        currentFragment=fragments.get(0);
        mainAdapter = new MainAdapter(this,getSupportFragmentManager(),data, fragments,dm.heightPixels-statusBarHeight-i);
        rv.setAdapter(mainAdapter);
        mainAdapter.setPagerChangeListener(this);
        mainAdapter.setOnItemClickListener(new PagerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getApplicationContext(),"外部部RV  "+position,Toast.LENGTH_LONG).show();
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void adjustIntercept(boolean b){

    }

    @Override
    public void pagerChange(int position) {
        currentFragment=fragments.get(position);
    }

}
