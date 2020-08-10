package cn.smbms.dao.bill;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface BillDao {
    //根据用户要求查询总数
    int getBillCount(Connection connection, String productName, int providerId, int isPayment) throws Exception;
    //分页显示商品数据
    List<Bill> getSub(Connection connection, String productName, int providerId, int isPayment, int pageIndex, int pageSize) throws Exception;

    int addBill(Connection connection,Bill bill) throws Exception;

    int delBill(Connection connection,int id) throws Exception;

    int updateBill(Connection connection,Bill bill) throws Exception;

    List<Bill> getBill(Connection connection) throws  Exception;

    Bill getBillbyId(Connection connection, int id) throws Exception;
}
