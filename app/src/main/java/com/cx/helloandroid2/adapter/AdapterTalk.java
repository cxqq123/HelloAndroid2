package com.cx.helloandroid2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.model.ModelTalk;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class AdapterTalk extends BaseAdapter{
    //发送消息Adapter

    private Context myContext;
    private List<ModelTalk> data;

    public AdapterTalk(Context myContext, List<ModelTalk> data) {
        super();
        this.myContext = myContext;
        this.data = data;
    }

    public void setData(List<ModelTalk> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView = View.inflate(myContext, R.layout.adapter_talk, null);
            holder = new ViewHolder();
            holder.tvMyFeed = (TextView)convertView.findViewById(R.id.tvMyFeed);
            holder.ivMyHead = (ImageView) convertView.findViewById(R.id.ivMyHead);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ModelTalk info = data.get(position);
        holder.tvMyFeed.setText(info.fbContent); //我发的消息
        holder.ivMyHead.setImageResource(R.drawable.icx_user_icon); //我的头像
        return convertView;
    }

    class ViewHolder{
        public TextView tvMyFeed;
        public ImageView ivMyHead;
    }
}
