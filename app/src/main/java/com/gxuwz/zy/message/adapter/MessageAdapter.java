package com.gxuwz.zy.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gxuwz.zy.R;
import com.gxuwz.zy.message.entity.bean.MessageBean;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<MessageBean> {

    private final Context mContext;
    private final List<MessageBean> mNotificationList;
    private int mSelectedItem = -1;

    public MessageAdapter(Context context, List<MessageBean> notificationList) {
        super(context, 0, notificationList);
        this.mContext = context;
        this.mNotificationList = notificationList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.messageImage = convertView.findViewById(R.id.messageImage);
            viewHolder.messageTextTitle = convertView.findViewById(R.id.messageTextTitle);
            viewHolder.messageTextDescribe = convertView.findViewById(R.id.messageTextDescribe);
            viewHolder.messageTextTime = convertView.findViewById(R.id.messageTextTime);
            viewHolder.messageText = convertView.findViewById(R.id.messageText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MessageBean currentItem = mNotificationList.get(position);
        if (!currentItem.getMessageImage().isEmpty()) {
            Picasso.with(getContext()).load(currentItem.getMessageImage()).into(viewHolder.messageImage);
        }
        viewHolder.messageTextTitle.setText(currentItem.getMessageTextTitle());
        viewHolder.messageTextDescribe.setText(currentItem.getMessageTextDescribe());
        viewHolder.messageTextTime.setText(currentItem.getMessageTextTime());
        viewHolder.messageText.setText(currentItem.getMessageText());


        convertView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));


        if (position == mSelectedItem) {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        }

        // 设置背景为选择器
        convertView.setBackgroundResource(R.drawable.selector);

        return convertView;
    }

    public void setSelectedItem(int position) {
        mSelectedItem = position;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView messageImage;
        TextView messageTextTitle;
        TextView messageTextDescribe;
        TextView messageTextTime;
        TextView messageText;

    }
}
