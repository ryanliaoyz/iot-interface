package org.totem.iot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("iotUtil")
public class CommonsUtil extends org.totem.common.utils.CommonsUtil{
    public static String REG_URL;

    public static String REG_MAIL;

    @Value("${REG_URL:http://reg.com}")
    public void setRegUrl(String regUrl) {
        REG_URL = regUrl;
    }

    @Value("${REG_MAIL:reg@mail.com}")
    public void setRegMail(String regMail) {
        REG_MAIL = regMail;
    }

    public static void main(String[] arg){}

}
