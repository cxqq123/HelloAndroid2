package com.cx.helloandroid2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.helloandroid2.R;

import java.util.List;


/**
 * Created by qk on 2017/3/2.
 */

public class AdapterPagePop extends BaseAdapter {
    private Context mContext;
    private List<String> data;
    private int[] imgs={R.drawable.icx_setting_a , R.drawable.icx_scancode_a};

    public AdapterPagePop(Context mContext, List<String> mData) {
        this.mContext = mContext;
        data = mData;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_page_pop, null);
            holder.tvContent = (TextView)convertView.findViewById(R.id.tvPagePop);
            holder.ivContent= (ImageView) convertView.findViewById(R.id.ivPagePop);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(data.get(position));
        holder.ivContent.setImageResource(imgs[position]);
        return convertView;
    }

    class ViewHolder{
        TextView tvContent;
        ImageView ivContent;
    }
}
