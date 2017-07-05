package com.yzwy.domain.repository;

import com.yzwy.domain.model.Msg_templet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by gin on 2017/6/1.
 */
@Mapper
public interface Msg_templetMapper {

    List<Msg_templet> queryMsgTemplet(Integer id);
    List<Msg_templet> queryAllMsgTemplet();
    void updateTempletByCalcu(Msg_templet templet);
}
