package com.lightning.zhihulite;



import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lightning.zhihulite.adapter.NewsAdapter;
import com.lightning.zhihulite.task.LoadNewsTask;
import com.lightning.zhihulite.utility.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListRefreshFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mRefreshLayout;
    private ListView lv;
    private NewsAdapter adapter;
    private boolean isConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_refresh, container, false);
        isConnected = Utility.checkNetworkConnection(getContext());
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lv = (ListView) view.findViewById(R.id.lv);
        adapter = new NewsAdapter(getContext(), R.layout.listview_item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        if (isConnected) new LoadNewsTask(adapter).execute();
        return view;

    }



    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }


}
