package org.totem.iot.action;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import org.totem.components.data.BeanHelper;
import org.totem.common.utils.TPageBounds;
import org.totem.common.utils.model.JsonVo;
import org.totem.common.utils.AuthorityUtil;
import org.totem.common.utils.CommonsUtil;
import org.totem.common.utils.ExportExcel;
import org.totem.components.data.DateUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import org.totem.common.service.AuthorityService;
import org.totem.common.service.CommonService;
import org.totem.iot.service.IotService;
import org.totem.common.model.SysResAuthCfg;
import org.totem.iot.model.IotUser;
import org.totem.iot.service.IotUserService;
import org.totem.components.data.json.JsonUtils;
import org.totem.tour.service.TourService;

@Controller
@RequestMapping("/iot/iotuser")
public class IotUserController extends CommonController {
    public static org.apache.log4j.Logger logger = Logger.getLogger(IotUserController.class.getName());

    @Autowired
    private IotUserService service;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IotService iotService;

    @Autowired
    private TourService tourService;

    @ResponseBody
    @RequestMapping(value = "/create")
    public Object createIotUser(HttpServletRequest request, HttpServletResponse response, Model m) {
        JSONObject result = new JSONObject();
        try {
            IotUser model = new IotUser();
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/member")
    public ModelAndView members(HttpServletRequest request, HttpServletResponse response, Model m) {
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotUser_member"));
        m.addAttribute("pageName","iot_IotUser_member");
        return new ModelAndView("/iot/iotuser/mMember");
    }

    @ResponseBody
    @RequestMapping(value = "/query/{id}")
    public Object queryIotUserById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        JSONObject result = new JSONObject();
        try {
            IotUser model = service.getById(id);
            if (null==model){
                return createIotUser(request, response, m);
            }
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iotService.checkResult(result, "queryId", "IotUser");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editIotUserById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        Map<String,Object> result = CommonsUtil.request2map(request);

        try {
            if (!StringUtils.equals(id, "create")){
                result.put("iotUserId",id);
                result.put("iotUser",service.getById(id));

            }else{
                result.put("iotUser",new IotUser());
                result.remove("iotUserId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotUser_"+result.get("actionType")));
        m.addAttribute("pageName","iot_IotUser_"+result.get("actionType"));

        m.addAllAttributes(result);
        String actionType = (String)result.get("actionType");
        if (StringUtils.isBlank(actionType)||!StringUtils.equals(actionType,"view")||StringUtils.equals(actionType,"create")||StringUtils.equals(actionType,"auth")){
            actionType = "edit";
        }
        if (null!=result.get("func")){
            actionType += (String)result.get("func");
        }
        return new ModelAndView("/iot/iotuser/"+actionType);
    }

    /**
    * 保存用户
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonVo<IotUser> updateIotUser(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUser> json = new JsonVo<IotUser>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        result.remove("userServiceServiceIdChilds");
        result.remove("iotDeviceDeviceBelongingChilds");
        IotUser model = new IotUser();
        BeanHelper.copyObjectProperties(model,result);

        try {
            IotUser pf = updateIotUser(model,request);
            request.setAttribute("model", pf);
            json.setT(pf);
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    private IotUser updateIotUser(IotUser model, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
        String actionType = "update";
        if (StringUtils.isBlank(model.getIotUserId())){
            actionType = "insert";
        }
        IotUser pf = service.updateIotUser(model);

        //保存更新日志
        String actionContent = JsonUtils.obj2json(model);
        if (null!=request.getParameter("userService_ServiceIdChilds")){
            actionContent= StringUtils.left(actionContent, actionContent.length()-1)+",\"userService_ServiceIdChilds\":\""+(String)request.getParameter("userService_ServiceIdChilds")+"\""+StringUtils.right(actionContent,1);
        }
        if (null!=request.getParameter("iotDevice_DeviceBelongingChilds")){
            actionContent= StringUtils.left(actionContent, actionContent.length()-1)+",\"iotDevice_DeviceBelongingChilds\":\""+(String)request.getParameter("iotDevice_DeviceBelongingChilds")+"\""+StringUtils.right(actionContent,1);
        }
        commonService.saveActionLog(request,actionType,actionContent ,pf.getIotUserId(),"IotUser","IOT_USER");

        return pf;
    }

    /**
    * 复制一条用户
    */
    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public JsonVo<IotUser> copyIotUser(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUser> json = new JsonVo<IotUser>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        result.remove("userServiceServiceIdChilds");
        result.remove("iotDeviceDeviceBelongingChilds");
        IotUser model = JsonUtils.json2object(JsonUtils.obj2json(result), IotUser.class);
        try{
            model.setIotUserId(null);
            IotUser pf = updateIotUser(model,request);
            request.setAttribute("model", pf);
            json.setT(pf);
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    /**
    * 刪除一条用户
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonVo<IotUser> deleteIotUser(@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUser> json = new JsonVo<IotUser>();
        try {
            IotUser model = service.getById(id);
            int i  = service.deleteIotUser(id);
            commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotUser","IOT_USER");
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    /**
    * 按条件刪除用户
    */
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public JsonVo<IotUser> deleteIotUsers(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUser> json = new JsonVo<IotUser>();
        Map<String,Object> map = CommonsUtil.request2map(request);
        if (map.size()==1&&(null!=map.get("iotUserId")||(null!=map.get("iotUserIds")&&((String[])map.get("iotUserIds")).length==1))){
            String id = (String)map.get("iotUserId");
            if (StringUtils.isBlank(id)){
                id = ((String[])map.get("iotUserIds"))[0];
            }
            try {
                IotUser model = service.getById(id);
                int i  = service.deleteIotUser(id);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotUser","IOT_USER");
                json.setResult(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                json.setResult(false);
                return json;
            }
        }else{
            List<IotUser> list = service.queryIotUsers(map);
            try {
                int i  = service.deleteIotUsers(map);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(list),"","IotUser","IOT_USER");
                json.setResult(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                json.setResult(false);
                return json;
            }
        }
    }

    /**
    * 获取所有用户数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAll")
    public Object queryAllData(IotUser model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotUser> dataList = service.queryIotUsers(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 获取所有有权访问的用户数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAuthAll")
    public Object queryAuthAllData(IotUser model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotUser> dataList = service.queryIotUsers(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 导出所有有权访问的用户数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/exportAuthAll")
    public Object exportAuthAllDataToExcel(IotUser model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List<IotUser> dataList = service.queryIotUsers(map);
            String filename = "data" + ".xlsx";
            filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            response.setContentType("APPLICATION/DOWNLOAD");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            List<String> titles = new ArrayList<String>();
            List<String> codes = new ArrayList<String>();
            titles.add("激活");
            codes.add("userValid");
            titles.add("用户名");
            codes.add("userName");
            titles.add("显示名");
            codes.add("userDisplayName");
            titles.add("密钥");
            codes.add("userToken");
            titles.add("邮箱");
            codes.add("userEmail");
            titles.add("余额");
            codes.add("userBalance");
            titles.add("注册日期");
            codes.add("userDate");
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (IotUser iotUser : dataList) {
                Map<String, Object> row = new HashMap<String, Object>();
                row.put("userValid", iotUser.getUserValid());
                row.put("userName", iotUser.getUserName());
                row.put("userDisplayName", iotUser.getUserDisplayName());
                row.put("userToken", iotUser.getUserToken());
                row.put("userEmail", iotUser.getUserEmail());
                row.put("userBalance", iotUser.getUserBalance());
                row.put("userDate", iotUser.getUserDate());
                datas.add(row);

            }
            ByteArrayOutputStream out = ExportExcel.outExcel(titles, codes, datas);
            OutputStream outStream = response.getOutputStream();
            outStream.write(out.toByteArray());
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 通过查询条件获取所有有权访问的用户数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryRemote")
    public Object queryRemotea(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(request.getParameter("q"))){
            map.put("remoteParameter", request.getParameter("q"));
        }else{
            return null;
        }
        try {
            List< IotUser> dataList = service.queryIotUsers(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryData(IotUser model,HttpServletRequest request, HttpServletResponse response, Model m,
    @RequestParam(required = false, defaultValue = "1") int page,
    @RequestParam(required = false, defaultValue = TPageBounds.PAGE_SIZE) int rows,
    @RequestParam(required = false, defaultValue = "") String sort,
    @RequestParam(required = false, defaultValue = "") String order,
    @RequestParam(required = false, defaultValue = "") String dir) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            String orderBy = service.getOrder(sort,order);
            if (StringUtils.isNotBlank(orderBy)){
                map.put("orderBy",orderBy);
            }
            map.remove("sort");
            //兼容easyui的datagrid和jquery的bootstrap table
            if (null!=map.get("limit")&&StringUtils.isNotBlank((String)map.get("limit"))){
                rows = Integer.parseInt((String)map.get("limit"));
            }

            if (null!=map.get("offset")&&StringUtils.isNotBlank((String)map.get("offset"))){
                page = Integer.parseInt((String)map.get("offset"))/rows+1;
            }

            PageBounds pageBound = new PageBounds(page, rows);

            List< IotUser> dataList = service.queryIotUsers(map, pageBound);
            PageList<IotUser> pageList = (PageList<IotUser>) dataList;

            map.put("page", page);
            map.put("total", pageList.getPaginator().getTotalCount());
            map.put("pages", pageList.getPaginator().getTotalPages());
            map.put("limit", TPageBounds.PAGE_LIMIT);
            map.put("rows", dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/queryby/{name}/is/{value}")
    public Object queryIotUserById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String name,@PathVariable String value) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(name,value);
        try {
            List<IotUser> models = service.queryIotUsers(map);
            map.put("rows",models);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model m) {
        m.addAllAttributes(CommonsUtil.request2map(request));
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotUser"));
        m.addAttribute("pageName","iot_IotUser");
        String page_name="manage";
        if (null!=request.getParameter("pageName")){
            page_name = (String)request.getParameter("pageName");
        }
        return new ModelAndView("/iot/iotuser/"+page_name);
    }
}