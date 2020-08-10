package cn.smbms.dao.provider;

import cn.smbms.dao.BaseDao;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao {
    @Override
    public List<Provider> getProviderList(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Provider> providerList = new ArrayList<Provider>();
        if(connection != null){
            String sql = "select * from smbms_provider";
            Object[] params = {};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            while(rs.next()){
                Provider _provider = new Provider();
                _provider.setId(rs.getInt("id"));
                _provider.setProCode(rs.getString("proCode"));
                _provider.setProName(rs.getString("proName"));
                _provider.setProDesc(rs.getString("proDesc"));
                _provider.setProContact(rs.getString("proContact"));
                _provider.setProPhone(rs.getString("proPhone"));
                _provider.setProAddress(rs.getString("proAddress"));
                _provider.setProFax(rs.getString("proFax"));
                _provider.setCreationDate(rs.getTimestamp("creationDate"));
                providerList.add(_provider);
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return providerList;
    }

    @Override
    public int getProviderCount(Connection connection, String proCode, String proName) throws Exception {
        String sql="SELECT COUNT(1) count FROM smbms_provider where 1=1";
        PreparedStatement ps=null;
        ResultSet rs=null;
        int count=0;
        StringBuffer sb=new StringBuffer(sql);
        List<Object> list=new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(proCode)){
            sb.append(" and proCode LIKE CONCAT('%',?,'%')");
            list.add(proCode);
        }
        if(!StringUtils.isNullOrEmpty(proName)){
            sb.append(" and proName LIKE CONCAT('%',?,'%')");
            list.add(proName);
        }

        sql=sb.toString();
        rs = BaseDao.execute(connection, ps, rs, sql, list.toArray());
        if(rs.next()){
            count = rs.getInt("count");
        }
        BaseDao.closeResource(null,ps,rs);
        return count;
    }

    @Override
    public List<Provider> getProvider(Connection connection, String proCode, String proName, int pageIndex, int pageSize) throws Exception {
        String sql="SELECT * FROM smbms_provider WHERE 1=1";
        List<Object> list=new ArrayList<>();
        List<Provider> providerList=new ArrayList<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        StringBuffer sb=new StringBuffer(sql);
        if(!StringUtils.isNullOrEmpty(proCode)){
            sb.append(" and proCode LIKE CONCAT('%',?,'%')");
            list.add(proCode);
        }
        if(!StringUtils.isNullOrEmpty(proName)){
            sb.append(" and proName LIKE CONCAT('%',?,'%')");
            list.add(proName);
        }
        sb.append(" limit ?,?");
        list.add((pageIndex-1)*pageSize);
        list.add(pageSize);
        rs=BaseDao.execute(connection,ps,rs,sb.toString(),list.toArray());
        while(rs.next()){
            Provider _provider = new Provider();
            _provider.setId(rs.getInt("id"));
            _provider.setProCode(rs.getString("proCode"));
            _provider.setProName(rs.getString("proName"));
            _provider.setProDesc(rs.getString("proDesc"));
            _provider.setProContact(rs.getString("proContact"));
            _provider.setProPhone(rs.getString("proPhone"));
            _provider.setProAddress(rs.getString("proAddress"));
            _provider.setProFax(rs.getString("proFax"));
            _provider.setCreationDate(rs.getTimestamp("creationDate"));
            providerList.add(_provider);
        }
        BaseDao.closeResource(null, ps, rs);
        return providerList;
    }

    @Override
    public int addProvider(Connection connection, Provider provider) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "insert into smbms_provider (proCode,proName,proAddress,proContact,proPhone,proFax,proDesc,createdBy,creationDate) values(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProAddress(),provider.getProContact(),provider.getProPhone(),provider.getProFax(),provider.getProDesc(),provider.getCreatedBy(),provider.getCreationDate()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    @Override
    public int delProvider(Connection connection, int id) throws Exception {
        String sql="delete from smbms_provider where id=?";
        PreparedStatement ps=null;
        Object[] params={id};
        int i = BaseDao.execute(connection, ps, sql, params);
        BaseDao.closeResource(null,ps,null);
        return i;
    }

    @Override
    public int updateProvider(Connection connection, Provider provider) throws Exception {
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update smbms_provider set proName=?,proDesc=?,proContact=?," +
                    "proPhone=?,proAddress=?,proFax=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
                    provider.getProFax(),provider.getModifyBy(),provider.getModifyDate(),provider.getId()};
            flag = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return flag;
    }

    @Override
    public Provider getProviderbyId(Connection connection, int id) throws Exception {
        Provider provider = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select * from smbms_provider p where id=?";
            Object[] params = {id};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if(rs.next()){
                provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return provider;
    }
}
