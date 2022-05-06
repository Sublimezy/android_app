package com.xueyiche.zjyk.xueyiche.community.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ctetin.expandabletextviewlibrary.app.StatusType;
import com.ctetin.expandabletextviewlibrary.model.ExpandableStatusFix;

/**
 * @Package: com.xueyiche.zjyk.xueyiche.community
 * @ClassName: CommunityListBean
 * @Description: java类作用描述
 * @Author: 松鼠爱吃肉
 * @CreateDate: 2022/4/20 13:23
 */
public class CommunityListBean implements MultiItemEntity, ExpandableStatusFix {
    private StatusType status;

    public static final int TEXT = 1;
    public static final int TEXT_ONE_PIC = 2;
    public static final int TEXT_TWO_PIC = 3;
    public static final int TEXT_VIDEO = 4;
    public static final int TEXT_PICS = 5;


    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public void setStatus(StatusType status) {
        this.status = status;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }
}