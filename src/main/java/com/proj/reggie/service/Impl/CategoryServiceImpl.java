package com.proj.reggie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proj.reggie.mapper.CategoryMapper;
import com.proj.reggie.pojo.Category;
import com.proj.reggie.pojo.Dish;
import com.proj.reggie.pojo.Setmeal;
import com.proj.reggie.service.CategoryService;
import com.proj.reggie.service.DishService;
import com.proj.reggie.service.SetmealService;
import com.proj.reggie.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(queryWrapper1);
        if(dishCount>0){
            throw new CustomException("该分类下已有菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(queryWrapper2);

        if(setmealCount>0){
            throw new CustomException("该分类下已有套餐，无法删除");
        }
        super.removeById(id);
    }
}
