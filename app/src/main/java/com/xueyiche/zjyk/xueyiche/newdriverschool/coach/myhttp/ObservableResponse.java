package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp;

import com.lzy.okgo.adapter.AdapterParam;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.adapter.CallAdapter;
import com.lzy.okgo.model.Response;

import io.reactivex.Observable;

/**
 * @Package: com.example.yjf.tata.utils.net
 * @ClassName: ObservableResponse
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2020/7/20 18:19
 */
public class ObservableResponse<T> implements CallAdapter<T, Observable<Response<T>>> {
    @Override
    public Observable<Response<T>> adapt(Call<T> call, AdapterParam param) {
        return AnalysisParams.analysis(call, param);
    }
}
