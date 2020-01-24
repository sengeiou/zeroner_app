package com.iwown.sport_module.ui.sleep;

import android.text.TextUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class TextTimeBean {
    public List<String> numbers = new ArrayList();
    public String text;

    public void updateValue(String value) {
        if (!TextUtils.equals("-1", value)) {
            int parseInt = Integer.parseInt(value);
            if (this.numbers.size() == 4) {
                KLog.d("number size is 4");
            } else {
                this.numbers.add(value);
            }
        } else if (this.numbers.size() == 0) {
            KLog.d(" numbers size is 0");
        } else {
            this.numbers.remove(this.numbers.size() - 1);
        }
    }

    public String getTextValue() {
        List<String> temps = new ArrayList<>();
        temps.add("0");
        temps.add("0");
        temps.add("0");
        temps.add("0");
        if (this.numbers.size() > 0) {
            int index = temps.size() - 1;
            for (int i = this.numbers.size() - 1; i >= 0 && index >= 0; i--) {
                temps.set(index, this.numbers.get(i));
                index--;
            }
        }
        return "" + ((String) temps.get(0)) + ((String) temps.get(1)) + ":" + ((String) temps.get(2)) + ((String) temps.get(3));
    }
}
