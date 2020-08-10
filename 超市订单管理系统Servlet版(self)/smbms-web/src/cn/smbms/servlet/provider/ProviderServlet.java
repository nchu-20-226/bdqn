package cn.smbms.servlet.provider;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.bill.BillServlceImpl;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jsp/provider.do")
public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        try{
            if(method.equals("query")){
                System.out.println("---------"+method);
                this.query(req,resp);
            }else if(method.equals("add")){
                this.addprovider(req,resp);
            }else if(method.equals("view")){
                this.providerView(req,resp);
            }else if(method.equals("delprovider")){
                this.delprovider(req,resp);
            }else if(method.equals("modify")){
                this.getproviderById(req,resp,"providermodify.jsp");
            }else if(method.equals("providerForm")){
                this.ProviderModify(req,resp);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ProviderService providerService=new ProviderServiceImpl();

        String proCode = req.getParameter("queryProCode");//供应商编码
        String proName = req.getParameter("queryProName");//供应商名称

        String pageIndex = req.getParameter("pageIndex");
        int currentPageNo=1;
        if(pageIndex!=null){
            currentPageNo=Integer.parseInt(pageIndex);
        }
        //获取供应商数量
        int totalCount = providerService.getProviderCount(proCode,proName);
        PageSupport ps=new PageSupport();
        ps.setCurrentPageNo(currentPageNo);
        ps.setTotalCount(totalCount);

        List<Provider> providerList = providerService.getProvider(proCode,proName,currentPageNo,ps.getPageSize());
        req.setAttribute("providerList",providerList);

        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",ps.getTotalPageCount());

        req.setAttribute("queryProCode",proCode);
        req.setAttribute("queryProName",proName);

        req.getRequestDispatcher("providerlist.jsp").forward(req,resp);
    }

    private void addprovider(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProFax(proFax);
        provider.setProAddress(proAddress);
        provider.setProDesc(proDesc);
        provider.setCreatedBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        ProviderService providerService=new ProviderServiceImpl();
        boolean b = providerService.addProvider(provider);
        if(b){
            resp.sendRedirect(req.getContextPath()+"/jsp/provider.do?method=query");
        }else{
            req.getRequestDispatcher("provideradd.js").forward(req,resp);
        }

    }

    private void providerView(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String proid = req.getParameter("proid");
        if(!StringUtils.isNullOrEmpty(proid)){
            ProviderService providerService=new ProviderServiceImpl();
            int id=Integer.parseInt(proid);
            Provider provider = providerService.getProviderbyId(id);
            req.setAttribute("provider",provider);
            req.getRequestDispatcher("providerview.jsp").forward(req,resp);
        }

    }

    private void delprovider(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String proid = req.getParameter("proid");
        Map<String,String> resultMap=new HashMap<>();
        int id=0;
        try {
            id=Integer.parseInt(proid);
        }catch (Exception e){
            e.printStackTrace();
        }
        ProviderService providerService=new ProviderServiceImpl();
        if(id<0){
            resultMap.put("delResult","notexist");
        }else{
            if(providerService.delProvider(id)){
                resultMap.put("delResult","true");
            }else{
                resultMap.put("delResult","false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    private void getproviderById(HttpServletRequest request,HttpServletResponse response,String url) throws Exception {
        String proid = request.getParameter("proid");
        if(!StringUtils.isNullOrEmpty(proid)){
            ProviderService providerService=new ProviderServiceImpl();
            //调用后台方法得到user对象
            Provider provider = providerService.getProviderbyId(Integer.parseInt(proid));
            request.setAttribute("provider", provider);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void ProviderModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String proContact = request.getParameter("proContact");
        String proPhone = request.getParameter("proPhone");
        String proAddress = request.getParameter("proAddress");
        String proFax = request.getParameter("proFax");
        String proDesc = request.getParameter("proDesc");
        String id = request.getParameter("id");

        Provider provider = new Provider();
        provider.setId(Integer.valueOf(id));
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProFax(proFax);
        provider.setProAddress(proAddress);
        provider.setProDesc(proDesc);
        provider.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());

        boolean flag = false;
        ProviderService providerService = new ProviderServiceImpl();
        flag = providerService.updateProvider(provider);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/jsp/provider.do?method=query");
        }else{
            request.getRequestDispatcher("providermodify.jsp").forward(request, response);
        }
    }


}
