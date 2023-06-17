package com.agufish.project.mapper;

import com.agufish.project.model.entity.Card;
import com.agufish.project.model.vo.CardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author acoolfish
 * @description 针对表【card】的数据库操作Mapper
 * @createDate 2023-05-30 13:59:46
 * @Entity Card
 */
public interface CardMapper extends BaseMapper<Card> {


    /**
     * 根据开户日期查询card
     *
     * @return
     */

    @Select("select c.*,u.username,d.name as deposit_name from " +
            "card c join user u on u.id = c.user_id join deposit d on d.id = c.deposit_id " +
            "where week(open_date)=week(now())")
    List<CardVO> queryCardByOpenDate();


    /**
     * 根据挂失查询卡信息
     * @return
     */
    List<CardVO> getCardByIsLoss();


    /**
     * 根据用户id查询账户信息
     * @param userId
     * @return
     */
    @Select("select c.*,u.username,d.name as deposit_name from " +
            "card c join user u on u.id = c.user_id join deposit d on d.id = c.deposit_id " +
            "where c.user_id=#{userId}")
    List<CardVO> getInfoByUserId(Integer userId);

}




