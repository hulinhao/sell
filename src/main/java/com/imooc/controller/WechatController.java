package com.imooc.controller;

import com.imooc.Exception.SellException;
import com.imooc.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/wechat/")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @GetMapping("authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置
        //2.调用方法
        String url = "http://173ca97752.51mypc.cn:54884/sell/wechat/userInfo";
       String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO,returnUrl);
       log.info("【微信网页授权】获取code,result={}",redirectUrl);
       return "redirect:" + redirectUrl;
    }

    @GetMapping("userInfo")
    public String userInfo(@RequestParam("code")String code,
                         @RequestParam("state")String returnUrl){
        log.info("code={}",code);
        log.info("state={}",returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try{//*******https://github.com/Wechat-Group/weixin-java-tools******//
            //获得access token
            //wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(code);

            wxMpOAuth2AccessToken.setOpenId("oyJni09XGI1m1MfA4dcF8MDRwqXI");
            wxMpOAuth2AccessToken.setAccessToken("10_5CYjk15xD6C7UaJLd1J6pVi56RWDxfj1dc5XvoUx52oYt0u1rKmwuR_Q64ndE-v0u22p_XsN5VQ6ZUFcSJvsumU3Y4BecbSzZiEYrLbpCmA");
            wxMpOAuth2AccessToken.setExpiresIn(7200);
            wxMpOAuth2AccessToken.setScope("snsapi_userinfo");
            wxMpOAuth2AccessToken.setRefreshToken("10_zH5KGKJXqHNF3K7VMEtmpuHftEnPLo0eFREMpVmJ8EINDlM_owch_FT4Gz2B9vfMdCxk2SwiwNaKlCWnZf63sEHfYDhFvnVRYp--Zxf294k");
            //获取用户基本信息
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            //刷新access token
            wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
            //验证access token
            boolean valid = wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken);

            //wxMpOAuth2AccessToken.setRefreshToken("oyJni09XGI1m1MfA4dcF8MDRwqXI");
        }catch (WxErrorException e){
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权-获取openId】openId = {}",openId);
        return "redirect:" + returnUrl+"?openid="+openId;

    }
}
