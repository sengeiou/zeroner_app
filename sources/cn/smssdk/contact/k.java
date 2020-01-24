package cn.smssdk.contact;

import android.os.Handler.Callback;
import android.os.Message;
import android.widget.Toast;
import com.mob.tools.utils.DeviceHelper;

class k implements Callback {
    final /* synthetic */ d a;

    k(d dVar) {
        this.a = dVar;
    }

    public boolean handleMessage(Message message) {
        String str;
        if (message.what == 1) {
            if ("zh".equals(DeviceHelper.getInstance(this.a.c).getOSLanguage())) {
                str = String.valueOf(new char[]{24212, 29992, 26080, 26435, 38480, 35835, 21462, 36890, 35759, 24405});
            } else {
                str = "no permission to read contacts";
            }
            Toast.makeText(this.a.c, str, 0).show();
        }
        return false;
    }
}
