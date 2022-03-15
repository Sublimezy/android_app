package com.xueyiche.zjyk.xueyiche.homepage.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;

import java.util.List;

/**
 * Created by Owner on 2016/11/28.
 */
public   class POIAdapter extends BaseAdapter {
    private List<PoiInfo> allPoi;

    public POIAdapter(List<PoiInfo> allPoi) {
        this.allPoi = allPoi;
    }

    @Override
    public int getCount() {
        return allPoi.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(App.context, R.layout.poi_list_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        PoiInfo poiInfo = allPoi.get(position);
        holder.tv_name.setText(poiInfo.name);
        holder.tv_address.setText(poiInfo.address);
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_address;
        TextView tv_name;
    }
}
