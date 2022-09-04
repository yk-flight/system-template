package com.zrkizzy.template.aspect;

import com.alibaba.fastjson.JSON;
import com.zrkizzy.template.annotation.LogAnnotation;
import com.zrkizzy.template.entity.OperateLog;
import com.zrkizzy.template.enums.StatusEnum;
import com.zrkizzy.template.mapper.OperateLogMapper;
import com.zrkizzy.template.utils.IpUtil;
import com.zrkizzy.template.utils.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.zrkizzy.template.constant.CommonConst.LOCAL_HOST;

/**
 * AOP切面 定义通知和切点的关系
 *
 * @author zhangrongkang
 * @date 2022/9/3
 */
@Aspect
@Component
public class LogAspect {
    @Resource
    private OperateLogMapper operateLogMapper;

    /**
     * 申明切点，从注解位置进行切入
     */
    @Pointcut("@annotation(com.zrkizzy.template.annotation.LogAnnotation))")
    private void pointCut() {}

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 实例化日志记录对象
        OperateLog operateLog = new OperateLog();
        // 当前方法的操作日期
        operateLog.setOperateTime(LocalDateTime.now());
        // 当前方法开始执行的时间
        long beginTime = System.currentTimeMillis();

        // 执行环绕的方法
        Object result = joinPoint.proceed();

        // ===================== 保存日志 =====================
        // 当前方法执行结束的时间
        long time = System.currentTimeMillis() - beginTime;
        // 方法执行时间
        operateLog.setDuration(time);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 执行的方法
        Method method = signature.getMethod();
        // 通过执行的方法得到自定义的注解
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        // 系统模块
        operateLog.setModule(logAnnotation.module());
        // 操作描述
        operateLog.setDescription(logAnnotation.description());
        // 请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        // 操作方法
        operateLog.setOperateMethod(className + "." + methodName);
        // 请求参数
        String params = JSON.toJSONString(joinPoint.getArgs());
        operateLog.setRequestParam(params);

        // 获取请求的 request 和 response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        // 请求方式
        operateLog.setRequestMethod(request.getMethod());
        // 通过request设置IP地址
        String ipAddress = IpUtil.getCurIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        if (LOCAL_HOST.equals(ipAddress)) {
            ipSource = "本地登录";
        }
        // 设置当前操作IP地址
        operateLog.setOperateIp(ipAddress);
        // 设置当前操作的IP属地
        operateLog.setOperateSource(ipSource);
        // 操作状态
        StatusEnum statusEnum = StatusEnum.getStatusEnum(response.getStatus());
        if (statusEnum == null) {
            operateLog.setStatus(false);
            // 操作描述
            operateLog.setStatusDescription("未知错误");
        } else {
            operateLog.setStatus(statusEnum.getResult());
            // 操作描述
            operateLog.setStatusDescription(statusEnum.getDescription());
        }
        // 设置当前操作的用户
        operateLog.setUser(UserUtil.getCurrentUser().getNickName());

        // 将操作日志添加到数据库中
        operateLogMapper.insert(operateLog);
        return result;
    }

}
