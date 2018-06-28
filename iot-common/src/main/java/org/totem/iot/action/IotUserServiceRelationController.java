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
import org.totem.iot.model.IotUserServiceRelation;
import org.totem.iot.service.IotUserServiceRelationService;
import org.totem.components.data.json.JsonUtils;
import org.totem.tour.service.TourService;

@Controller
@RequestMapping("/iot/iotuserservicerelation")
public class IotUserServiceRelationController extends CommonController {
    public static org.apache.log4j.Logger logger = Logger.getLogger(IotUserServiceRelationController.class.getName());

    @Autowired
    private IotUserServiceRelationService service;

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
    public Object createIotUserServiceRelation(HttpServletRequest request, HttpServletResponse response, Model m) {
        JSONObject result = new JSONObject();
        try {
            IotUserServiceRelation model = new IotUserServiceRelation();
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/query/{id}")
    public Object queryIotUserServiceRelationById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        JSONObject result = new JSONObject();
        try {
            IotUserServiceRelation model = service.getById(id);
            if (null==model){
                return createIotUserServiceRelation(request, response, m);
            }
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iotService.checkResult(result, "queryId", "IotUserServiceRelation");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editIotUserServiceRelationById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        Map<String,Object> result = CommonsUtil.request2map(request);

        try {
            if (!StringUtils.equals(id, "create")){
                result.put("iotUserServiceRelationId",id);
                result.put("iotUserServiceRelation",service.getById(id));

            }else{
                result.put("iotUserServiceRelation",new IotUserServiceRelation());
                result.remove("iotUserServiceRelationId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotUserServiceRelation_"+result.get("actionType")));
        m.addAttribute("pageName","iot_IotUserServiceRelation_"+result.get("actionType"));

        m.addAllAttributes(result);
        String actionType = (String)result.get("actionType");
        if (StringUtils.isBlank(actionType)||!StringUtils.equals(actionType,"view")||StringUtils.equals(actionType,"create")||StringUtils.equals(actionType,"auth")){
            actionType = "edit";
        }
        if (null!=result.get("func")){
            actionType += (String)result.get("func");
        }
        return new ModelAndView("/iot/iotuserservicerelation/"+actionType);
    }

    /**
    * 保存用户服务关系
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonVo<IotUserServiceRelation> updateIotUserServiceRelation(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUserServiceRelation> json = new JsonVo<IotUserServiceRelation>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        IotUserServiceRelation model = new IotUserServiceRelation();
        BeanHelper.copyObjectProperties(model,result);

        try {
            IotUserServiceRelation pf = updateIotUserServiceRelation(model,request);
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

    private IotUserServiceRelation updateIotUserServiceRelation(IotUserServiceRelation model, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
        String actionType = "update";
        if (StringUtils.isBlank(model.getIotUserServiceRelationId())){
            actionType = "insert";
        }
        IotUserServiceRelation pf = service.updateIotUserServiceRelation(model);

        //保存更新日志
        String actionContent = JsonUtils.obj2json(model);
        commonService.saveActionLog(request,actionType,actionContent ,pf.getIotUserServiceRelationId(),"IotUserServiceRelation","IOT_USER_SERVICE_RELATION");

        return pf;
    }

    /**
    * 复制一条用户服务关系
    */
    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public JsonVo<IotUserServiceRelation> copyIotUserServiceRelation(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUserServiceRelation> json = new JsonVo<IotUserServiceRelation>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        IotUserServiceRelation model = JsonUtils.json2object(JsonUtils.obj2json(result), IotUserServiceRelation.class);
        try{
            model.setIotUserServiceRelationId(null);
            IotUserServiceRelation pf = updateIotUserServiceRelation(model,request);
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
    * 刪除一条用户服务关系
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonVo<IotUserServiceRelation> deleteIotUserServiceRelation(@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUserServiceRelation> json = new JsonVo<IotUserServiceRelation>();
        try {
            IotUserServiceRelation model = service.getById(id);
            int i  = service.deleteIotUserServiceRelation(id);
            commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotUserServiceRelation","IOT_USER_SERVICE_RELATION");
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    /**
    * 按条件刪除用户服务关系
    */
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public JsonVo<IotUserServiceRelation> deleteIotUserServiceRelations(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotUserServiceRelation> json = new JsonVo<IotUserServiceRelation>();
        Map<String,Object> map = CommonsUtil.request2map(request);
        if (map.size()==1&&(null!=map.get("iotUserServiceRelationId")||(null!=map.get("iotUserServiceRelationIds")&&((String[])map.get("iotUserServiceRelationIds")).length==1))){
            String id = (String)map.get("iotUserServiceRelationId");
            if (StringUtils.isBlank(id)){
                id = ((String[])map.get("iotUserServiceRelationIds"))[0];
            }
            try {
                IotUserServiceRelation model = service.getById(id);
                int i  = service.deleteIotUserServiceRelation(id);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotUserServiceRelation","IOT_USER_SERVICE_RELATION");
                json.setResult(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                json.setResult(false);
                return json;
            }
        }else{
            List<IotUserServiceRelation> list = service.queryIotUserServiceRelations(map);
            try {
                int i  = service.deleteIotUserServiceRelations(map);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(list),"","IotUserServiceRelation","IOT_USER_SERVICE_RELATION");
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
    * 获取所有用户服务关系数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAll")
    public Object queryAllData(IotUserServiceRelation model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotUserServiceRelation> dataList = service.queryIotUserServiceRelations(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 获取所有有权访问的用户服务关系数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAuthAll")
    public Object queryAuthAllData(IotUserServiceRelation model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotUserServiceRelation> dataList = service.queryIotUserServiceRelations(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 导出所有有权访问的用户服务关系数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/exportAuthAll")
    public Object exportAuthAllDataToExcel(IotUserServiceRelation model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List<IotUserServiceRelation> dataList = service.queryIotUserServiceRelations(map);
            String filename = "data" + ".xlsx";
            filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            response.setContentType("APPLICATION/DOWNLOAD");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            List<String> titles = new ArrayList<String>();
            List<String> codes = new ArrayList<String>();
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (IotUserServiceRelation iotUserServiceRelation : dataList) {
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
    * 通过查询条件获取所有有权访问的用户服务关系数据
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
            List< IotUserServiceRelation> dataList = service.queryIotUserServiceRelations(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryData(IotUserServiceRelation model,HttpServletRequest request, HttpServletResponse response, Model m,
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

            List< IotUserServiceRelation> dataList = service.queryIotUserServiceRelations(map, pageBound);
            PageList<IotUserServiceRelation> pageList = (PageList<IotUserServiceRelation>) dataList;

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
    public Object queryIotUserServiceRelationById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String name,@PathVariable String value) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(name,value);
        try {
            List<IotUserServiceRelation> models = service.queryIotUserServiceRelations(map);
            map.put("rows",models);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model m) {
        m.addAllAttributes(CommonsUtil.request2map(request));
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotUserServiceRelation"));
        m.addAttribute("pageName","iot_IotUserServiceRelation");
        String page_name="manage";
        if (null!=request.getParameter("pageName")){
            page_name = (String)request.getParameter("pageName");
        }
        return new ModelAndView("/iot/iotuserservicerelation/"+page_name);
    }
}