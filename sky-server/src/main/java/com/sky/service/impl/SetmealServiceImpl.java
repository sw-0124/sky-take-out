package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //插入套餐数据
        setmealMapper.insert(setmeal);

        //获取插入数据库的id
        Long setmealId = setmeal.getId();

        //保持套餐和菜品的关联关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        //插入关联关系
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //使用pageHelper插件完成分页查询
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();
        System.out.println(result);
        return new PageResult(total, result);

    }

    /**
     * 批量删除套餐也要删除套餐和菜品的关联关系
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {

        //批量删除套餐
        setmealMapper.deleteBatch(ids);

        //删除套餐和菜品的关联关系
        setmealDishMapper.deleteBySetmealIds(ids);

    }

    /**
     * 根据id查询套餐以及与菜品的关系
     * @param id
     * @return
     */
    @Override
    public SetmealVO getByIdWithDish(Long id) {

        SetmealVO setmealVO = new SetmealVO();

        //查询套餐信息
        Setmeal setmeal = setmealMapper.getById(id);
        //查询套餐与菜品的关联信息
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;

    }

    /**
     * 修改套餐以及套餐和菜品的关系
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //修改套餐
        setmealMapper.update(setmeal);

        List<Long> ids = new ArrayList<>();
        ids.add(setmealDTO.getId());
        //删除套餐和菜品之前的关联关系
        setmealDishMapper.deleteBySetmealIds(ids);
        //新增现在套餐和菜品的关联关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);

    }


}
