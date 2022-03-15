package com.xueyiche.zjyk.xueyiche.homepage.fragments.bannerview;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xueyiche.zjyk.xueyiche.R;
import com.xueyiche.zjyk.xueyiche.constants.bean.LunBoShouYeBean;
import com.xueyiche.zjyk.xueyiche.homepage.bean.ShouYeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SoBan
 * @create 2017/5/19 15:33.
 */
public class ShareCardViewQuestion extends FrameLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private androidx.viewpager.widget.ViewPager mViewPager;
    private CardAdapter mAdapter;
    private int pageCount; //所有卡片的个数

    public ShareCardViewQuestion(Context context) {
        this(context, null);
    }

    public ShareCardViewQuestion(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View container = LayoutInflater.from(mContext).inflate(R.layout.layout_share_cardview_question, null);
        addView(container);
        mViewPager = (androidx.viewpager.widget.ViewPager) (container.findViewById(R.id.slide_viewPager));
        mViewPager.setPageTransformer(false, new ScaleTransformer());//设置卡片切换动画
        mViewPager.setPageMargin(10);//卡片与卡片间的距离
        mViewPager.setOnPageChangeListener(this);
    }

    //设置数据
    public void setCardData(List<ShouYeBean.ContentBean.QuestionsContentBean> cardItem) {
        pageCount = cardItem.size();
        mAdapter = new CardAdapter(cardItem);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(pageCount);//预加载所有卡片
    }

    public androidx.viewpager.widget.ViewPager getView() {
        return mViewPager;
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
        private LunBoShouYeBean zsCardItem = new LunBoShouYeBean();
        private List<ShouYeBean.ContentBean.QuestionsContentBean> iconLists = new ArrayList<>();

        public CardAdapter(List<ShouYeBean.ContentBean.QuestionsContentBean> cardItem) {
            inflater = LayoutInflater.from(mContext);
            iconLists = cardItem;
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        //中间卡片
        public void setZSCard(View view, LunBoShouYeBean item, int lrCardItemPos) {
            TextView viewpager_question_title = (TextView) view.findViewById(R.id.viewpager_question_title);
            TextView viewpager_question_content = (TextView) view.findViewById(R.id.viewpager_question_content);
            TextView tv_viewpager_pinglun_number = (TextView) view.findViewById(R.id.tv_viewpager_pinglun_number);
            TextView tv_viewpager_dianzan_number = (TextView) view.findViewById(R.id.tv_viewpager_dianzan_number);
            if (lrCardItemPos < pageCount) {
                if (iconLists != null) {
                    ShouYeBean.ContentBean.QuestionsContentBean questionsContentBean = iconLists.get(lrCardItemPos);
                    if (questionsContentBean != null) {
                        int comment_count = questionsContentBean.getComment_count();
                        int praise_count = questionsContentBean.getPraise_count();
                        String title = questionsContentBean.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            viewpager_question_title.setText(title);
                        }
                        if (!TextUtils.isEmpty(""+praise_count)) {
                            tv_viewpager_dianzan_number.setText(""+praise_count);
                        }
                        if (!TextUtils.isEmpty(""+comment_count)) {
                            tv_viewpager_pinglun_number.setText(""+comment_count);
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
            view = inflater.inflate(R.layout.layout_share_zscard_question, container, false);

            setZSCard(view, zsCardItem, position);

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
