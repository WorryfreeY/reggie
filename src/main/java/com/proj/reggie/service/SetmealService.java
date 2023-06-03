package com.proj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proj.reggie.dto.SetmealDto;
import com.proj.reggie.pojo.Setmeal;
import com.proj.reggie.pojo.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDishes(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
