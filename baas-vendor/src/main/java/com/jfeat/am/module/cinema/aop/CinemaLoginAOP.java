package com.jfeat.am.module.cinema.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserStatus;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.AdvertiserMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.org.services.persistence.dao.SysUserMapper;
import com.jfeat.org.services.persistence.model.PasswordStatus;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
public class CinemaLoginAOP {

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    AdvertiserMapper advertiserMapper;

    private Logger log = Logger.getLogger(this.getClass());
    //指定切入点表达式，拦截那些方法，即为那些类生成代理对象
    //@Pointcut("execution(* com.bie.aop.UserDao.save(..))")  ..代表所有参数
    //@Pointcut("execution(* com.bie.aop.UserDao.*())")  指定所有的方法
    //@Pointcut("execution(* com.bie.aop.UserDao.save())") 指定save方法

    @Pointcut("execution(* com.jfeat.am.uaas.system.api.SysUserEndpoint.login(..))")
    public void cinemaLoginPointCut(){

    }

    @Pointcut("execution(* com.jfeat.am.uaas.system.api.SysUserEndpoint.reset(..))")
    public void resetPasswordPointCut(){

    }


    @After("cinemaLoginPointCut()")
    public void close(JoinPoint joinPoint){

    }

    @Around("cinemaLoginPointCut()")
    public Tip login(ProceedingJoinPoint joinPoint) throws Throwable {

        //先执行原先登录方法
        Object responObject = joinPoint.proceed();

        //获得参数
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        String s = JSONObject.toJSONString(arg);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String account = jsonObject.get("account").toString();

        String responseString = JSON.toJSONString(responObject);
        JSONObject responseJSON = JSONObject.parseObject(responseString);
        JSONObject data = (JSONObject)responseJSON.get("data");
        Long orgId = data.getLong("orgId");
        Advertiser advertiser = advertiserMapper.selectOne(new QueryWrapper<Advertiser>().eq("org_id", orgId));
        if(advertiser!=null){
            //返回广告主状态
            data.put("status",advertiser.getStatus());
        }else{
            //视为其他用户 则传递PASS
            data.put("status",AdvertiserStatus.PASS);
        }


        return SuccessTip.create(data);
    }


}
