package com.xueyiche.zjyk.xueyiche.daijia.addressdialog;



public interface OnAddressSelectedListener {
    // 获取地址完成回调
    void onAddressSelected(Province province, City city, County county, Street street);
    // 选取省份完成回调
    void onProvinceSelected(Province province);
    // 选取城市完成回调
    void onCitySelected(City city);
    // 选取区/县完成回调
    void onCountySelected(County county);
}
