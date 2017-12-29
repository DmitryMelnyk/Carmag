package com.carpedia.carmagazine.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by d264 on 12/26/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}
