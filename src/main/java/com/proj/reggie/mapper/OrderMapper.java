package com.proj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.proj.reggie.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
