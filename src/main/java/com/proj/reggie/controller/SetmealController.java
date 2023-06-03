package com.proj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.proj.reggie.dto.SetmealDto;
import com.proj.reggie.pojo.Category;
import com.proj.reggie.pojo.Dish;
import com.proj.reggie.pojo.Setmeal;
import com.proj.reggie.pojo.SetmealDish;
import com.proj.reggie.service.CategoryService;
import com.proj.reggie.service.SetmealDishService;
import com.proj.reggie.service.SetmealService;
import com.proj.reggie.utils.R;
import org.apache.catalina.mbeans.ClassNameMBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> saveWithDishes(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDishes(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(Setmeal::getName,name);
        setmealService.page(setmealPage);
        List<Setmeal> records = setmealPage.getRecords();
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<SetmealDto> setmealDtoList = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除套餐成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable("status") int status,@RequestParam("ids") List<Long> id){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,id);
        List<Setmeal> setmeals = setmealService.list(queryWrapper);
        for (Setmeal setmeal : setmeals) {
            setmeal.setStatus(status);
        }
        setmealService.updateBatchById(setmeals);
        if(status==0)
            return R.success("停售成功");
        else
            return R.success("起售成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> getByCategory(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus,1);
        List<Setmeal> setmeals = setmealService.list(queryWrapper);
        return R.success(setmeals);
    }
}
