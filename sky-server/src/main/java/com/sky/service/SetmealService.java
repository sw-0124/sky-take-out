package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除查询套餐也要删除套餐和菜品的关联关系
     * @param ids
     * @return
     */
    void deleteBatch(List<Long> setmealIds);

    /**
     * 根据id查询套餐以及与菜品的关系
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 修改套餐以及套餐和菜品的关系
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);
}
