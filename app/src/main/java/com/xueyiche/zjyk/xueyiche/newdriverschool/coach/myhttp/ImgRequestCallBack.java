package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp;

import android.graphics.drawable.Drawable;

public interface ImgRequestCallBack{

	/**
	 * 成功
	 *
	 */
    void requestSuccess(Drawable drawable);

    void requestLoading();

    void requestError();

}
