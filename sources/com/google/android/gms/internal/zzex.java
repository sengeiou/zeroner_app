package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

public interface zzex extends IInterface {
    Bundle zza(Account account) throws RemoteException;

    Bundle zza(Account account, String str, Bundle bundle) throws RemoteException;

    Bundle zza(String str, Bundle bundle) throws RemoteException;

    AccountChangeEventsResponse zza(AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException;

    Bundle zzp(String str) throws RemoteException;
}
