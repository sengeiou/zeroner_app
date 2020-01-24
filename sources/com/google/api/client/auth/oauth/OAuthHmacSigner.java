package com.google.api.client.auth.oauth;

import com.google.api.client.util.Base64;
import com.google.api.client.util.Beta;
import com.google.api.client.util.StringUtils;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Typography;

@Beta
public final class OAuthHmacSigner implements OAuthSigner {
    public String clientSharedSecret;
    public String tokenSharedSecret;

    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }

    public String computeSignature(String signatureBaseString) throws GeneralSecurityException {
        StringBuilder keyBuf = new StringBuilder();
        String clientSharedSecret2 = this.clientSharedSecret;
        if (clientSharedSecret2 != null) {
            keyBuf.append(OAuthParameters.escape(clientSharedSecret2));
        }
        keyBuf.append(Typography.amp);
        String tokenSharedSecret2 = this.tokenSharedSecret;
        if (tokenSharedSecret2 != null) {
            keyBuf.append(OAuthParameters.escape(tokenSharedSecret2));
        }
        SecretKey secretKey = new SecretKeySpec(StringUtils.getBytesUtf8(keyBuf.toString()), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        return Base64.encodeBase64String(mac.doFinal(StringUtils.getBytesUtf8(signatureBaseString)));
    }
}
