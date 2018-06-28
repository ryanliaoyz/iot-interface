package org.totem.iot.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.totem.iot.model.UserService;
import org.totem.iot.model.IotDevice;
import com.alibaba.fastjson.annotation.JSONField;
/**
* 模型: IotUser
* 表名: IOT_USER
* 描述:
*/

public class IotUser extends org.totem.common.model.BaseModel{
    /**
    * 主键
    */
    private String iotUserId;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String userPassword;

    /**
    * 密钥
    */
    private String userToken;

    /**
    * 邮箱
    */
    private String userEmail;

    /**
    * 显示名
    */
    private String userDisplayName;

    /**
    * 激活
    */
    private String userValid;

    /**
    * 注册日期
    */
    private String userDate;

    /**
    * 余额
    */
    private String userBalance;

    /**
    *  SERVICE_ID的子成员服务列表
    */
    private List<UserService> userServiceServiceIdChilds;
    /**
    *  DEVICE_BELONGING的子成员设备列表
    */
    private List<IotDevice> iotDeviceDeviceBelongingChilds;

    public void setIotUserId(String iotUserId) {
        this.iotUserId = iotUserId;
    }

    public String getIotUserId() {
        return iotUserId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserValid(String userValid) {
        this.userValid = userValid;
    }

    public String getUserValid() {
        return userValid;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public List<UserService> getUserServiceServiceIdChilds(){
        return userServiceServiceIdChilds;
    }
    public void setUserServiceServiceIdChilds(List<UserService> userServices){
        this.userServiceServiceIdChilds = userServices;
    }
    public List<IotDevice> getIotDeviceDeviceBelongingChilds(){
        return iotDeviceDeviceBelongingChilds;
    }
    public void setIotDeviceDeviceBelongingChilds(List<IotDevice> iotDevices){
        this.iotDeviceDeviceBelongingChilds = iotDevices;
    }

}