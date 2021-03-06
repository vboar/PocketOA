package top.kass.pocketoa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import top.kass.pocketoa.R;
import top.kass.pocketoa.bean.OpportunityBean;

public class OpportunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<OpportunityBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public OpportunityAdapter(Context context) {
        this.mContext = context;
    }

    public void setmData(List<OpportunityBean> data) {
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
                    .inflate(R.layout.item_opportunity, parent, false);
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

            OpportunityBean opportunity = mData.get(position);
            if(opportunity == null) {
                return;
            }
            ((ItemViewHolder) holder).mName.setText(opportunity.getOpportunityTitle());
            ((ItemViewHolder) holder).mType.setText(OpportunityBean.getTypeString(opportunity.getBusinessType()));
            ((ItemViewHolder) holder).mStatus.setText(OpportunityBean.getStatusString(opportunity.getOpportunityStatus()));
            if (opportunity.getEstimatedAmount() != null) {
                ((ItemViewHolder) holder).mAmount.setText(opportunity.getEstimatedAmount().toString() + "元");
            }
            ((ItemViewHolder) holder).mCustomer.setText("客户 [" + opportunity.getCustomer().getCustomerName() +"]");
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

    public OpportunityBean getItem(int position) {
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

        public TextView mName;
        public TextView mType;
        public TextView mStatus;
        public TextView mAmount;
        public TextView mCustomer;

        public ItemViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.tvName);
            mType = (TextView) v.findViewById(R.id.tvType);
            mStatus = (TextView) v.findViewById(R.id.tvStatus);
            mAmount = (TextView) v.findViewById(R.id.tvAmount);
            mCustomer = (TextView) v.findViewById(R.id.tvCustomer);
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
