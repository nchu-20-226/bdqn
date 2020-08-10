package cn.smbms.servlet.bill;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.bill.BillServlceImpl;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.service.provider.ProviderServiceImpl;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.role.RoleServiceImpl;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jsp/bill.do")
public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        try {
            if(method.equals("query")){
                this.query(req,resp);
            }else if(method.equals("add")){
                this.addBill(req,resp);
            }else if(method.equals("view")){
                this.billView(req,resp);
            }else if(method.equals("getproviderlist")){
                this.getProviderlist(req,resp);
            }else if(method.equals("delbill")){
                this.delBill(req,resp);
            }else if(method.equals("modify")){
                this.getBillById(req,resp,"billmodify.jsp");
            }else if(method.equals("modifysave")){
                this.BillModify(req,resp);
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
        BillService billService=new BillServlceImpl();

        String queryname = req.getParameter("queryProductName");//商品名
        String temp = req.getParameter("queryProviderId");//供应商
        String queryIsPayment = req.getParameter("queryIsPayment");//是否付款

        String pageIndex = req.getParameter("pageIndex");
        int providerId=0;
        int isPayment=0;
        if(temp != null && !temp.equals("")){
            providerId= Integer.parseInt(temp);
        }
        if(queryIsPayment != null && !queryIsPayment.equals("")){
            isPayment= Integer.parseInt(queryIsPayment);
        }
        int currentPageNo=1;
        if(pageIndex!=null){
            currentPageNo=Integer.parseInt(pageIndex);
        }
        //获取商品列表
        int totalCount = billService.getBillCount(queryname,providerId,isPayment);
        PageSupport ps=new PageSupport();
        ps.setCurrentPageNo(currentPageNo);
        ps.setTotalCount(totalCount);

        List<Bill> billList = billService.getSub(queryname,providerId,isPayment,currentPageNo,ps.getPageSize());
        req.setAttribute("billList",billList);

        List<Provider> providerList = new ProviderServiceImpl().getProviderList();
        req.setAttribute("providerList",providerList);

        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",ps.getTotalPageCount());

        req.setAttribute("queryIsPayment",queryIsPayment);
        req.setAttribute("queryProviderId",providerId);
        req.setAttribute("queryProductName",queryname);

        req.getRequestDispatcher("billlist.jsp").forward(req,resp);
    }

    public void addBill(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String billCode = request.getParameter("billCode");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");

        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setCreatedBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        boolean flag = false;
        BillService billService = new BillServlceImpl();
        flag = billService.addBill(bill);
        System.out.println("add flag -- > " + flag);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/jsp/bill.do?method=query");
        }else{
            request.getRequestDispatcher("billadd.jsp").forward(request, response);
        }

    }

    private void billView(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String suid = request.getParameter("billid");
        int uid = Integer.parseInt(suid);
        BillService userService=new BillServlceImpl();
        Bill bill = userService.getBillbyId(uid);
        request.setAttribute("bill",bill);
        request.getRequestDispatcher("billview.jsp").forward(request,response);

    }
    private void getProviderlist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Provider> providerList = null;
        ProviderService providerService = new ProviderServiceImpl();
        providerList = providerService.getProviderList();
        //把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(providerList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    private void delBill(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String billid = req.getParameter("billid");
        Map<String,String> resultMap=new HashMap<>();
        int billId=0;
        try {
            billId=Integer.parseInt(billid);
        }catch (Exception e){
            e.printStackTrace();
        }
        BillService billService=new BillServlceImpl();
        if(billId<0){
            resultMap.put("delResult","notexist");
        }else{
            if(billService.delBill(billId)){
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

    private void getBillById(HttpServletRequest request,HttpServletResponse response,String url) throws Exception {
        String billid = request.getParameter("billid");
        if(!StringUtils.isNullOrEmpty(billid)){
            BillService billService=new BillServlceImpl();
            //调用后台方法得到user对象
            Bill bill = billService.getBillbyId(Integer.parseInt(billid));
            request.setAttribute("bill", bill);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void BillModify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");
        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        Bill bill = new Bill();
        bill.setId(Integer.valueOf(id));
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));

        bill.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        BillService billService = new BillServlceImpl();
        flag = billService.updateBill(bill);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/jsp/bill.do?method=query");
        }else{
            request.getRequestDispatcher("billmodify.jsp").forward(request, response);
        }
    }


}
