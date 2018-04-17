package com.cx.helloandroid2.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.model.ModelChatMsg;
import com.cx.helloandroid2.util.ImageManager;

import java.util.List;

public class AdapterChatMsg extends ArrayAdapter<ModelChatMsg> {

    private LayoutInflater inflater;
    private List<ModelChatMsg> modelChatMsgs;
    public AdapterChatMsg(@NonNull Context context, @LayoutRes int resource, List<ModelChatMsg> modelChatMsgs) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.modelChatMsgs = modelChatMsgs;
    }

    public void setData(List<ModelChatMsg> data){
        this.modelChatMsgs = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return modelChatMsgs.size();
    }

    @Nullable
    @Override
    public ModelChatMsg getItem(int position) {
        return modelChatMsgs.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelChatMsg msg = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            assert msg != null;
            if (!msg.isMyInfo()) {
                view = inflater.inflate(R.layout.adapter_chat_other, parent, false);
            } else {
                view = inflater.inflate(R.layout.adapter_chat_me, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.content = (TextView) view.findViewById(R.id.content);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.icon.setImageResource(ImageManager.imagesAvatar[modelChatMsgs.get(position).getIconID()]);
        viewHolder.username.setText(msg.isMyInfo() ? modelChatMsgs.get(position).getUsername() : modelChatMsgs.get(position).getChatObj());
        viewHolder.content.setText(modelChatMsgs.get(position).getContent());
        return view;
    }

    private class ViewHolder {
        ImageView icon;
        TextView username;
        TextView content;
    }
}
