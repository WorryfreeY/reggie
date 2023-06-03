package com.proj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.proj.reggie.dto.DishDto;
import com.proj.reggie.pojo.Category;
import com.proj.reggie.pojo.Dish;
import com.proj.reggie.pojo.DishFlavor;
import com.proj.reggie.service.CategoryService;
import com.proj.reggie.service.DishFlavorService;
import com.proj.reggie.service.DishService;
import com.proj.reggie.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.savrWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name ){
        Page<Dish> pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPageInfo=new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(name),Dish::getName,name);
        dishService.page(pageInfo);
        BeanUtils.copyProperties(pageInfo,dishDtoPageInfo,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtoRecords = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            if(category!=null){
            dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPageInfo.setRecords(dishDtoRecords);
        return R.success(dishDtoPageInfo);

    }

    @GetMapping("/{id}")
    public R<DishDto> getByID(@PathVariable("id") Long id){
        DishDto dishDto=dishService.getByIDWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> getByCategory(Dish dish){
//        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(Dish::getCategoryId,dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus,1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> dishes = dishService.list(queryWrapper);
//        return R.success(dishes);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> getByCategory(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(Dish::getCategoryId,dish.getCategoryId());
        queryWrapper1.eq(Dish::getStatus,1);
        queryWrapper1.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dishService.list(queryWrapper1);
        List<DishDto> dishDtoList = dishes.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            if(category!=null){
                dishDto.setCategoryName(category.getName());
            }
            LambdaQueryWrapper<DishFlavor> queryWrapper2=new LambdaQueryWrapper<>();
            queryWrapper2.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> flavors = dishFlavorService.list(queryWrapper2);

            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
