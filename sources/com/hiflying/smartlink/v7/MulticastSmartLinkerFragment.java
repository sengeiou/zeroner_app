package com.hiflying.smartlink.v7;

import com.hiflying.smartlink.AbstractSmartLinkerFragment;
import com.hiflying.smartlink.ISmartLinker;

public class MulticastSmartLinkerFragment extends AbstractSmartLinkerFragment {
    public ISmartLinker setupSmartLinker() {
        return MulticastSmartLinker.getInstance();
    }
}
