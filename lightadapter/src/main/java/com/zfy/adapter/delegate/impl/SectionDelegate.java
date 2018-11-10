package com.zfy.adapter.delegate.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zfy.adapter.LightHolder;
import com.zfy.adapter.assistant.decoration.PinItemDecoration;
import com.zfy.adapter.common.LightUtils;
import com.zfy.adapter.common.LightValues;
import com.zfy.adapter.delegate.refs.SectionRef;
import com.zfy.adapter.listener.BindCallback;
import com.zfy.adapter.model.ModelType;
import com.zfy.adapter.model.SingleTypeConfigCallback;

/**
 * CreateAt : 2018/10/30
 * Describe : 底部加载更多功能
 *
 * @author chendong
 */
public class SectionDelegate<D> extends BaseDelegate implements SectionRef<D> {

    private BindCallback<D> mBindCallback; // section 绑定
    private boolean mPinEnable; // 是否支持悬停
    private PinItemDecoration mPinItemDecoration;

    @Override
    public int getKey() {
        return SECTION;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mPinEnable && mPinItemDecoration == null) {
            mPinItemDecoration = new PinItemDecoration();
            recyclerView.addItemDecoration(mPinItemDecoration);
        }
    }


    @Override
    public LightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LightValues.TYPE_SECTION) {
            ModelType type = mAdapter.getModelType(viewType);
            View view = LightUtils.inflateView(parent.getContext(), parent, type.layoutId);
            return new LightHolder(mAdapter, viewType, view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public boolean onBindViewHolder(LightHolder holder, int position) {
        if (mBindCallback != null && mAdapter.getItemViewType(position) == LightValues.TYPE_SECTION) {
            int pos = mAdapter.toModelIndex(position);
            D data = (D) mAdapter.getItem(pos);
            if (data != null) {
                mBindCallback.bind(holder, pos, data);
            }
            return true;
        }
        return super.onBindViewHolder(holder, position);
    }


    @Override
    public void setPinEnable(boolean pinEnable) {
        if (isAttached() && mPinItemDecoration == null && pinEnable) {
            mPinItemDecoration = new PinItemDecoration();
            mView.addItemDecoration(mPinItemDecoration);
        }
        mPinEnable = pinEnable;
    }

    @Override
    public void setOptions(SingleTypeConfigCallback configCallback, BindCallback<D> bindCallback) {
        mAdapter.addModelUpdater(configCallback.setSingleType(LightValues.TYPE_SECTION));
        mBindCallback = bindCallback;
        setPinEnable(mAdapter.getModelType(LightValues.TYPE_SECTION).supportPin);
    }

    @Override
    public void setOptions(int layoutId, boolean supportPin, BindCallback<D> callback) {
        mAdapter.addModelUpdater(new SingleTypeConfigCallback(data -> {
            data.layoutId = layoutId;
            data.supportPin = supportPin;
            data.spanSize = LightValues.SPAN_SIZE_ALL;
        }).setSingleType(LightValues.TYPE_SECTION));
        setPinEnable(supportPin);
        mBindCallback = callback;
    }

}