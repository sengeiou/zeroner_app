package com.youzan.sdk.web.bridge;

import android.text.TextUtils;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: Entrance */
abstract class c {

    /* renamed from: ˊ reason: contains not printable characters */
    static final String f377 = "androidJS";

    /* renamed from: ˋ reason: contains not printable characters */
    private static final int f378 = 5;

    /* renamed from: ˎ reason: contains not printable characters */
    private HashSet<String> f379 = new HashSet<>(5);

    /* renamed from: ˏ reason: contains not printable characters */
    private String f380;

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public abstract String m149();

    /* access modifiers changed from: protected */
    /* renamed from: ˋ reason: contains not printable characters */
    public abstract void m150();

    c() {
    }

    /* renamed from: ˏ reason: contains not printable characters */
    private String m146() {
        return this.f380;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private void m145(String entrance) {
        if (!TextUtils.isEmpty(entrance)) {
            this.f380 = entrance;
            return;
        }
        throw new IllegalArgumentException("Js Bridge Entrance Cannot Be Null");
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: ˊ reason: contains not printable characters */
    public c m148(String entrance) {
        if (entrance != null) {
            this.f379.add(entrance);
        }
        return this;
    }

    /* renamed from: ᐝ reason: contains not printable characters */
    private String m147() {
        StringBuilder content = new StringBuilder(5);
        if (this.f379 != null) {
            int index = 0;
            Iterator it = this.f379.iterator();
            while (it.hasNext()) {
                String item = (String) it.next();
                if (item != null) {
                    content.append(String.format(index == 0 ? "a.%s" : " = a.%s", new Object[]{item}));
                    index++;
                }
            }
        }
        return content.toString();
    }

    /* renamed from: ʻ reason: contains not printable characters */
    private HashSet<String> m142() {
        return this.f379;
    }

    /* renamed from: ˎ reason: contains not printable characters */
    public String m151() {
        m143();
        return m144();
    }

    /* renamed from: ʼ reason: contains not printable characters */
    private void m143() {
        m145(m149());
        m150();
    }

    /* renamed from: ʽ reason: contains not printable characters */
    private String m144() {
        if (TextUtils.isEmpty(m147())) {
            throw new IllegalArgumentException("Js Bridge Interfaces Cannot Be Null");
        } else if (m146() == null) {
            throw new IllegalArgumentException("Js Bridge Entrance Cannot Be Null");
        } else {
            StringBuilder builder = new StringBuilder(1000);
            builder.append("javascript:(function(b){console.log(\"androidJS begin\");var a={queue:[],callback:function(){var d=Array.prototype.slice.call(arguments,0);var c=d.shift();var e=d.shift();this.queue[c].apply(this,d);if(!e){delete this.queue[c]}}};");
            builder.append(m147());
            builder.append("=function(){var f=Array.prototype.slice.call(arguments,0);if(f.length<1){throw\"miss method name\"}var e=[];for(var h=1;h<f.length;h++){var c=f[h];var j=typeof c;e[e.length]=j;if(j==\"function\"){var d=a.queue.length;a.queue[d]=c;f[h]=d}}var g=JSON.parse(prompt(JSON.stringify({method:f.shift(),types:e,args:f})));return g.result};Object.getOwnPropertyNames(a).forEach(function(d){var c=a[d];if(typeof c===\"function\"&&d!==\"callback\"){a[d]=function(){return c.apply(a,[d].concat(Array.prototype.slice.call(arguments,0)))}}});b.");
            builder.append(m146());
            builder.append("=a;console.log(\"androidJS end\");})(window);");
            return builder.toString();
        }
    }

    public String toString() {
        return m151();
    }
}
