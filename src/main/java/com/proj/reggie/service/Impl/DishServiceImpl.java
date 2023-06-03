package com.proj.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proj.reggie.dto.DishDto;
import com.proj.reggie.mapper.DishMapper;
import com.proj.reggie.pojo.Dish;
import com.proj.reggie.pojo.DishFlavor;
import com.proj.reggie.service.CategoryService;
import com.proj.reggie.service.DishFlavorService;
import com.proj.reggie.service.DishService;
import com.proj.reggie.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    @Override
    public void savrWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId=dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
    @Override
    public DishDto getByIDWithFlavor(Long id) {
        DishDto dishDto=new DishDto();
        Dish dish = this.getById(id);

        BeanUtils.copyProperties(dish,dishDto);

//      String name = categoryService.getById(dish.getCategoryId()).getName();
//      dishDto.setCategoryName(name);
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        Long dishId = dishDto.getId();
        queryWrapper.eq(DishFlavor::getDishId,dishId);
        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors= flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

}
