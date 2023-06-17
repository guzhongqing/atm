package com.agufish.project.service;

import com.agufish.project.model.entity.User;
import com.agufish.project.utils.IdCardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void isValid() {
        String PID = "320113199001018091";
        PID = "340122200008246776";
        boolean b = IdCardUtil.isIdcard(PID);
        System.out.println(b);

    }


    @Test
    public void list() {
        for (User user : userService.list()) {
            System.out.println(user);
        }
    }


    @Test
    public void save() {
        String pid = "34011119900101955X";
        String username = "唐莲格";
        String telephone = "18492439104";

        User user = new User();
        user.setPid(pid);
        user.setUsername(username);
        user.setTelephone(telephone);


        boolean save = userService.save(user);
        Assertions.assertTrue(save);
    }

    @Test
    public void update() {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 先条件查询(where),再设置(set)
        updateWrapper.eq("id", 3).set("id", 7);


        boolean update = userService.update(updateWrapper);
        Assertions.assertTrue(update);
    }

    @Test
    public void remove() {

        // 不对queryWrapper进行任何操作就是选中所有记录
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        boolean remove = userService.remove(queryWrapper);
        Assertions.assertTrue(remove);
    }


}