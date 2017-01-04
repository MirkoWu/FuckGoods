package com.mirkowu.fuckgoods.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alessandro on 12/01/2016.
 */
public abstract class BaseRecycleViewAdapter<Dao, VH extends BaseRCViewHolder> extends RecyclerView.Adapter<BaseRCViewHolder> {
    private Context mContext;
    private List<Dao> mList = new ArrayList<>();
    private boolean openSelecter;//总开关 开启选择功能
    private int selectIndex = -1;//单个选中状态
    private boolean multiSelected;//是否多选 默认为单选
    private ArrayList<Integer> selectList = new ArrayList<>();//多选下标集合

    /**
     * 设置开启模式
     *
     * @param isOpen 开启选择模式  默认单选
     */
    public void setOpenSelecter(boolean isOpen) {
        this.openSelecter = isOpen;
        notifyDataSetChanged();

    }

    /**
     * 设置开启模式
     *
     * @param isOpen        开启选择模式
     * @param multiSelected 是否单选
     */
    public void setOpenSelecter(boolean isOpen, boolean multiSelected) {
        this.openSelecter = isOpen;
        this.multiSelected = multiSelected;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedList() {
        if (!openSelecter) throw new IllegalStateException(" please setOpenSelecter()  first!");
        return selectList;
    }

    public BaseRecycleViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRecycleViewAdapter(List<Dao> mList) {
        this.mList = mList;
    }

    public Context getContext() {
        return mContext;
    }

    public List<Dao> getData() {
        return mList;
    }

    public void setData(List<Dao> data) {
        if (data == null) data = new ArrayList<>();
        mList = data;
        notifyDataSetChanged();
    }

    public void addData(List<Dao> data) {
        if (data == null) data = new ArrayList<>();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public Dao getItem(int position) {
        return mList.get(position);
    }

    /**
     * 为什么调用  notifyDataSetChanged();  会重新创建ViewHolder?????  说好的复用呢
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //一定要传入parent 否则item宽度会没有填充
        VH vh = onCreateVh(parent, viewType);
        //OnItemClick
        vh.setOnItemClickListener((view, position) -> {
            //设置选择器
            if (openSelecter) {
                if (multiSelected) {
                    boolean s = selectList.contains(position) ? selectList.remove((Integer) position) : selectList.add(position);
                } else {
                    selectIndex = position;
                }
                notifyDataSetChanged();//刷新下
            }

            if (onItemClickListener != null)
                onItemClickListener.onItemClickListener(view, position);

        });
        //OnItemLongClick
        vh.setOnItemLongClickListener((view, position) -> {
            if (onItemLongClickListener != null)
                onItemLongClickListener.onItemLongClickListener(view, position);
        });
        return vh;
    }

    public abstract VH onCreateVh(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(final BaseRCViewHolder holder, final int position) {
        VH vh = (VH) holder;
        if (openSelecter) {
            vh.isSelected = multiSelected ? selectList.contains(position) : selectIndex == position;//设置状态
        }
        onBindVH(vh, mList.get(position), position);
    }


    public abstract void onBindVH(final VH holder, Dao data, final int position);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    /**
     * onItemClick
     */
    public RecyclerViewOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface RecyclerViewOnItemClickListener {
        /**
         * @param view     The view within the ViewHolder that was clicked.
         * @param position
         */
        void onItemClickListener(View view, int position);
    }

    /**
     * onItemLongClick
     */
    public RecyclerViewOnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(RecyclerViewOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public interface RecyclerViewOnItemLongClickListener {
        void onItemLongClickListener(View view, int position);
    }


    /**
     * @param onViewClickListener
     */
    public OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener<Dao> onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener<Dao> {
        void onViewClick(View view, int position, Dao data);
    }

    /**
     * onViewTouchListener
     */
    public OnViewTouchListener onViewTouchListener;

    public void setOnViewTouchListener(OnViewTouchListener<Dao> onViewTouchListener) {
        this.onViewTouchListener = onViewTouchListener;
    }

    public interface OnViewTouchListener<Dao> {
        void onViewTouch(View view, MotionEvent event, int position, Dao data);
    }


}
