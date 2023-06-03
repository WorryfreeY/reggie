package com.proj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proj.reggie.dto.DishDto;
import com.proj.reggie.pojo.Dish;

public interface DishService extends IService<Dish> {

    public void savrWithFlavor(DishDto dishDto);

    public DishDto getByIDWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
