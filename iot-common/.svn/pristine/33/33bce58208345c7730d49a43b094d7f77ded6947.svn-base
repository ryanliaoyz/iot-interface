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
import org.totem.iot.model.UserService;
import org.totem.iot.service.UserServiceService;
import org.totem.iot.model.IotUserServiceRelation;
import org.totem.iot.service.IotUserServiceRelationService;
import org.totem.components.data.json.JsonUtils;
import org.totem.tour.service.TourService;

@Controller
@RequestMapping("/iot/userservice")
public class UserServiceController extends CommonController {
    public static org.apache.log4j.Logger logger = Logger.getLogger(UserServiceController.class.getName());

    @Autowired
    private UserServiceService service;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IotUserServiceRelationService iotUserServiceRelationService;

    @Autowired
    private IotService iotService;

    @Autowired
    private TourService tourService;

    @ResponseBody
    @RequestMapping(value = "/create")
    public Object createUserService(HttpServletRequest request, HttpServletResponse response, Model m) {
        JSONObject result = new JSONObject();
        try {
            UserService model = new UserService();
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/query/{id}")
    public Object queryUserServiceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        JSONObject result = new JSONObject();
        try {
            UserService model = service.getById(id);
            if (null==model){
                return createUserService(request, response, m);
            }
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iotService.checkResult(result, "queryId", "UserService");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editUserServiceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        Map<String,Object> result = CommonsUtil.request2map(request);

        try {
            if (!StringUtils.equals(id, "create")){
                result.put("serviceId",id);
                result.put("userService",service.getById(id));

            }else{
                result.put("userService",new UserService());
                result.remove("serviceId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_UserService_"+result.get("actionType")));
        m.addAttribute("pageName","iot_UserService_"+result.get("actionType"));

        m.addAllAttributes(result);
        String actionType = (String)result.get("actionType");
        if (StringUtils.isBlank(actionType)||!StringUtils.equals(actionType,"view")||StringUtils.equals(actionType,"create")||StringUtils.equals(actionType,"auth")){
            actionType = "edit";
        }
        if (null!=result.get("func")){
            actionType += (String)result.get("func");
        }
        return new ModelAndView("/iot/userservice/"+actionType);
    }

    /**
    * 保存服务
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonVo<UserService> updateUserService(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<UserService> json = new JsonVo<UserService>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        UserService model = new UserService();
        BeanHelper.copyObjectProperties(model,result);

        try {
            UserService pf = updateUserService(model,request);
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

    private UserService updateUserService(UserService model, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
        String actionType = "update";
        if (StringUtils.isBlank(model.getServiceId())){
            actionType = "insert";
        }
        UserService pf = service.updateUserService(model);

        //保存关联对象用户服务关系
        if (StringUtils.isNotBlank(request.getParameter("userService_UserId"))){
            IotUserServiceRelation relaModel = new IotUserServiceRelation();
            relaModel.setUserId((String)request.getParameter("userService_UserId"));
            relaModel.setServiceId(pf.getServiceId());
            List<IotUserServiceRelation> list = iotUserServiceRelationService.queryIotUserServiceRelations(BeanHelper.obj2Map(relaModel));
            if (null==list||list.size()==0){
                iotUserServiceRelationService.updateIotUserServiceRelation(relaModel);
            }
        }

        //保存更新日志
        String actionContent = JsonUtils.obj2json(model);
        commonService.saveActionLog(request,actionType,actionContent ,pf.getServiceId(),"UserService","USER_SERVICE");

        return pf;
    }

    /**
    * 复制一条服务
    */
    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public JsonVo<UserService> copyUserService(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<UserService> json = new JsonVo<UserService>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        UserService model = JsonUtils.json2object(JsonUtils.obj2json(result), UserService.class);
        try{
            model.setServiceId(null);
            UserService pf = updateUserService(model,request);
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
    * 刪除一条服务
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonVo<UserService> deleteUserService(@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<UserService> json = new JsonVo<UserService>();
        try {
            UserService model = service.getById(id);
            int i  = service.deleteUserService(id);
            commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"UserService","USER_SERVICE");
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    /**
    * 按条件刪除服务
    */
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public JsonVo<UserService> deleteUserServices(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<UserService> json = new JsonVo<UserService>();
        Map<String,Object> map = CommonsUtil.request2map(request);
        if (map.size()==1&&(null!=map.get("serviceId")||(null!=map.get("serviceIds")&&((String[])map.get("serviceIds")).length==1))){
            String id = (String)map.get("serviceId");
            if (StringUtils.isBlank(id)){
                id = ((String[])map.get("serviceIds"))[0];
            }
            try {
                UserService model = service.getById(id);
                int i  = service.deleteUserService(id);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"UserService","USER_SERVICE");
                json.setResult(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                json.setResult(false);
                return json;
            }
        }else{
            List<UserService> list = service.queryUserServices(map);
            try {
                int i  = service.deleteUserServices(map);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(list),"","UserService","USER_SERVICE");
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
    * 获取所有服务数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAll")
    public Object queryAllData(UserService model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< UserService> dataList = service.queryUserServices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 获取所有有权访问的服务数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAuthAll")
    public Object queryAuthAllData(UserService model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< UserService> dataList = service.queryUserServices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 导出所有有权访问的服务数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/exportAuthAll")
    public Object exportAuthAllDataToExcel(UserService model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List<UserService> dataList = service.queryUserServices(map);
            String filename = "data" + ".xlsx";
            filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            response.setContentType("APPLICATION/DOWNLOAD");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            List<String> titles = new ArrayList<String>();
            List<String> codes = new ArrayList<String>();
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (UserService userService : dataList) {
                Map<String, Object> row = new HashMap<String, Object>();
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
    * 通过查询条件获取所有有权访问的服务数据
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
            List< UserService> dataList = service.queryUserServices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryData(UserService model,HttpServletRequest request, HttpServletResponse response, Model m,
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

            List< UserService> dataList = service.queryUserServices(map, pageBound);
            PageList<UserService> pageList = (PageList<UserService>) dataList;

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
    public Object queryUserServiceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String name,@PathVariable String value) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(name,value);
        try {
            List<UserService> models = service.queryUserServices(map);
            map.put("rows",models);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model m) {
        m.addAllAttributes(CommonsUtil.request2map(request));
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_UserService"));
        m.addAttribute("pageName","iot_UserService");
        String page_name="manage";
        if (null!=request.getParameter("pageName")){
            page_name = (String)request.getParameter("pageName");
        }
        return new ModelAndView("/iot/userservice/"+page_name);
    }
}