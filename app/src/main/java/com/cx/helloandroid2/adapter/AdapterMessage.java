package com.cx.helloandroid2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.model.ModelMessage;

import java.util.List;

/**
 * Created by cx on 2017/9/29.
 */

public class AdapterMessage extends BaseAdapter{

    private Context mContext;
    private List<ModelMessage> adapterMessages;

    public AdapterMessage(Context mContext,List<ModelMessage> adapterMessages){
        this.mContext=mContext;
        this.adapterMessages=adapterMessages;
    }
    @Override
    public int getCount() {
        return adapterMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return adapterMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_message,null);
            holder.tvName=convertView.findViewById(R.id.icx_message_adapter_name);
            holder.tvMessage=  convertView.findViewById(R.id.icx_message_adapter_message);
            holder.icon= convertView.findViewById(R.id.ivIcon);
            holder.tvDate=convertView.findViewById(R.id.icx_message_date);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(adapterMessages.get(position).name);
        holder.tvMessage.setText(adapterMessages.get(position).message);
        holder.tvDate.setText(adapterMessages.get(position).date);
        holder.icon.setImageBitmap(adapterMessages.get(position).icon);
        return convertView;
    }


    class ViewHolder{
        ImageView icon;
        TextView tvName;
        TextView tvMessage;
        TextView tvDate;
    }
}
