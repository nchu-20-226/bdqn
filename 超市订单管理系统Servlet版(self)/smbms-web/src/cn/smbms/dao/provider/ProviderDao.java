package cn.smbms.dao.provider;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface ProviderDao {
    public List<Provider> getProviderList(Connection connection)throws Exception;

    //根据用户要求查询总数
    int getProviderCount(Connection connection, String proCode, String proName) throws Exception;
    //分页显示商品数据
    List<Provider> getProvider(Connection connection, String proCode, String proName, int pageIndex, int pageSize) throws Exception;

    int addProvider(Connection connection,Provider provider) throws Exception;

    int delProvider(Connection connection,int id) throws Exception;

    int updateProvider(Connection connection,Provider provider) throws Exception;

    Provider getProviderbyId(Connection connection, int id) throws Exception;
}
