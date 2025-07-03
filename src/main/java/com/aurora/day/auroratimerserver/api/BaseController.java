package com.aurora.day.auroratimerserver.api;

import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.Context;
import org.noear.solon.validation.annotation.Valid;
import org.noear.solon.web.cors.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@CrossOrigin
@Valid
public abstract class BaseController {

    protected static Logger log = LoggerFactory.getLogger("Controller");

    public String getCurrentUserId(){
        return TokenUtil.getId(Context.current());
    }

}
