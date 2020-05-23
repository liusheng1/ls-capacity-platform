package com.ls.capacity.common.filter;

import com.ls.capacity.common.constant.TraceConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TraceContextFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      javax.servlet.FilterChain filterChain)
      throws ServletException, IOException {
    // 请求头传入存在以请求头传入的为准，不然以X-B3-TraceId为
    String app_trace_id =
        StringUtils.defaultString(
            request.getHeader(TraceConstant.HTTP_HEADER_TRACE_ID),
            MDC.get(TraceConstant.LOG_B3_TRACEID));
    // 未经过HandlerInterceptor的设置
    if (StringUtils.isBlank(MDC.get(TraceConstant.LOG_TRACE_ID))) {
      // 但是有请求头，重新设置
      if (StringUtils.isNotEmpty(app_trace_id)) {
        MDC.put(TraceConstant.LOG_TRACE_ID, app_trace_id);
      }
      filterChain.doFilter(request, response);
    }
  }
}
