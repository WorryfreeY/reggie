package com.proj.reggie.dto;


import com.proj.reggie.pojo.Setmeal;
import com.proj.reggie.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
