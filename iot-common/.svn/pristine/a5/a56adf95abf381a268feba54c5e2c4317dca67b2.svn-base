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
import org.totem.iot.model.IotDevice;
import org.totem.iot.service.IotDeviceService;
import org.totem.components.data.json.JsonUtils;
import org.totem.tour.service.TourService;

@Controller
@RequestMapping("/iot/iotdevice")
public class IotDeviceController extends CommonController {
    public static org.apache.log4j.Logger logger = Logger.getLogger(IotDeviceController.class.getName());

    @Autowired
    private IotDeviceService service;

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
    public Object createIotDevice(HttpServletRequest request, HttpServletResponse response, Model m) {
        JSONObject result = new JSONObject();
        try {
            IotDevice model = new IotDevice();
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/query/{id}")
    public Object queryIotDeviceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        JSONObject result = new JSONObject();
        try {
            IotDevice model = service.getById(id);
            if (null==model){
                return createIotDevice(request, response, m);
            }
            result.putAll(BeanHelper.obj2Map(model));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iotService.checkResult(result, "queryId", "IotDevice");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editIotDeviceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String id) {
        Map<String,Object> result = CommonsUtil.request2map(request);

        try {
            if (!StringUtils.equals(id, "create")){
                result.put("deviceId",id);
                result.put("iotDevice",service.getById(id));

            }else{
                result.put("iotDevice",new IotDevice());
                result.remove("deviceId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotDevice_"+result.get("actionType")));
        m.addAttribute("pageName","iot_IotDevice_"+result.get("actionType"));

        m.addAllAttributes(result);
        String actionType = (String)result.get("actionType");
        if (StringUtils.isBlank(actionType)||!StringUtils.equals(actionType,"view")||StringUtils.equals(actionType,"create")||StringUtils.equals(actionType,"auth")){
            actionType = "edit";
        }
        if (null!=result.get("func")){
            actionType += (String)result.get("func");
        }
        return new ModelAndView("/iot/iotdevice/"+actionType);
    }

    /**
    * 保存设备
    */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonVo<IotDevice> updateIotDevice(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotDevice> json = new JsonVo<IotDevice>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        IotDevice model = new IotDevice();
        BeanHelper.copyObjectProperties(model,result);

        try {
            IotDevice pf = updateIotDevice(model,request);
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

    private IotDevice updateIotDevice(IotDevice model, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
        String actionType = "update";
        if (StringUtils.isBlank(model.getDeviceId())){
            actionType = "insert";
        }
        IotDevice pf = service.updateIotDevice(model);

        //保存更新日志
        String actionContent = JsonUtils.obj2json(model);
        commonService.saveActionLog(request,actionType,actionContent ,pf.getDeviceId(),"IotDevice","IOT_DEVICE");

        return pf;
    }

    /**
    * 复制一条设备
    */
    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public JsonVo<IotDevice> copyIotDevice(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotDevice> json = new JsonVo<IotDevice>();
        Map<String,Object> result = CommonsUtil.request2map(request);
        IotDevice model = JsonUtils.json2object(JsonUtils.obj2json(result), IotDevice.class);
        try{
            model.setDeviceId(null);
            IotDevice pf = updateIotDevice(model,request);
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
    * 刪除一条设备
    */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonVo<IotDevice> deleteIotDevice(@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotDevice> json = new JsonVo<IotDevice>();
        try {
            IotDevice model = service.getById(id);
            int i  = service.deleteIotDevice(id);
            commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotDevice","IOT_DEVICE");
            json.setResult(true);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.setResult(false);
            return json;
        }
    }

    /**
    * 按条件刪除设备
    */
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public JsonVo<IotDevice> deleteIotDevices(HttpServletRequest request,HttpServletResponse response,
    ModelMap modelMap) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        JsonVo<IotDevice> json = new JsonVo<IotDevice>();
        Map<String,Object> map = CommonsUtil.request2map(request);
        if (map.size()==1&&(null!=map.get("deviceId")||(null!=map.get("deviceIds")&&((String[])map.get("deviceIds")).length==1))){
            String id = (String)map.get("deviceId");
            if (StringUtils.isBlank(id)){
                id = ((String[])map.get("deviceIds"))[0];
            }
            try {
                IotDevice model = service.getById(id);
                int i  = service.deleteIotDevice(id);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(model),id,"IotDevice","IOT_DEVICE");
                json.setResult(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                json.setResult(false);
                return json;
            }
        }else{
            List<IotDevice> list = service.queryIotDevices(map);
            try {
                int i  = service.deleteIotDevices(map);
                commonService.saveActionLog(request,"delete", JsonUtils.obj2json(list),"","IotDevice","IOT_DEVICE");
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
    * 获取所有设备数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAll")
    public Object queryAllData(IotDevice model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotDevice> dataList = service.queryIotDevices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 获取所有有权访问的设备数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/queryAuthAll")
    public Object queryAuthAllData(IotDevice model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List< IotDevice> dataList = service.queryIotDevices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
    * 导出所有有权访问的设备数据
    *
    **/
    @ResponseBody
    @RequestMapping(value = "/exportAuthAll")
    public Object exportAuthAllDataToExcel(IotDevice model,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = CommonsUtil.request2map(request);
        try {
            List<IotDevice> dataList = service.queryIotDevices(map);
            String filename = "data" + ".xlsx";
            filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            response.setContentType("APPLICATION/DOWNLOAD");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            List<String> titles = new ArrayList<String>();
            List<String> codes = new ArrayList<String>();
            titles.add("设备厂商");
            codes.add("deviceManufa");
            titles.add("设备类型");
            codes.add("deviceType");
            titles.add("设备名");
            codes.add("deviceName");
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (IotDevice iotDevice : dataList) {
                Map<String, Object> row = new HashMap<String, Object>();
                row.put("deviceManufa", iotDevice.getDeviceManufa());
                row.put("deviceType", iotDevice.getDeviceTypeShowLabel());
                row.put("deviceName", iotDevice.getDeviceName());
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
    * 通过查询条件获取所有有权访问的设备数据
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
            List< IotDevice> dataList = service.queryIotDevices(map);
            map.put("rows",dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryData(IotDevice model,HttpServletRequest request, HttpServletResponse response, Model m,
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

            List< IotDevice> dataList = service.queryIotDevices(map, pageBound);
            PageList<IotDevice> pageList = (PageList<IotDevice>) dataList;

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
    public Object queryIotDeviceById(HttpServletRequest request, HttpServletResponse response, Model m,
    @PathVariable String name,@PathVariable String value) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(name,value);
        try {
            List<IotDevice> models = service.queryIotDevices(map);
            map.put("rows",models);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model m) {
        m.addAllAttributes(CommonsUtil.request2map(request));
        m.addAttribute("showTour", tourService.showTour(AuthorityUtil.getLoginUser(request).getUserId(), "iot_IotDevice"));
        m.addAttribute("pageName","iot_IotDevice");
        String page_name="manage";
        if (null!=request.getParameter("pageName")){
            page_name = (String)request.getParameter("pageName");
        }
        return new ModelAndView("/iot/iotdevice/"+page_name);
    }
}