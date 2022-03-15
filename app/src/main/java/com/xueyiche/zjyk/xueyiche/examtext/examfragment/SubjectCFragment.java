package com.xueyiche.zjyk.xueyiche.examtext.examfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.base.module.BaseFragment;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.constants.AppUrl;
import com.xueyiche.zjyk.xueyiche.driverschool.bean.KeMuSanBean;
import com.xueyiche.zjyk.xueyiche.examtext.CommonWebView;
import com.xueyiche.zjyk.xueyiche.examtext.kemuc.KeMuSanVideo;
import com.xueyiche.zjyk.xueyiche.utils.ACache;
import com.xueyiche.zjyk.xueyiche.utils.JsonUtil;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;
import com.xueyiche.zjyk.xueyiche.utils.XueYiCheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.List;

/**
 * Created by Owner on 2016/9/23.
 */
public class SubjectCFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recycler_view;
    private GridLayoutManager mLayoutManage;
    private MyAdapter myAdapter;
    private static List<KeMuSanBean.ContentBean> content;
    private TextView tv_kesan_xuzhi;
    private TextView tv_must_pass;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected View setInitView() {
        View view = LayoutInflater.from(App.context).inflate(R.layout.home_exam_subjectc, null);


        tv_must_pass = (TextView) view.findViewById(R.id.tv_must_pass);
        tv_kesan_xuzhi = (TextView) view.findViewById(R.id.tv_kesan_xuzhi);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        tv_must_pass.setOnClickListener(this);
        mLayoutManage = new GridLayoutManager(getContext(), 2);
        mLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(mLayoutManage);
        myAdapter = new MyAdapter();
        tv_kesan_xuzhi.setOnClickListener(this);
        initData();
        return view;
    }

    private void initData() {
        String asString = ACache.get(getContext()).getAsString("KEMUSAN");
        if (!TextUtils.isEmpty(asString)) {
            processData(asString);
        }
        if (XueYiCheUtils.IsHaveInternet(getContext())) {
            String user_phone = PrefUtils.getString(App.context, "user_phone", "");
            showProgressDialog(getActivity(),false);
            OkHttpUtils.post().url(AppUrl.KEMUSAN).addParams("user_phone", user_phone).
                    build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(Response response) throws IOException {
                    String string = response.body().string();
                    if (!TextUtils.isEmpty(string)) {
                        ACache.get(getContext()).put("KEMUSAN", string);
                        processData(string);

                    }
                    return string;
                }

                @Override
                public void onError(Request request, Exception e) {
                    stopProgressDialog();
                }

                @Override
                public void onResponse(Object response) {
                    stopProgressDialog();
                }
            });

        }
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, final int position) {
                if (isWifi(getContext())) {
                    playVieo(position);
                }else {
                    //显示拨打电话的dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setIcon(R.mipmap.logo);
                    builder.setTitle("提示");
                    builder.setMessage("当前处于非WiFi状态是否继续播放");
                    //点击空白处弹框不消失
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            playVieo(position);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                    builder.show();
                }


            }
        });


    }

    private void playVieo(int position) {
        KeMuSanBean.ContentBean contentBean = content.get(position);
        String subjects_name = contentBean.getSubjects_name();
        String video_url = contentBean.getVideo_url();
        Intent intent = new Intent(App.context, KeMuSanVideo.class);
        if (!TextUtils.isEmpty(subjects_name)) {
            intent.putExtra("subjects_name", subjects_name);
        }
        if (!TextUtils.isEmpty(video_url)) {
            intent.putExtra("video_url", video_url);
        }
        startActivity(intent);
    }

    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
    private void processData(String string) {
        final KeMuSanBean dingDan = JsonUtil.parseJsonToBean(string, KeMuSanBean.class);
        content = dingDan.getContent();

        App.handler.post(new Runnable() {
            @Override
            public void run() {

                if (content != null && content.size() != 0) {
                    recycler_view.setAdapter(myAdapter);
                }


            }
        });
    }


    @Override
    protected Object setLoadDate() {
        return "xyc";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_must_pass:
                Intent intent1 = new Intent(App.context, CommonWebView.class);
                intent1.putExtra("weburl", "biguo");
                startActivity(intent1);
                break;
            case R.id.tv_kesan_xuzhi:
                Intent intent2 = new Intent(App.context, CommonWebView.class);
                intent2.putExtra("weburl", "three_xuzhi");
                startActivity(intent2);
                break;
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public interface OnItemClickListener {
            void onItemClickListener(View view, int position);
        }

        private OnItemClickListener listener;


        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_kemusan;
            TextView tv_kemusan;

            public ViewHolder(View v) {
                super(v);
            }

        }

        @Override
        public int getItemCount() {
            return content.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kemusan_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.iv_kemusan = (ImageView) v.findViewById(R.id.iv_kemusan);
            holder.tv_kemusan = (TextView) v.findViewById(R.id.tv_kemusan);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String subjects_name = content.get(position).getSubjects_name();
            String img_url = content.get(position).getImg_url();

            if (!TextUtils.isEmpty(subjects_name)) {
                holder.tv_kemusan.setText(subjects_name);
            }
            if (!TextUtils.isEmpty(img_url)) {
                Picasso.with(App.context).load(img_url). placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into( holder.iv_kemusan);
            }
            if (listener != null) {
                holder.iv_kemusan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onItemClickListener(v, position);
                    }
                });
            }
        }
    }


}
