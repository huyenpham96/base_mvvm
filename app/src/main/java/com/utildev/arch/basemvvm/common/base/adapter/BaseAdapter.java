package com.utildev.arch.basemvvm.common.base.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.utildev.arch.basemvvm.BR;
import com.utildev.arch.basemvvm.R;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    final LayoutInflater layoutInflater;
    private List<T> itemList;
    private AdapterListener adapterListener;
    private LinearLayoutManager layoutManager;

    private int layoutRes;
    private boolean isLoading = true;

    private static final int VIEW_TYPE_ITEM = 555, VIEW_TYPE_LOADING = 111;

    public BaseAdapter(RecyclerView recyclerView, LinearLayoutManager layoutManager, int layoutRes) {
        layoutInflater = (LayoutInflater) recyclerView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutRes = layoutRes;
        this.layoutManager = layoutManager;
        itemList = new ArrayList<>();

        if (layoutManager != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                int visibleItemCount, totalItemCount, firstVisibleItem;
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                        if (isLoading) {
                            if (visibleItemCount + firstVisibleItem >= totalItemCount) {
                                adapterListener.onLoadMore();
                                isLoading = false;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = itemList.get(position);
        holder.getBinding().setVariable(BR.viewModel, item);
        holder.getBinding().setVariable(BR.listener, adapterListener);
        holder.getBinding().executePendingBindings();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return new ViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.view_loadmore, viewGroup, false));
        } else {
            return new ViewHolder(DataBindingUtil.inflate(layoutInflater, layoutRes, viewGroup, false));
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (layoutManager != null) {
            return position + 1 == layoutManager.getItemCount() ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }
        return super.getItemViewType(position);
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void add(T viewModel) {
        itemList.add(viewModel);
        notifyDataSetChanged();
    }

    public void add(int position, T viewModel) {
        itemList.add(position, viewModel);
        notifyDataSetChanged();
    }

    public void set(List<T> viewModels) {
        itemList.clear();
        addAll(viewModels);
    }

    public void addAll(List<T> viewModels) {
        itemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void clearList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder<t extends ViewDataBinding> extends RecyclerView.ViewHolder {
        final t binding;

        ViewHolder(t binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        t getBinding() {
            return binding;
        }
    }

    public interface AdapterListener {
        void onItemClick(String value);

        boolean onItemLongClick(Object object);

        void onLoadMore();
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    private AdapterListener getAdapterListener() {
        return adapterListener;
    }
}
