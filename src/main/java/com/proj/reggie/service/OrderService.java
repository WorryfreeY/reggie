package com.proj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proj.reggie.pojo.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
