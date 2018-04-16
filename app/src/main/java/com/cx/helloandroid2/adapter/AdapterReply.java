package com.cx.helloandroid2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.model.ModelTalk;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class AdapterReply extends BaseAdapter{

    private Context myContext;
    private List<ModelTalk> data;

    public AdapterReply(Context myContext, List<ModelTalk> data) {
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
            convertView = View.inflate(myContext, R.layout.adapter_reply, null);
            holder = new ViewHolder();
            holder.tvFeedBack = (TextView)convertView.findViewById(R.id.tvFeedBack);
            holder.rlFeedBack = (RelativeLayout)convertView.findViewById(R.id.rlFeedBack);
            holder.ivSystemHead = (ImageView)convertView.findViewById(R.id.ivSystemHead);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ModelTalk info = data.get(position);

//        if(EmptyUtil.isEmptyOrNull(info.fbReply)){ //系统回复的消息
//            holder.rlFeedBack.setVisibility(View.GONE);
//        }else{
//            holder.tvFeedBack.setText(info.fbReply);
//            holder.rlFeedBack.setVisibility(View.VISIBLE);
//            //系统回复
//            holder.ivSystemHead.setImageResource(R.mipmap.ic_launcher_round);//系统的头像
//        }
        return convertView;
    }

    class ViewHolder{
        public TextView tvFeedBack;
        public RelativeLayout rlFeedBack;
        public ImageView ivSystemHead;
    }
}


