package cn.smbms.service.bill;

import cn.smbms.pojo.Bill;

import java.sql.Connection;
import java.util.List;

public interface BillService {
    //根据用户要求查询总数
    int getBillCount(String productName, int providerId, int isPayment) throws Exception;
    //分页显示商品数据
    List<Bill> getSub(String productName, int providerId, int isPayment, int pageIndex, int pageSize) throws Exception;

    boolean addBill(Bill bill) throws Exception;

    boolean delBill(int id) throws Exception;

    boolean updateBill(Bill bill) throws Exception;

    List<Bill> getBill() throws  Exception;

    Bill getBillbyId(int id) throws Exception;
}
