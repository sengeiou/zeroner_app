package me.yokeyword.fragmentation.exception;

import android.util.Log;

public class AfterSaveStateTransactionWarning extends RuntimeException {
    public AfterSaveStateTransactionWarning(String action) {
        super("Warning: Perform this " + action + " action after onSaveInstanceState!");
        Log.w("Fragmentation", getMessage());
    }
}
