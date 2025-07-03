package com.aurora.day.auroratimerserver.filter;

import com.aurora.day.auroratimerserver.model.Result;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.smartboot.http.common.enums.HttpMethodEnum;

public class LoginFilter  implements Filter {
    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        //预检请求默认通过
        if(ctx.method().equals(HttpMethodEnum.OPTIONS.getMethod())){
            chain.doFilter(ctx);
            return;
        }
        if(TokenUtil.Verify(ctx)){
            chain.doFilter(ctx);
        }else {
            ctx.status(401);
            ctx.render(Result.fail("token验证失败或权限不足"));
        }
    }
}
