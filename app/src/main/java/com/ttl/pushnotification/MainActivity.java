package com.ttl.pushnotification;

import android.support.multidex.MultiDex;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ttl.pushnotification.DAO.DataBase;
import com.ttl.pushnotification.controller.ApplicationUtils;
import com.ttl.pushnotification.model.NotificationModel;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public NotificationAdapter notificationAdapter;
    public LinearLayoutManager linearLayoutManager ;
    public SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("PGC");

        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");


        if(title != null && body != null){
            insertIntoDatabase(title , body);
            Toast.makeText(this, ""+title + " :: body " + body , Toast.LENGTH_SHORT).show();
        }


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateNotificationRecycler();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateNotificationRecycler();
    }

    private void insertIntoDatabase(String title, String body) {
        DataBase.getInstance(this).getDao().insertUser(new NotificationModel(title , body , "AVAILABLE" , ApplicationUtils.getDate() ));
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    List<NotificationModel> notificationList;

    public void updateNotificationRecycler() {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                notificationList = DataBase.getInstance(MainActivity.this).getDao().getAllNotifications();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                notificationAdapter = new NotificationAdapter(MainActivity.this , notificationList);
                recyclerView.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
