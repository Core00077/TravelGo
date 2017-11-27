package cn.corechan.travel.dao;

import cn.corechan.travel.vo.Business;

import java.sql.SQLException;

public interface IBusinessDAO {
    /**
     * 数据的添加操作
     * @param business 要添加的商家
     * @return 是否添加成功的标记
     * @throws SQLException 数据库异常交给调用出处理
     */
    boolean doCreate(Business business) throws SQLException;

    /**
     * 根据商家手机号查询商家信息
     * @param phoneNumber 手机号
     * @return 商家的vo对象
     * @throws SQLException 数据库异常交给调用出处理
     */
    Business findByPhoneNumber(String phoneNumber) throws SQLException;
}
