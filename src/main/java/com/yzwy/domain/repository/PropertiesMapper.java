package com.yzwy.domain.repository;

import com.yzwy.domain.model.Properties;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by gin on 2017/6/1.
 */
@Mapper
public interface PropertiesMapper {

    List<Properties> queryProperty(String code);
}
