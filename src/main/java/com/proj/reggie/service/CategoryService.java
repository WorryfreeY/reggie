package com.proj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proj.reggie.pojo.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
