package com.proj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.proj.reggie.pojo.Employee;
import com.proj.reggie.service.EmployeeService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
