package org.totem.iot.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.annotation.JSONField;
/**
* 模型: UserService
* 表名: USER_SERVICE
* 描述:
*/

public class UserService extends org.totem.common.model.BaseModel{
    /**
    * 主键
    */
    private String serviceId;

    /**
    * 服务名
    */
    private String serviceName;

    /**
    * 服务类型
    */
    private String serviceType;

    /**
    * 服务描述
    */
    private String serviceDescription;

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

}