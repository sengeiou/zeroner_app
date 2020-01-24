package com.iwown.device_module.common.Bluetooth.sync.proto;

import com.alibaba.fastjson.JSON;
import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.PropertyPreFilter;
import com.alibaba.json.serializer.SerializerFeature;
import com.iwown.device_module.common.sql.File_protobuf_80data;
import com.iwown.device_module.common.sql.File_protobuf_80data.HRV;
import com.iwown.device_module.common.sql.File_protobuf_80data.HeartRate;
import com.iwown.device_module.common.sql.File_protobuf_80data.Pedo;
import com.iwown.device_module.common.sql.File_protobuf_80data.Sleep;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ComplexPropertyPreFilter implements PropertyPreFilter {
    private Map<Class<?>, String[]> excludes = new HashMap();
    private Map<Class<?>, String[]> includes = new HashMap();

    static {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.mask;
    }

    public ComplexPropertyPreFilter() {
    }

    public ComplexPropertyPreFilter(Map<Class<?>, String[]> includes2) {
        this.includes = includes2;
    }

    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }
        Class<?> clazz = source.getClass();
        for (Entry<Class<?>, String[]> item : this.excludes.entrySet()) {
            if (((Class) item.getKey()).isAssignableFrom(clazz) && isHave((String[]) item.getValue(), name)) {
                return isFilter(name, source);
            }
        }
        if (this.includes.isEmpty()) {
            return true;
        }
        for (Entry<Class<?>, String[]> item2 : this.includes.entrySet()) {
            if (((Class) item2.getKey()).isAssignableFrom(clazz) && isHave((String[]) item2.getValue(), name)) {
                return isFilter(name, source);
            }
        }
        return false;
    }

    public static boolean isHave(String[] str, String s) {
        for (String i : str) {
            if (i.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFilter(String name, Object value) {
        try {
            if (value instanceof Sleep) {
                for (Field field : Class.forName(value.getClass().getName()).getDeclaredFields()) {
                    if (field.getName().equals(name)) {
                        if ("c".equalsIgnoreCase(name) && ((Sleep) value).getC() == 0) {
                            return false;
                        }
                        if ("s".equalsIgnoreCase(name) && ((Sleep) value).getS() == 0) {
                            return false;
                        }
                        if ("a".equalsIgnoreCase(name) && ((Sleep) value).getA().length == 0) {
                            return false;
                        }
                    }
                }
            } else if (value instanceof HeartRate) {
                for (Field field2 : Class.forName(value.getClass().getName()).getDeclaredFields()) {
                    if (field2.getName().equals(name)) {
                        if ("a".equalsIgnoreCase(name) && ((HeartRate) value).getA() == 0) {
                            return false;
                        }
                        if ("n".equalsIgnoreCase(name) && ((HeartRate) value).getN() == 0) {
                            return false;
                        }
                        if ("x".equalsIgnoreCase(name) && ((HeartRate) value).getX() == 0) {
                            return false;
                        }
                    }
                }
            } else if (value instanceof Pedo) {
                for (Field field3 : Class.forName(value.getClass().getName()).getDeclaredFields()) {
                    if (field3.getName().equals(name)) {
                        if ("a".equalsIgnoreCase(name) && ((Pedo) value).getA() == 0) {
                            return false;
                        }
                        if ("c".equalsIgnoreCase(name) && ((Pedo) value).getC() == 0.0f) {
                            return false;
                        }
                        if ("d".equalsIgnoreCase(name) && ((Pedo) value).getD() == 0) {
                            return false;
                        }
                        if ("s".equalsIgnoreCase(name) && ((Pedo) value).getS() == 0) {
                            return false;
                        }
                        if ("t".equalsIgnoreCase(name) && ((Pedo) value).getT() == 0) {
                            return false;
                        }
                    }
                }
            } else if (value instanceof HRV) {
                for (Field field4 : Class.forName(value.getClass().getName()).getDeclaredFields()) {
                    if (field4.getName().equals(name)) {
                        if ("f".equalsIgnoreCase(name) && ((HRV) value).getF() == 0.0f) {
                            return false;
                        }
                        if ("m".equalsIgnoreCase(name) && ((HRV) value).getM() == 0.0f) {
                            return false;
                        }
                        if ("p".equalsIgnoreCase(name) && ((HRV) value).getP() == 0.0f) {
                            return false;
                        }
                        if ("r".equalsIgnoreCase(name) && ((HRV) value).getR() == 0.0f) {
                            return false;
                        }
                        if ("s".equalsIgnoreCase(name) && ((HRV) value).getS() == 0.0f) {
                            return false;
                        }
                    }
                }
            } else if (value instanceof File_protobuf_80data) {
                for (Field field5 : Class.forName(value.getClass().getName()).getDeclaredFields()) {
                    if (field5.getName().equals(name)) {
                        if ("v".equalsIgnoreCase(name)) {
                            HRV hrv = ((File_protobuf_80data) value).getV();
                            if (hrv.getF() == 0.0f && hrv.getM() == 0.0f && hrv.getP() == 0.0f && hrv.getR() == 0.0f && hrv.getS() == 0.0f) {
                                return false;
                            }
                        }
                        if ("p".equalsIgnoreCase(name)) {
                            Pedo pedo = ((File_protobuf_80data) value).getP();
                            if (pedo.getA() == 0 && pedo.getC() == 0.0f && pedo.getD() == 0 && pedo.getS() == 0 && pedo.getT() == 0) {
                                return false;
                            }
                        }
                        if ("h".equalsIgnoreCase(name)) {
                            HeartRate heartRate = ((File_protobuf_80data) value).getH();
                            if (heartRate.getA() == 0 && heartRate.getN() == 0 && heartRate.getX() == 0) {
                                return false;
                            }
                        }
                        if ("e".equalsIgnoreCase(name)) {
                            Sleep sleep = ((File_protobuf_80data) value).getE();
                            if (sleep.getA().length == 0 && sleep.getC() == 0 && sleep.getS() == 0) {
                                return false;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                return true;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public Map<Class<?>, String[]> getIncludes() {
        return this.includes;
    }

    public void setIncludes(Map<Class<?>, String[]> includes2) {
        this.includes = includes2;
    }

    public Map<Class<?>, String[]> getExcludes() {
        return this.excludes;
    }

    public void setExcludes(Map<Class<?>, String[]> excludes2) {
        this.excludes = excludes2;
    }
}
