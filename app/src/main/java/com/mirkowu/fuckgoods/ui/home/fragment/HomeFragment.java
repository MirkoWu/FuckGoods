package com.mirkowu.fuckgoods.ui.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.BaseLazyFragment;
import com.mirkowu.fuckgoods.network.bean.GankBean;
import com.mirkowu.fuckgoods.ui.article.AircleActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class HomeFragment extends BaseLazyFragment<HomePresenter>
        implements HomeContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;

    private GankListAdapter adapter;
    private int count = 10, page = 1;
    private String type;

    @Override
    protected void initEventAndData() {
        String[] arrayType = getResources().getStringArray(R.array.gank_type);
        type = arrayType[getPosition()];
        //
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mXRecyclerView.setLoadingListener(this);
        adapter = new GankListAdapter(getActivity());
        mXRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getActivity(), AircleActivity.class);
            intent.putExtra("url", adapter.getItem(position).url);
            startActivity(intent);
            //  scaleImage(adapter.getItem(position));
        });
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getGank(type, count, page);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void getGank(List<GankBean> data) {
        mXRecyclerView.refreshComplete();
        mXRecyclerView.loadMoreComplete();
        adapter.addData(data);
    }


    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getGank(type, count, page);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGank(type, count, ++page);
    }

}
