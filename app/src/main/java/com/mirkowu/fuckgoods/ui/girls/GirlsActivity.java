package com.mirkowu.fuckgoods.ui.girls;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.BaseActivity;
import com.mirkowu.fuckgoods.network.bean.GankBean;
import com.mirkowu.fuckgoods.util.ImageUtil;
import com.mirkowu.fuckgoods.widget.CommonToolbar;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.util.List;

import butterknife.BindView;

public class GirlsActivity extends BaseActivity<GirlsPresenter>
        implements GirlsContract.View, XRecyclerView.LoadingListener, DragPhotoView.OnExitListener, DragPhotoView.OnTapListener {
    @BindView(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;

    GirlsListAdapter adapter;
    private int count = 10, page = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_girls;
    }

    @Override
    protected Toolbar getToolbar() {
        return new CommonToolbar.Builder().setTitle(R.string.girls).build(this);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initialize() {
        //
        mXRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mXRecyclerView.setLoadingListener(this);
        adapter = new GirlsListAdapter(getActivity());
        mXRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            // startActivity(getActivity(),);
            scaleImage(adapter.getItem(position));
        });

        mPresenter.getGirls(count, page);

    }

    private void scaleImage(GankBean item) {
        // 所有ImageView用法都可以
        DragPhotoView photoView = (DragPhotoView) findViewById(R.id.dragPhotoView);
        photoView.setVisibility(View.VISIBLE);
        ImageUtil.load(photoView, item.url);
        //必须添加一个onExitListener,在拖拽到底部时触发.
        photoView.setOnExitListener(this);

        photoView.setOnTapListener(this);
    }

//    public void startPhotoActivity(Context context, ImageView imageView) {
//        Intent intent = new Intent(context, DragPhotoActivity.class);
//        int location[] = new int[2];
//
//        imageView.getLocationOnScreen(location);
//        intent.putExtra("left", location[0]);
//        intent.putExtra("top", location[1]);
//        intent.putExtra("height", imageView.getHeight());
//        intent.putExtra("width", imageView.getWidth());
//
//        context.startActivity(intent);
//
//        //关闭系统共享元素动画
//        overridePendingTransition(0, 0);
//    }


    @Override
    public void getGirls(List<GankBean> data) {
        mXRecyclerView.refreshComplete();
        mXRecyclerView.loadMoreComplete();
        adapter.addData(data);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getGirls(count, page);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGirls(count, ++page);
    }

    @Override
    public void onExit(DragPhotoView dragPhotoView, float v, float v1, float v2, float v3) {

    }

    @Override
    public void onTap(DragPhotoView dragPhotoView) {

    }
}
