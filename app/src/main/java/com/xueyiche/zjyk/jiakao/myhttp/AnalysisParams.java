package com.xueyiche.zjyk.jiakao.myhttp;

import com.lzy.okgo.adapter.AdapterParam;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.model.Response;

import io.reactivex.Observable;


public class AnalysisParams {
    static <T> Observable<Response<T>> analysis(Call<T> call, AdapterParam param) {
        Observable<Response<T>> observable;
        if (param == null) param = new AdapterParam();
        if (param.isAsync) {
            observable = new CallEnqueueObservable<>(call);
        } else {
            observable = new CallExecuteObservable<>(call);
        }
        return observable;
    }
}
