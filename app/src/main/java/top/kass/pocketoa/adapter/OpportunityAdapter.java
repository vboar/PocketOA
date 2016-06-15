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
            ((ItemViewHolder) holder).mType.setText(getOpportunityType(opportunity.getBusinessType()));
            ((ItemViewHolder) holder).mStatus.setText(getOpportunityStatus(opportunity.getOpportunityStatus()));
            ((ItemViewHolder) holder).mAmount.setText(Double.toString(opportunity.getEstimatedAmount()));
            ((ItemViewHolder) holder).mCustomer.setText(opportunity.getCustomerId().toString());
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

    private String getOpportunityType(int type) {
        switch (type) {
            case 1: return mContext.getString(R.string.opportunity_type_important);
            case 2: return mContext.getString(R.string.opportunity_type_normal);
        }
        return "";
    }

    private String getOpportunityStatus(int status) {
        switch (status) {
            case 1: return mContext.getString(R.string.opportunity_status_1);
            case 2: return mContext.getString(R.string.opportunity_status_2);
            case 3: return mContext.getString(R.string.opportunity_status_3);
            case 4: return mContext.getString(R.string.opportunity_status_4);
            case 5: return mContext.getString(R.string.opportunity_status_5);
            case 6: return mContext.getString(R.string.opportunity_status_6);
        }
        return "";
    }

}
