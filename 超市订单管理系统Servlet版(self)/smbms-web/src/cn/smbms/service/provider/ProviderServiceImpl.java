package cn.smbms.service.provider;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.dao.provider.ProviderDaoImpl;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    ProviderDao providerDao=new ProviderDaoImpl();
    @Override
    public List<Provider> getProviderList() {
        Connection connection = null;
        List<Provider> providerList = null;
        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.getProviderList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return providerList;
    }

    @Override
    public int getProviderCount(String proCode, String proName) throws Exception {
        Connection connection=BaseDao.getConnection();
        int providerCount =providerDao.getProviderCount(connection, proCode, proName);
        BaseDao.closeResource(connection,null,null);
        return providerCount;
    }

    @Override
    public List<Provider> getProvider(String proCode, String proName, int pageIndex, int pageSize) throws Exception {
        Connection connection=BaseDao.getConnection();

        List<Provider> providerList = providerDao.getProvider(connection, proCode, proName, pageIndex, pageSize);
        BaseDao.closeResource(connection,null,null);
        return providerList;
    }


    @Override
    public boolean addProvider(Provider provider) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        if (providerDao.addProvider(connection, provider)>0){
            flag=true;
        }
        BaseDao.closeResource(connection,null,null);
        return flag;
    }

    @Override
    public boolean delProvider(int id) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        if (providerDao.delProvider(connection, id)>0){
            flag=true;
        }
        BaseDao.closeResource(connection,null,null);
        return flag;
    }

    @Override
    public boolean updateProvider(Provider provider) throws Exception {
        Connection connection=BaseDao.getConnection();
        boolean flag=false;
        if (providerDao.updateProvider(connection, provider)>0){
            flag=true;
        }
        BaseDao.closeResource(connection,null,null);
        return flag;
    }

    @Override
    public Provider getProviderbyId(int id) throws Exception {
        Connection connection=BaseDao.getConnection();
        Provider provider = providerDao.getProviderbyId(connection, id);
        BaseDao.closeResource(connection,null,null);
        return provider;
    }
}
