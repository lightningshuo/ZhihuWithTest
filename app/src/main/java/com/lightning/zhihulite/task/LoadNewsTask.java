package com.lightning.zhihulite.task;

import android.os.AsyncTask;

import com.lightning.zhihulite.adapter.NewsAdapter;
import com.lightning.zhihulite.entity.News;
import com.lightning.zhihulite.http.Http;
import com.lightning.zhihulite.http.JsonHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class LoadNewsTask extends AsyncTask<Void, Void, List<News>> {
    private NewsAdapter adapter;
    private onFinishListener listener;

    public LoadNewsTask(NewsAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    public LoadNewsTask(NewsAdapter adapter, onFinishListener listener) {
        super();
        this.adapter = adapter;
        this.listener = listener;
    }

    @Override
    protected List<News> doInBackground(Void... params) {
        List<News> newsList = null;
        try {
            newsList = JsonHelper.parseJsonToList(Http.getLastNewsList());
        } catch (IOException | JSONException e) {

        } finally {
            return newsList;
        }
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter. refreshNewsList(newsList);
        if (listener != null) {
            listener.afterTaskFinish();
        }

    }

    public interface onFinishListener {
        public void afterTaskFinish();
    }
}
