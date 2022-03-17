package com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.App;
import com.xueyiche.zjyk.xueyiche.custom.RoundImageView;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author SoBan
 * @create 2017/5/19 15:33.
 */
public class ShareCardYiBiView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final int MSG_NEXT = 1;
    private Context mContext;
    private ViewPager mViewPager; //自定义的无限循环ViewPager
    private ViewGroup mViewGroup;
    private CardAdapter mAdapter;
    private int mFocusImageId;
    private int mUnfocusImageId;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private int centerPos; //中间卡片位置
    private int pageCount; //所有卡片的个数

    public ShareCardYiBiView(Context context) {
        this(context, null);
    }

    public ShareCardYiBiView(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View container = LayoutInflater.from(mContext).inflate(R.layout.layout_share_cardview, null);
        addView(container);
        mViewPager = (ViewPager) (container.findViewById(R.id.slide_viewPager));
        mViewGroup = (ViewGroup) (container.findViewById(R.id.slide_viewGroup));

        mFocusImageId = R.mipmap.card_point_ok;
        mUnfocusImageId = R.mipmap.card_point_no;

        mViewPager.setPageTransformer(false, new ScaleTransformer());//设置卡片切换动画
        mViewPager.setPageMargin(10);//卡片与卡片间的距离
        mViewPager.setOnPageChangeListener(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_NEXT:
                        next();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        startTimer();
                        break;
                }
                return false;
            }
        });
    }

    //设置数据
    public void setCardData(List<ShouYeBean.ContentBean.VolutionContentBean> cardItem) {
        pageCount = cardItem.size();
        centerPos = pageCount / 2;//中间卡片的位置
        mAdapter = new CardAdapter(cardItem);
        mViewPager.setAdapter(mAdapter);
        mAdapter.select(0);//默认从中间卡片开始
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(pageCount);//预加载所有卡片
        startTimer();
    }

    public ViewPager getView() {
        return mViewPager;
    }

    //启动动画
    public void startTimer() {
        stopTimer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_NEXT);
            }
        };
        mTimer = new Timer(true);
        mTimer.schedule(mTimerTask, 3000, 3000);
    }

    //停止动画
    public void stopTimer() {
        mHandler.removeMessages(MSG_NEXT);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    //选择下一个卡片
    public void next() {
        int pos = mViewPager.getCurrentItem();
        pos += 1;
        mViewPager.setCurrentItem(pos);
    }

    //判断是否显隐控件
    public void refresh() {
        if (getCount() <= 0) {
            this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.VISIBLE);
        }
    }

    public int getCount() {
        if (mAdapter != null) {
            return mAdapter.getCount();
        }
        return 0;
    }


    public class CardAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private ArrayList<ImageView> mPoints;
        private List<ShouYeBean.ContentBean.VolutionContentBean> iconLists = new ArrayList<>();

        public CardAdapter(List<ShouYeBean.ContentBean.VolutionContentBean> cardItem) {
            inflater = LayoutInflater.from(mContext);
            mPoints = new ArrayList<>();
            iconLists = cardItem;
            setItems();
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        private ImageView newPoint() {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 20;
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mUnfocusImageId);
            return imageView;
        }


        public void setItems() {
            while (mPoints.size() < pageCount) mPoints.add(newPoint());
            while (mPoints.size() > pageCount) mPoints.remove(0);
            mViewGroup.removeAllViews();
            for (ImageView view : mPoints) {
                mViewGroup.addView(view);
            }
            mViewPager.setCurrentItem(0);
            select(0);
        }

        public void select(int pos) {
            if (mPoints.size() > 0) {
                pos = pos % mPoints.size();
                for (int i = 0; i < mPoints.size(); i++) {
                    if (i == pos) {
                        mPoints.get(i).setBackgroundResource(mFocusImageId);
                    } else {
                        mPoints.get(i).setBackgroundResource(mUnfocusImageId);
                    }
                }
            }

            refresh();
        }

        //中间卡片
        public void setZSCard(View view, int lrCardItemPos) {
            RoundImageView hhh = (RoundImageView) view.findViewById(R.id.iv_lunbo);
            TextView tv_lunbo_title = (TextView) view.findViewById(R.id.tv_lunbo_title);
            tv_lunbo_title.getPaint().setFakeBoldText(true);
            TextView tv_lunbo_content = (TextView) view.findViewById(R.id.tv_lunbo_content);
            if (iconLists!=null) {
                if (lrCardItemPos < iconLists.size()) {
                    ShouYeBean.ContentBean.VolutionContentBean volutionContentBean = iconLists.get(lrCardItemPos);
                    if (volutionContentBean!=null) {
                        String address =volutionContentBean .getAddress();
                        String main_title = volutionContentBean.getMain_title();
                        String sub_title = volutionContentBean.getSub_title();
                        if (!TextUtils.isEmpty(address)) {
                            Picasso.with(App.context).load(address).placeholder(R.mipmap.lunbotu).error(R.mipmap.lunbotu).into(hhh);
                        }
                        if (!TextUtils.isEmpty(main_title)) {
                            tv_lunbo_title.setText(main_title);
                        }
                        if (!TextUtils.isEmpty(sub_title)) {
                            tv_lunbo_content.setText(sub_title);
                        }
                    }


                }

            }

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            View view = null;
            view = inflater.inflate(R.layout.layout_share_zscard, container, false);

            setZSCard(view, position);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onPageSelected(int position) {
        mAdapter.select(position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
