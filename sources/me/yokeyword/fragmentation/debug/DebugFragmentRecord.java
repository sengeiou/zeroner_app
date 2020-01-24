package me.yokeyword.fragmentation.debug;

import java.util.List;

public class DebugFragmentRecord {
    public List<DebugFragmentRecord> childFragmentRecord;
    public CharSequence fragmentName;

    public DebugFragmentRecord(CharSequence fragmentName2, List<DebugFragmentRecord> childFragmentRecord2) {
        this.fragmentName = fragmentName2;
        this.childFragmentRecord = childFragmentRecord2;
    }
}
