package com.abercrombiefitch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.abercrombiefitch.R;
import com.abercrombiefitch.api.AbercrombieAPI;
import com.abercrombiefitch.api.RetrofitServiceFactory;
import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.api.model.ResponseData;
import com.abercrombiefitch.ui.base.BaseActivity;
import com.abercrombiefitch.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity implements Observer<ResponseData> {

    private static final String TAG = "MainActivity";

    private Observable<ResponseData> promotionResponse;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.content_recyclerview)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    private CardAdapter mCardAdapter;
    // Empty view
    private View inflatedStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);
        mCardAdapter.setOnItemClickListener(new CardAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, "Adapter " + String.valueOf(position) + " item clicked");
                Promotion clickedPromotion = mCardAdapter.getItem(position);
                try {
                    Intent intent = new Intent(MainActivity.this, PromotionDetailActivity.class);
                    intent.putExtra(PromotionDetailActivity.KEY_INTENT_PROMOTION_ITEM, new ObjectMapper().writeValueAsString(clickedPromotion));

                    String transitionName = getString(R.string.transition_name_image);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MainActivity.this,
                            view.findViewById(R.id.card_item_image),
                            transitionName
                    );
                    ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callPromotionsAPI();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });

        callPromotionsAPI();

    }

    private void callPromotionsAPI() {
            AbercrombieAPI service = RetrofitServiceFactory.createRetrofitService(this);
            service.getPromotions()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
    }

    /**
     * Creates an empty view layout to replace Recycleer view if no items present
     */
    private void createEmptyView() {
        inflatedStub = ((ViewStub)findViewById(R.id.empty_view)).inflate();
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyView() {
        if (inflatedStub != null) {
            inflatedStub.setVisibility(View.GONE);
        }
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    /**
     * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
     * <p>
     * The {@link Observable} will not call this method if it calls {@link #onError}.
     */
    @Override
    public void onCompleted() {
        Log.d(TAG, "Retrofit API completed");
        hideEmptyView();
    }

    /**
     * Notifies the Observer that the {@link Observable} has experienced an error condition.
     * <p>
     * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
     * {@link #onCompleted}.
     *
     * @param e the exception encountered by the Observable
     */
    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Retrofit API Error " + e.toString());
        Toast.makeText(MainActivity.this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
        createEmptyView();
        e.printStackTrace();
    }

    /**
     * Provides the Observer with a new item to observe.
     * <p>
     * The {@link Observable} may call this method 0 or more times.
     * <p>
     * The {@code Observable} will not call this method again after it calls either {@link #onCompleted} or
     * {@link #onError}.
     *
     * @param response the item emitted by the Observable
     */
    @Override
    public void onNext(ResponseData response) {
        mCardAdapter.clear();
        for(Promotion p : response.getPromotions()) {
            Log.d(TAG, "TITLE: " + p.getTitle());
            mCardAdapter.addData(p);
        }
    }

}
