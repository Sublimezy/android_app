package com.xueyiche.zjyk.jiakao.base.module;


import me.jessyan.autosize.internal.CancelAdapt;


/**
 * Activity的基类
 */
public abstract class BaseActivity extends NewBaseActivity {
   /* protected View view;
    private BaseProgressDialog mProgressDialog = null;
    *//**
     * 网络类型
     *//*


    private int netMobile;
    private InputMethodManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        StatusBarUtil.setLightMode(this);
        view = View.inflate(getBaseContext(), initContentView(), null);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(view);
        initView();
        initListener();
        initData();
//        // 添加Activity到堆栈
        AppUtils.getAppManager().addActivity(this);
        //透明状态栏
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BaseActivity"); // [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
        MobclickAgent.onResume(this);// 友盟统计，所有Activity中添加，父类添加后子类不用重复添加
        //设置不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //点击空白处软件盘消失
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppUtils.getAppManager().finishActivity(this);
    }
    @Override
    public Resources getResources() {
        //禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BaseActivity"); // [(仅有Activity的应用中SDK自动调用,不需要单独写)保证onPageEnd在onPause之前调用,因为onPause中会保存信息。参数页面名称,可自定义]
        MobclickAgent.onPause(this);// 友盟统计，所有Activity中添加，父类添加后子类不用重复添加
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    *//**
     * 初始化contentView
     *
     * @return 返回contentView的layout id
     *//*
    protected abstract int initContentView();

    *//**
     * 初始化View，执行findViewById操作
     *//*
    protected abstract void initView();

    *//**
     * 初始化监听器
     *//*
    protected abstract void initListener();

    *//**
     * 初始化数据
     *//*
    protected abstract void initData();

    @TargetApi(19)
    private void initWindow() {

    }

    //短Toast
    protected void showToastShort(String Msg) {
        Toast.makeText(App.context, Msg, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(BaseProgressDialog.OnCancelListener cancelListener, boolean cancelable, String msg) {
        Log.e("zhanglei_activity", "This is " + getClass().getSimpleName());
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new BaseProgressDialog(this, msg);
        if (cancelListener != null) {
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(boolean cancelable, String msg) {
        showProgressDialog(null, cancelable, msg);
    }

    public void showProgressDialog(boolean cancelable) {
        showProgressDialog(cancelable, "");
    }

    public void stopProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.stop();
        }
        mProgressDialog = null;
    }

    //长Toast
    protected void showToastLong(String Msg) {
        Toast.makeText(App.context, Msg, Toast.LENGTH_LONG).show();
    }

    *//**
     * 界面跳转
     *
     * @param clazz 目标Activity
     *//*
    protected void openActivity(Class<?> clazz) {
        readyGo(clazz, null);
    }

    *//**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     *//*
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(App.context, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    *//**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     *//*
    protected void openActivityThenKill(Class<?> clazz) {
        openActivityThenKill(clazz, null);
    }

    *//**
     * @param clazz  目标Activity
     * @param bundle 数据
     *//*
    protected void openActivityThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        finish();
    }

    //在Fragment里的软件盘消失
    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<BaseActivity.MyTouchListener>();

    *//**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     *//*
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    *//**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     *//*
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }
*/

}
