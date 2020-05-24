package com.ls.capacity.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.ls.capacity.common.annotation.AccessLimit;
import com.ls.capacity.common.web.Result;
import com.ls.capacity.redis.util.RedisUtil;
import lombok.AllArgsConstructor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.security.Key;

/** 非网关部分应用次数限制 */
@AllArgsConstructor
@SuppressWarnings("all")
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {
  @Resource private RedisUtil redisUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod hm = (HandlerMethod) handler;
      AccessLimit methodAnnotation = hm.getMethodAnnotation(AccessLimit.class);
      if (methodAnnotation == null) {
        return true;
      }
      int seconds = methodAnnotation.seconds();
      int maxCount = methodAnnotation.maxCount();
      boolean needLogin = methodAnnotation.needLogin();
      String key = request.getRequestURI();
      if (needLogin) {
        // 获取登录用户。。。。。
      } else {
        // do nothing
      }
      if (!redisUtil.hasKey(key) || redisUtil.getExpire(key) <= 0) {
        redisUtil.set(key, 0, seconds);
      }
      if (redisUtil.incr(key,1)>maxCount){
        render(response, Result.failed("访问太频繁！"));
        return false;
      }
    }

    return true;
  }
  private void render(HttpServletResponse response, Result result) throws Exception {
    response.setContentType("application/json;charset=UTF-8");
    OutputStream out = response.getOutputStream();
    String str = JSON.toJSONString(result);
    out.write(str.getBytes("UTF-8"));
    out.flush();
    out.close();
  }
}
