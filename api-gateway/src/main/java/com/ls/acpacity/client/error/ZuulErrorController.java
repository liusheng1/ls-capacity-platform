package com.ls.acpacity.client.error;

import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.web.servlet.error.ErrorController;

public class ZuulErrorController  implements ErrorController {

    public static String ERROR_PATH="/error";


    public Object error(){
        RequestContext ctx=RequestContext.getCurrentContext();
        Throwable throwable=ctx.getThrowable();
        return new Object();
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
