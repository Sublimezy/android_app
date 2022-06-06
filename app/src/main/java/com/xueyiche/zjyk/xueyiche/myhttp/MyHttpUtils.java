package com.xueyiche.zjyk.xueyiche.myhttp;

import android.os.UserManager;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.xueyiche.zjyk.xueyiche.utils.PrefUtils;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Package: com.keji.zsj.virtual_country.utils.httputils
 * @ClassName: HttpUtils
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2020/12/1 12:42
 */
public class MyHttpUtils {
    /**
     * post网络请求
     *
     * @param url      地址
     * @param params   参数
     * @param clazz    实体类
     * @param listener 回调
     * @param <T>
     */
    public static <T> void postHttpMessage(final String url, Map<String, String> params, final Class<T> clazz, final RequestCallBack<T> listener) {
        Log.e("postmap____________", url + "~~~~~" + params.toString() + "");

        OkGo.<String>post(url)//
                .tag(url)
                .headers("Content-Type", "application/json")
                .headers("token", PrefUtils.getParameter("token"))
                .params(params)
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
//                        progressView.show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        // addDisposable(d);

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Response<String> response) {
                        Log.e("返回成功参数"+url, response.body());
                        Log.e("返回成功参数token", PrefUtils.getParameter("token"));
                        String json = response.body();

                        try {
                            if (listener != null) {
                                T t = JsonUtils.jsonToBean(json, clazz);
                                if (t != null) {
                                    listener.requestSuccess(t);
                                } else {
                                    listener.requestError("请求失败,无数据", -1);
                                }

                            }
                        } catch (Exception e) {
                            Log.e("返回失败参数_异常了", e.toString());
                            listener.requestError(e.toString(), -1);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("返回失败参数"+url, e.toString());
                        if (listener != null) {
                            listener.requestError(e.toString(), -1);
                        }

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * @param url
     * @param clazz
     * @param listener
     * @param <T>
     */
    public static <T> void getHttpMessage(final String url, final Class<T> clazz, final RequestCallBack<T> listener) {
        getHttpMessage(url, "application/json", clazz, listener);
    }

    public static <N> void getHttpMessage(final String url, String header, final Class<N> bean, final RequestCallBack<N> listener) {
        Log.e("get-------", url + "");

        OkGo.<String>get(url)
                .tag(url)
                .converter(new StringConvert())
                .headers("Content-Type", header)
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
//                        progressView.show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        // addDisposable(d);

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Response<String> response) {
                        Log.e("返回成功参数", url+":"+response.body());
                        String json = response.body();

                        try {
                            if (listener != null) {
                                N t = JsonUtils.jsonToBean(json, bean);
                                if (t != null) {
                                    listener.requestSuccess(t);
                                } else {
                                    listener.requestError("请求失败,无数据", -1);
                                }
                            }
                        } catch (Exception e) {
                            listener.requestError(e.toString(), -1);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("返回失败参数", e.toString());
                        if (listener != null) {
                            listener.requestError(e.toString(), -1);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    /**
     * 处理缓存的post网络请求
     *
     * @param cachekey  缓存key
     * @param cachemode 缓存模式
     * @param url       地址
     * @param params    参数
     * @param clazz     实体类
     * @param listener  回调
     * @param <T>
     */
    public static <T> void postHttpMessage(String cachekey, CacheMode cachemode, final String url, Map<String, String> params, final Class<T> clazz, final RequestCallBack<T> listener) {
        Log.e("postmap____________", url + "~~~~~" + params.toString() + "");

        OkGo.<String>post(url)//
//                .headers("aaa", "111")//
//                .params("rsa_str", rsa_str)
                .tag(url)
                .params(params)
                .cacheKey(cachekey)
                .cacheMode(cachemode)
                .headers("Content-Type", "application/json")
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
//                        progressView.show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        // addDisposable(d);

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Response<String> response) {
                        Log.e("返回成功参数", response.body());
                        String json = response.body();
                        Log.e("返回成功参数_是否使用了缓存", response.isFromCache()+"111111");


                        try {
                            if (listener != null) {
                                T t = JsonUtils.jsonToBean(json, clazz);
                                if (t != null) {
                                    listener.requestSuccess(t);
                                } else {
                                    listener.requestError("请求失败,无数据", -1);
                                }
                            }
                        } catch (Exception e) {
                            listener.requestError(e.toString(), -1);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("返回失败参数", e.toString());
                        if (listener != null) {
                            listener.requestError(e.toString(), -1);
                        }

                    }


                    @Override
                    public void onComplete() {

                    }
                });

    }
//    public static void upLoadPic(Context context, List<LocalMedia> selectList, RequestCallBack<TuPianBean> listener){
//        List<File> list = new ArrayList<>();
//
//        for (int i = 0; i < selectList.size(); i++) {
//            Log.e("httputils", "upLoadPic: selectList.get(i).getCompressPath()="+selectList.get(i).getCompressPath() );
//            list.add(new File(selectList.get(i).getCompressPath()));
//        }
//        OkGo.<String>post(AppUrl.TU_UPLOAD)
//                .tag(context)
//                .addFileParams("file",list)
//                .isMultipart(true)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        LogUtils.e("上传成功", response.body());
//                        TuPianBean tuPianBean = JsonUtils.jsonToBean(response.body(), TuPianBean.class);
//                        listener.requestSuccess(tuPianBean);
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        LogUtils.e("上传失败",response.body());
//                        listener.requestError(response.body(), -1);
//                    }
//                });
//    }

}
