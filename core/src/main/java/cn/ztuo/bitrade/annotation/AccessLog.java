package cn.ztuo.bitrade.annotation;


import java.lang.annotation.*;

import cn.ztuo.bitrade.constant.AdminModule;

@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLog {
    String operation();
    AdminModule module();
}

