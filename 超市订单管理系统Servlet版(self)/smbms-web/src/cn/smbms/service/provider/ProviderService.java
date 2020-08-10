package cn.smbms.service.provider;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface ProviderService {
    public List<Provider> getProviderList();

    //根据用户要求查询总数
    int getProviderCount(String proCode, String proName) throws Exception;
    //分页显示商品数据
    List<Provider> getProvider(String proCode, String proName, int pageIndex, int pageSize) throws Exception;

    boolean addProvider(Provider provider) throws Exception;

    boolean delProvider(int id) throws Exception;

    boolean updateProvider(Provider provider) throws Exception;


    Provider getProviderbyId(int id) throws Exception;

}
