package com.hiflying.smartlink.v3;

import com.hiflying.smartlink.AbstractSmartLinkerActivity;
import com.hiflying.smartlink.ISmartLinker;

public class SnifferSmartLinkerActivity extends AbstractSmartLinkerActivity {
    public ISmartLinker setupSmartLinker() {
        return SnifferSmartLinker.getInstance();
    }
}
