package top.kass.pocketoa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.CustomerBean;

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<CustomerBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public CustomerAdapter(Context context) {
        this.mContext = context;
    }

    public void setmData(List<CustomerBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_customer, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {

            CustomerBean customer = mData.get(position);
            if(customer == null) {
                return;
            }
            ((ItemViewHolder) holder).mName.setText(customer.getName());
            ((ItemViewHolder) holder).mType.setText(getCustomerType(customer.getType()));
            ((ItemViewHolder) holder).mStatus.setText(getCustomerStatus(customer.getStatus()));
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public CustomerBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mType;
        public TextView mName;
        public TextView mStatus;

        public ItemViewHolder(View v) {
            super(v);
            mType = (TextView) v.findViewById(R.id.tvCustomerType);
            mName = (TextView) v.findViewById(R.id.tvCustomerName);
            mStatus = (TextView) v.findViewById(R.id.tvCustomerStatus);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                // TODO
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private String getCustomerType(int type) {
        switch (type) {
            case 1: return mContext.getString(R.string.customer_type_important);
            case 2: return mContext.getString(R.string.customer_type_normal);
            case 3: return mContext.getString(R.string.customer_type_low_value);
        }
        return "";
    }

    private String getCustomerStatus(int status) {
        switch (status) {
            case 1: return mContext.getString(R.string.customer_status_1);
            case 2: return mContext.getString(R.string.customer_status_2);
            case 3: return mContext.getString(R.string.customer_status_3);
            case 4: return mContext.getString(R.string.customer_status_4);
            case 5: return mContext.getString(R.string.customer_status_5);
        }
        return "";
    }


}
