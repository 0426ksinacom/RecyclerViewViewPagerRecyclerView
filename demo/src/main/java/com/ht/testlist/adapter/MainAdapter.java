package com.ht.testlist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ht.testlist.R;
import com.ht.testlist.fragment.PagerFragment;
import com.ht.testlist.holder.PageViewHolder;

import java.util.List;

/**
 * Created by song on 2018/8/22 0022
 * My email : logisong@163.com
 * The role of this :
 */
public class MainAdapter extends RecyclerView.Adapter {

    private FragmentManager fragmentManager;
    private List<String> titles;
    private int height;
    private PageViewHolder pageViewHolder;
    private PagerAdapter adapter;
    private int lastItem;
    private List<PagerFragment> fragments;

    private PagerChangeListener pagerChangeListener;
    Context context;

    public MainAdapter(Context context, FragmentManager fragmentManager, List<String> titles, List<PagerFragment> fragments, int height) {
        this.fragmentManager = fragmentManager;
        this.height = height;
        this.fragmentManager = fragmentManager;
        this.titles = titles;
        this.fragments = fragments;
        this.context =context;
    }

    private int TOP_COUNT = 20;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
//            正常显示item宽度
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_normal, parent, false));
        } else {
            return new PageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_pager, parent, false));
//            不能正常显示高度
//            return new PageViewHolder(View.inflate(parent.getContext(), R.layout.rv_item_pager, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (position < TOP_COUNT) {
            MainViewHolder mainViewHolder = (MainViewHolder) holder;
            mainViewHolder.tv.setText("外部item " + position);
        } else {
            pageViewHolder = (PageViewHolder) holder;
            if (adapter == null) {
                adapter = new MainPagerAdapter(fragmentManager, titles, fragments);
            }
            pageViewHolder.mViewPager.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageViewHolder.tabLayout.setupWithViewPager(pageViewHolder.mViewPager);
            if (lastItem > 0) {
                pageViewHolder.mViewPager.setCurrentItem(lastItem);
            }
            pageViewHolder.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (pagerChangeListener != null) {
                        pagerChangeListener.pagerChange(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        //实现点击效果
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return TOP_COUNT + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < TOP_COUNT) {
            return 1;
        } else {
            return 2;
        }
    }



    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public MainViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface PagerChangeListener {
        void pagerChange(int position);
    }

    public void setPagerChangeListener(PagerChangeListener pagerChangeListener) {
        this.pagerChangeListener = pagerChangeListener;
    }


    //私有属性
    private PagerListAdapter.OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(PagerListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}
