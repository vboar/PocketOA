package top.kass.pocketoa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.CustomerBean;
import top.kass.pocketoa.util.UrlUtil;

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
            ((ItemViewHolder) holder).mName.setText(customer.getCustomerName());
            ((ItemViewHolder) holder).mType.setText(CustomerBean.getTypeString(customer.getCustomerType()));
            ((ItemViewHolder) holder).mStatus.setText(CustomerBean.getStatusString(customer.getCustomerStatus()));
            Glide.with(mContext).load(UrlUtil.COMMON_PIC_URL).crossFade()
                    .error(R.drawable.icon_default)
                    .into(((ItemViewHolder) holder).mIvPic);
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
        public CircleImageView mIvPic;

        public ItemViewHolder(View v) {
            super(v);
            mType = (TextView) v.findViewById(R.id.tvCustomerType);
            mName = (TextView) v.findViewById(R.id.tvCustomerName);
            mStatus = (TextView) v.findViewById(R.id.tvCustomerStatus);
            mIvPic = (CircleImageView) v.findViewById(R.id.ivPicture);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
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


}
