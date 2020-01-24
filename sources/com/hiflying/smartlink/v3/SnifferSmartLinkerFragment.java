package com.hiflying.smartlink.v3;

import com.hiflying.smartlink.AbstractSmartLinkerFragment;
import com.hiflying.smartlink.ISmartLinker;

public class SnifferSmartLinkerFragment extends AbstractSmartLinkerFragment {
    public ISmartLinker setupSmartLinker() {
        return SnifferSmartLinker.getInstance();
    }
}
