package cn.corechan.travel.dao;

import cn.corechan.travel.vo.LifePhoto;

import java.sql.SQLException;
import java.util.List;

public interface ILifePhotoDAO {
    /**
     * 数据的添加操作
     * @param lifePhoto 要添加的商家生活照片
     * @return 是否添加成功的标记
     * @throws SQLException 数据库异常交给调用出处理
     */
    boolean doCreate(LifePhoto lifePhoto) throws SQLException;

    /**
     * 根据商家手机号查询商家生活照片
     * @param phoneNumber 商家手机号
     * @return 商家生活照片的vo对象组
     * @throws SQLException 数据库异常交给调用出处理
     */
    List<LifePhoto> findByPhoneNumber(String phoneNumber) throws SQLException;
}
