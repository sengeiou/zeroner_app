package com.hiflying.smartlink.v7;

import com.hiflying.smartlink.AbstractSmartLinkerActivity;
import com.hiflying.smartlink.ISmartLinker;

public class MulticastSmartLinkerActivity extends AbstractSmartLinkerActivity {
    public ISmartLinker setupSmartLinker() {
        return MulticastSmartLinker.getInstance();
    }
}
