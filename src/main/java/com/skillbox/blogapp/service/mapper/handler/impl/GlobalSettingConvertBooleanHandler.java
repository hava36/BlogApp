package com.skillbox.blogapp.service.mapper.handler.impl;

import com.skillbox.blogapp.service.mapper.handler.GlobalSettingConvertHandler;

public class GlobalSettingConvertBooleanHandler implements GlobalSettingConvertHandler<Boolean, String> {

    @Override
    public Boolean parse(String value) {
        return value.equals("YES");
    }

}
