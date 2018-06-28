package org.totem.iot.action;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.mail.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.totem.components.cryptor.aes.DecryptException;
import org.totem.components.cryptor.aes.Decryptor;
import org.totem.components.cryptor.aes.EncryptException;
import org.totem.components.cryptor.aes.Encryptor;
import org.totem.components.data.UUIDUtils;
import org.totem.components.mail.MailService;
import org.totem.components.mail.domain.MailException;
import org.totem.iot.model.IotUser;
import org.totem.iot.service.IotService;
import org.totem.iot.service.IotUserService;
import org.totem.iot.utils.CommonsUtil;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.totem.iot.utils.CommonsUtil.REG_MAIL;
import static org.totem.iot.utils.CommonsUtil.REG_URL;


@Controller
@RequestMapping("/iot")
public class IotController {


    private final static String encodeKey = "2l1n34592j4512mnl0sddfasd89-345l,m32n538u525ln234523";

    @Autowired
    private IotService iotService;
    @Autowired
    private IotUserService iotUserService;
    @Autowired
    private MailService mailService;

    @ResponseBody
    @RequestMapping(value = "/iot")
    public Object iot(HttpServletRequest request, HttpServletResponse response, Model m) {
        return "OK";
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "userlogin")
    public ModelAndView userLogin(HttpServletRequest request, HttpServletResponse response, Model m) {
        return new ModelAndView("userlogin");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "usersignup")
    public ModelAndView usersignup(HttpServletRequest request, HttpServletResponse response, Model m) {
        return new ModelAndView("usersignup");
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/usersignupsuc")
    public ModelAndView usersignupsuc(HttpServletRequest request, HttpServletResponse response, Model m) {
        return new ModelAndView("usersignupsuc");
    }


    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/signup")
    public ModelAndView signup(HttpServletRequest request, HttpServletResponse response, Model m) {
        Map<String, Object> map = CommonsUtil.request2map(request);
        m.addAllAttributes(map);
        Map<String, Object> map2 = new HashMap<>();
        String userName = (String) map.get("userName");
        map2.put("userName", userName);
        map2.put("userValid", "");
        if (StringUtils.isNotBlank(userName)) {
            List<IotUser> dataList = iotUserService.queryIotUsers(map2);
            if (!dataList.isEmpty()) {
                m.addAttribute("userNameError", "The Username already exist.");
                return new ModelAndView("usersignup");
            }
        }

        map2.remove("userName");
        String userEmail = (String) map.get("userEmail");
        map2.put("userEmail", userEmail);
        if (StringUtils.isNotBlank(userEmail)) {
            List<IotUser> dataList = iotUserService.queryIotUsers(map2);
            if (!dataList.isEmpty()) {
                m.addAttribute("userEmailError", "The e-mail address already exist.");
                return new ModelAndView("usersignup");
            }
        } else {
            m.addAttribute("userEmailError", "");
            return new ModelAndView("usersignup");
        }
        String userPassword = (String) map.get("userPassword");

        IotUser newUser = new IotUser();
        newUser.setUserName(userName);
        newUser.setUserEmail(userEmail);
        newUser.setUserPassword(userPassword);
        newUser.setUserValid("0");
        iotUserService.updateIotUser(newUser);
        //send email
        org.totem.components.mail.domain.MailMessage message = new org.totem.components.mail.domain.MailMessage();
        InternetAddress from = new InternetAddress();
        from.setAddress(REG_MAIL);
        try {
            from.setPersonal("Account activation");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        message.setFrom(from);
        message.setSubject("Account activation");
        if (org.apache.commons.lang3.StringUtils.isNotBlank(message.getSubject())) {
            Encryptor encryptor = new Encryptor();
            try {
                String id = encryptor.encryptString2Base64(encodeKey, newUser.getIotUserId());
                String href = "<br><br><a href='" + REG_URL + id + "?actionType=active'>" + "click to activate" + "</a>";
                message.setContent("click the click below to activate the account:" + href);
                InternetAddress email = new InternetAddress();
                email.setAddress(userEmail);
                email.setPersonal(newUser.getUserName());
                InternetAddress[] addresses = {email};
                message.setTo(addresses);
                mailService.sendMail(message);
            } catch (MailException e) {
                e.printStackTrace();
            } catch (EncryptException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        m.addAttribute("userEmailRequest", userEmail);
        return new ModelAndView("signupSuc");
    }


    @ResponseBody
    @RequestMapping(value = "/resetpassword")
    public ModelAndView resetpassword(HttpServletRequest request, HttpServletResponse response, Model m) {
        Map<String, Object> map = CommonsUtil.request2map(request);
        Map<String, Object> map2 = new HashMap<>();

        String userEmail = (String) map.get("userEmail");

        map2.put("userEmail", userEmail);
        map2.put("userValid", "");
        if (StringUtils.isNotBlank(userEmail)) {
            List<IotUser> dataList = iotUserService.queryIotUsers(map2);
            if (!dataList.isEmpty()) {
                if (StringUtils.equals(dataList.get(0).getUserValid(), "0")) {
                    m.addAttribute("resetPassError", "The accound have not been activated yet, an email has been sent to the email above provided.");
                    //send email
                    return new ModelAndView("/resetpassword");
                } else {
                    m.addAttribute("resetPassError", "Am e-mail with the link to revise password has been sent to the addresss provided.");
                    String newPassword = UUIDUtils.getShortUuid();
                    dataList.get(0).setUserPassword(newPassword);
                    iotUserService.updateIotUser(dataList.get(0));
                    String content = "You password is: " + newPassword + " <br> please reset your password at the earliest convenience";
                    //send email
                    org.totem.components.mail.domain.MailMessage message = new org.totem.components.mail.domain.MailMessage();
                    InternetAddress from = new InternetAddress();
                    from.setAddress(REG_MAIL);
                    try {
                        from.setPersonal("Password Recovery");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    message.setFrom(from);
                    message.setSubject("Password Recovery");
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(message.getSubject())) {
                        Encryptor encryptor = new Encryptor();
                        try {
                            String id = encryptor.encryptString2Base64(encodeKey, dataList.get(0).getIotUserId());
//                            String href = "<br><br><a href='"+REG_URL+id+"?actionType=active'>"+map.get("href")+"</a>";
                            message.setContent(content);
                            InternetAddress email = new InternetAddress();
                            email.setAddress(userEmail);
                            email.setPersonal(dataList.get(0).getUserName());
                            InternetAddress[] addresses = {email};
                            message.setTo(addresses);
                            mailService.sendMail(message);
                        } catch (MailException e) {
                            e.printStackTrace();
                        } catch (EncryptException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                m.addAttribute("resetPassError", "The account doesn't exist.");

            }
        }

        return new ModelAndView("/resetpassword");
    }


    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, Model m) {
        Map<String, Object> map = CommonsUtil.request2map(request);
        try {
            List<IotUser> dataList = iotUserService.queryIotUsers(map);
            if (dataList.size() == 1) {
                response.sendRedirect("/home");
            } else {
                response.sendRedirect("userlogin");
            }
            map.put("rows", dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @RequestMapping(value = "/activation/{randomUrl}")
    public ModelAndView activation(HttpServletRequest request, HttpServletResponse response, Model m, @PathVariable String randomUrl) {
        Decryptor decryptor = new Decryptor();

        try {
            String userId = decryptor.decryptBase642String(encodeKey,randomUrl);
            IotUser theUser = iotUserService.getById(userId);
            if (null != theUser) {
                if (StringUtils.equals(theUser.getUserValid(), "0")) {
                    theUser.setUserValid("1");
                    iotUserService.updateIotUser(theUser);
                    m.addAttribute("activationMes", "the account is activated");
                }
                else{
                    m.addAttribute("activationMes", "the account has already been activated");
                }
            }
            else{

                m.addAttribute("activationMes", "the account is not valid.");

            }
        } catch (DecryptException e) {
            e.printStackTrace();
        }

        return new ModelAndView("activation");
    }
}