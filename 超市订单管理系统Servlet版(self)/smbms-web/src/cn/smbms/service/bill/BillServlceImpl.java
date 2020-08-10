package cn.smbms.service.bill;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.bill.BillDaoImpl;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.User;

import java.sql.Connection;
import java.util.List;

public class BillServlceImpl implements BillService {
    BillDao billDao=new BillDaoImpl();
    @Override
    public int getBillCount(String productName, int providerId, int isPayment) throws Exception {
        Connection connection = BaseDao.getConnection();
        int count = billDao.getBillCount(connection, productName, providerId,isPayment);
        BaseDao.closeResource(connection,null,null);
        return count;
    }

    @Override
    public List<Bill> getSub(String productName, int providerId, int isPayment, int pageIndex, int pageSize) throws Exception {
        Connection connection = BaseDao.getConnection();
        List<Bill> list = billDao.getSub(connection, productName, providerId, isPayment,pageIndex, pageSize);
        BaseDao.closeResource(connection,null,null);
        return list;
    }

    @Override
    public boolean addBill(Bill bill) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        int i = billDao.addBill(connection, bill);
        if(i>0){
            flag=true;
        }
        BaseDao.closeResource(connection, null,null);
        return flag;
    }

    @Override
    public boolean delBill(int id) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        int i = billDao.delBill(connection, id);
        if(i>0){
            flag=true;
        }
        BaseDao.closeResource(connection, null,null);
        return flag;
    }

    @Override
    public boolean updateBill(Bill bill) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        int i = billDao.updateBill(connection, bill);
        if(i>0){
            flag=true;
        }
        BaseDao.closeResource(connection, null,null);
        return flag;
    }

    @Override
    public List<Bill> getBill() throws Exception {
        return null;
    }

    @Override
    public Bill getBillbyId(int id) throws Exception {
        Connection connection=BaseDao.getConnection();
        Bill bill = billDao.getBillbyId(connection, id);
        BaseDao.closeResource(connection,null,null);
        return bill;
    }
}
