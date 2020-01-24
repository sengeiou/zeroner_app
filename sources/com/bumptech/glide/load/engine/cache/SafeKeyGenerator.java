package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

class SafeKeyGenerator {
    private final LruCache<Key, String> loadIdToSafeHash = new LruCache<>(1000);

    SafeKeyGenerator() {
    }

    public String getSafeKey(Key key) {
        String safeKey;
        synchronized (this.loadIdToSafeHash) {
            safeKey = (String) this.loadIdToSafeHash.get(key);
        }
        if (safeKey == null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
                key.updateDiskCacheKey(messageDigest);
                safeKey = Util.sha256BytesToHex(messageDigest.digest());
            } catch (UnsupportedEncodingException e) {
                ThrowableExtension.printStackTrace(e);
            } catch (NoSuchAlgorithmException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            synchronized (this.loadIdToSafeHash) {
                this.loadIdToSafeHash.put(key, safeKey);
            }
        }
        return safeKey;
    }
}
