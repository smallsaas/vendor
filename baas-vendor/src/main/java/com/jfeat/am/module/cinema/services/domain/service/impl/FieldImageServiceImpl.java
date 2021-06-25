package com.jfeat.am.module.cinema.services.domain.service.impl;


import com.jfeat.am.module.cinema.services.domain.dao.DefaultImage;
import com.jfeat.am.module.cinema.services.domain.service.FieldImageService;

import com.jfeat.am.module.config.services.service.ConfigFieldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("FieldImageService")
public class FieldImageServiceImpl  implements FieldImageService {
    @Resource
    ConfigFieldService configFieldService;
    static final String DEFAULT_IMAGE="defaultImage";
    static final Long ORG_ID = 100000000000000010L;

    @Resource
    DefaultImage defaultImage;

    @Override
    public String getDefaultImageUrl(){
        String fieldString = configFieldService.getFieldString(DEFAULT_IMAGE);
        if(fieldString == null){
            fieldString = defaultImage.getDefaultUrl(DEFAULT_IMAGE, ORG_ID);
        }
        return fieldString;
    }

}
