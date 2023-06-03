package com.proj.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proj.reggie.mapper.DishFlavorMapper;
import com.proj.reggie.pojo.DishFlavor;
import com.proj.reggie.service.DishFlavorService;
import com.proj.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
