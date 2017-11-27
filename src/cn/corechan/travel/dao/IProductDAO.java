package cn.corechan.travel.dao;

import cn.corechan.travel.vo.Product;

import java.sql.SQLException;

public interface IProductDAO {
    /**
     * 数据的添加操作
     * @param product 要添加的商品信息
     * @return 是否添加成功的标记
     * @throws SQLException 数据库异常交给调用出处理
     */
    boolean doCreate(Product product) throws SQLException;

    /**
     * 根据商品编号查询商品信息
     * @param id 商品编号
     * @return 商品的vo对象
     * @throws SQLException 数据库异常交给调用出处理
     */
    Product findByPhoneNumber(String id) throws SQLException;
}
