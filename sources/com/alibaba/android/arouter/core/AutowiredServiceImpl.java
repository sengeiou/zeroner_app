package com.alibaba.android.arouter.core;

import android.content.Context;
import android.util.LruCache;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.utils.Consts;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/arouter/service/autowired")
public class AutowiredServiceImpl implements AutowiredService {
    private List<String> blackList;
    private LruCache<String, ISyringe> classCache;

    public void init(Context context) {
        this.classCache = new LruCache<>(66);
        this.blackList = new ArrayList();
    }

    public void autowire(Object instance) {
        String className = instance.getClass().getName();
        try {
            if (!this.blackList.contains(className)) {
                ISyringe autowiredHelper = (ISyringe) this.classCache.get(className);
                if (autowiredHelper == null) {
                    autowiredHelper = (ISyringe) Class.forName(instance.getClass().getName() + Consts.SUFFIX_AUTOWIRED).getConstructor(new Class[0]).newInstance(new Object[0]);
                }
                autowiredHelper.inject(instance);
                this.classCache.put(className, autowiredHelper);
            }
        } catch (Exception e) {
            this.blackList.add(className);
        }
    }
}
