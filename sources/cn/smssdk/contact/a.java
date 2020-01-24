package cn.smssdk.contact;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.common.net.HttpHeaders;
import com.mob.tools.FakeActivity;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.util.ArrayList;
import java.util.HashMap;

public class a extends FakeActivity implements OnClickListener {
    private static a a;
    private ArrayList<Runnable> b = new ArrayList<>();
    private ArrayList<Runnable> c = new ArrayList<>();
    private TextView d;
    private TextView e;
    private HashMap<String, Object> f = new HashMap<>();

    public a() {
        a = this;
        this.f.put("okActions", this.b);
        this.f.put("cancelActions", this.c);
        setResult(this.f);
    }

    public static boolean a() {
        return a != null;
    }

    public static void a(Runnable runnable, Runnable runnable2) {
        a.b.add(runnable);
        a.c.add(runnable2);
    }

    public void onCreate() {
        this.activity.setContentView(b());
    }

    private LinearLayout b() {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        linearLayout.setBackgroundColor(-1);
        TextView textView = new TextView(this.activity);
        textView.setBackgroundColor(-13617865);
        int dipToPx = R.dipToPx(this.activity, 26);
        textView.setPadding(dipToPx, 0, dipToPx, 0);
        textView.setTextColor(-3158065);
        textView.setTextSize(1, 20.0f);
        textView.setText(c());
        textView.setGravity(16);
        linearLayout.addView(textView, new LayoutParams(-1, R.dipToPx(this.activity, 52)));
        View view = new View(this.activity);
        view.setBackgroundColor(-15066083);
        linearLayout.addView(view, new LayoutParams(-1, R.dipToPx(this.activity, 2)));
        TextView textView2 = new TextView(this.activity);
        int dipToPx2 = R.dipToPx(this.activity, 15);
        textView2.setPadding(dipToPx2, dipToPx2, dipToPx2, dipToPx2);
        textView2.setTextColor(-6710887);
        textView2.setTextSize(1, 18.0f);
        textView2.setText(d());
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.weight = 1.0f;
        linearLayout.addView(textView2, layoutParams);
        LinearLayout linearLayout2 = new LinearLayout(this.activity);
        int dipToPx3 = R.dipToPx(this.activity, 5);
        linearLayout2.setPadding(dipToPx3, dipToPx3, dipToPx3, dipToPx3);
        linearLayout.addView(linearLayout2, new LayoutParams(-1, -2));
        this.d = new TextView(this.activity);
        this.d.setTextColor(-6102899);
        this.d.setTextSize(1, 20.0f);
        this.d.setText(e());
        this.d.setBackgroundDrawable(f());
        this.d.setGravity(17);
        int dipToPx4 = R.dipToPx(this.activity, 48);
        LayoutParams layoutParams2 = new LayoutParams(-1, dipToPx4);
        layoutParams2.weight = 1.0f;
        linearLayout2.addView(this.d, layoutParams2);
        this.d.setOnClickListener(this);
        linearLayout2.addView(new View(this.activity), new LayoutParams(dipToPx3, -1));
        this.e = new TextView(this.activity);
        this.e.setTextColor(-1);
        this.e.setTextSize(1, 20.0f);
        this.e.setText(g());
        this.e.setBackgroundDrawable(h());
        this.e.setGravity(17);
        LayoutParams layoutParams3 = new LayoutParams(-1, dipToPx4);
        layoutParams3.weight = 1.0f;
        linearLayout2.addView(this.e, layoutParams3);
        this.e.setOnClickListener(this);
        return linearLayout;
    }

    private String c() {
        if ("zh".equals(DeviceHelper.getInstance(this.activity).getOSLanguage())) {
            return String.valueOf(new char[]{35686, 21578});
        }
        return HttpHeaders.WARNING;
    }

    private String d() {
        String str;
        String appName = DeviceHelper.getInstance(this.activity).getAppName();
        if ("zh".equals(DeviceHelper.getInstance(this.activity).getOSLanguage())) {
            str = "\"%s\"" + String.valueOf(new char[]{24819, 35775, 38382, 24744, 30340, 36890, 20449, 24405});
        } else {
            str = "\"%s\" would like to access your contacts.";
        }
        return String.format(str, new Object[]{appName});
    }

    private String e() {
        if ("zh".equals(DeviceHelper.getInstance(this.activity).getOSLanguage())) {
            return String.valueOf(new char[]{21462, 28040});
        }
        return "Cancel";
    }

    private Drawable f() {
        return new ShapeDrawable(new b(this));
    }

    private String g() {
        if ("zh".equals(DeviceHelper.getInstance(this.activity).getOSLanguage())) {
            return String.valueOf(new char[]{32487, 32493});
        }
        return "OK";
    }

    private Drawable h() {
        return new ShapeDrawable(new c(this));
    }

    public void onClick(View view) {
        if (view.equals(this.e)) {
            this.f.put(ShareConstants.RES_PATH, Boolean.valueOf(true));
        }
        finish();
    }

    public void onDestroy() {
        a = null;
        super.onDestroy();
    }
}
