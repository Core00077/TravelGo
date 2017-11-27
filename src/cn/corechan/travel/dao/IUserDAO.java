package cn.corechan.travel.dao;

import cn.corechan.travel.json.Status;
import cn.corechan.travel.vo.User;

import java.sql.SQLException;

public interface IUserDAO {
    /**
     * 数据的添加操作
     * @param user 要添加新的用户
     * @return 是否添加成功的标记
     * @throws SQLException 数据库异常交给调用出处理
     */
    boolean doCreate(User user) throws SQLException;

    /**
     * 用户修改信息。
     *
     *
     */
    boolean doChange() throws SQLException;

    /**
     * 根据用户手机号查询用户信息
     * @param phoneNumber 手机号
     * @return 用户的vo对象
     * @throws SQLException 数据库异常交给调用出处理
     */
    User findByPhoneNumber(String phoneNumber) throws SQLException;
}
