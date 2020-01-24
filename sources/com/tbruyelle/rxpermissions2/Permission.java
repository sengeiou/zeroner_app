package com.tbruyelle.rxpermissions2;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.List;

public class Permission {
    public final boolean granted;
    public final String name;
    public final boolean shouldShowRequestPermissionRationale;

    public Permission(String name2, boolean granted2) {
        this(name2, granted2, false);
    }

    public Permission(String name2, boolean granted2, boolean shouldShowRequestPermissionRationale2) {
        this.name = name2;
        this.granted = granted2;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale2;
    }

    public Permission(List<Permission> permissions) {
        this.name = combineName(permissions);
        this.granted = combineGranted(permissions).booleanValue();
        this.shouldShowRequestPermissionRationale = combineShouldShowRequestPermissionRationale(permissions).booleanValue();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;
        if (this.granted == that.granted && this.shouldShowRequestPermissionRationale == that.shouldShowRequestPermissionRationale) {
            return this.name.equals(that.name);
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 1;
        int hashCode = this.name.hashCode() * 31;
        if (this.granted) {
            i = 1;
        } else {
            i = 0;
        }
        int i3 = (hashCode + i) * 31;
        if (!this.shouldShowRequestPermissionRationale) {
            i2 = 0;
        }
        return i3 + i2;
    }

    public String toString() {
        return "Permission{name='" + this.name + '\'' + ", granted=" + this.granted + ", shouldShowRequestPermissionRationale=" + this.shouldShowRequestPermissionRationale + '}';
    }

    private String combineName(List<Permission> permissions) {
        return ((StringBuilder) Observable.fromIterable(permissions).map(new Function<Permission, String>() {
            public String apply(Permission permission) throws Exception {
                return permission.name;
            }
        }).collectInto(new StringBuilder(), new BiConsumer<StringBuilder, String>() {
            public void accept(StringBuilder s, String s2) throws Exception {
                if (s.length() == 0) {
                    s.append(s2);
                } else {
                    s.append(", ").append(s2);
                }
            }
        }).blockingGet()).toString();
    }

    private Boolean combineGranted(List<Permission> permissions) {
        return (Boolean) Observable.fromIterable(permissions).all(new Predicate<Permission>() {
            public boolean test(Permission permission) throws Exception {
                return permission.granted;
            }
        }).blockingGet();
    }

    private Boolean combineShouldShowRequestPermissionRationale(List<Permission> permissions) {
        return (Boolean) Observable.fromIterable(permissions).any(new Predicate<Permission>() {
            public boolean test(Permission permission) throws Exception {
                return permission.shouldShowRequestPermissionRationale;
            }
        }).blockingGet();
    }
}
