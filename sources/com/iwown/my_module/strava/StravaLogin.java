package com.iwown.my_module.strava;

import android.content.Context;
import android.content.Intent;
import com.sweetzpot.stravazpot.authenticaton.api.AccessScope;
import com.sweetzpot.stravazpot.authenticaton.api.ApprovalPrompt;

public class StravaLogin {
    private static final String STRAVA_LOGIN_URL = "https://www.strava.com/oauth/authorize?response_type=code";
    private AccessScope accessScope;
    private ApprovalPrompt approvalPrompt;
    private int clientID;
    private Context context;
    private String redirectURI;

    public static StravaLogin withContext(Context context2) {
        return new StravaLogin(context2);
    }

    public StravaLogin(Context context2) {
        this.context = context2;
    }

    public StravaLogin withClientID(int clientID2) {
        this.clientID = clientID2;
        return this;
    }

    public StravaLogin withRedirectURI(String redirectURI2) {
        this.redirectURI = redirectURI2;
        return this;
    }

    public StravaLogin withApprovalPrompt(ApprovalPrompt approvalPrompt2) {
        this.approvalPrompt = approvalPrompt2;
        return this;
    }

    public StravaLogin withAccessScope(AccessScope accessScope2) {
        this.accessScope = accessScope2;
        return this;
    }

    public Intent makeIntent() {
        Intent intent = new Intent(this.context, StravaLoginActivity.class);
        intent.putExtra("StravaLoginActivity.EXTRA_LOGIN_URL", makeLoginURL());
        intent.putExtra("StravaLoginActivity.EXTRA_REDIRECT_URL", this.redirectURI);
        return intent;
    }

    private String makeLoginURL() {
        StringBuilder loginURLBuilder = new StringBuilder();
        loginURLBuilder.append(STRAVA_LOGIN_URL);
        loginURLBuilder.append(clientIDParameter());
        loginURLBuilder.append(redirectURIParameter());
        loginURLBuilder.append(approvalPromptParameter());
        loginURLBuilder.append(accessScopeParameter());
        return loginURLBuilder.toString();
    }

    private String clientIDParameter() {
        return "&client_id=" + this.clientID;
    }

    private String redirectURIParameter() {
        if (this.redirectURI != null) {
            return "&redirect_uri=" + this.redirectURI;
        }
        return "";
    }

    private String approvalPromptParameter() {
        if (this.approvalPrompt != null) {
            return "&approval_prompt=" + this.approvalPrompt.toString();
        }
        return "";
    }

    private String accessScopeParameter() {
        if (this.accessScope != null) {
            return "&scope=" + this.accessScope.toString();
        }
        return "";
    }
}
