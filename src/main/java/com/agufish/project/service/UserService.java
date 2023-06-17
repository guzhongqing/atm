package com.agufish.project.service;

import com.agufish.project.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author acoolfish
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-05-30 13:59:46
 */
public interface UserService extends IService<User> {

    /**
     * 开户
     */
    void register();

    /**
     * 查询账号信息
     */
    void queryInfo();

    /**
     * 验证user
     * @return
     */
    User verifyUser();
}
