package com.xueyiche.zjyk.xueyiche.myhttp;

public interface RequestCallBack<T> extends RequestBase {

	/**
	 * 成功
	 *
	 * @param json
	 */
    void requestSuccess(T json);
}
