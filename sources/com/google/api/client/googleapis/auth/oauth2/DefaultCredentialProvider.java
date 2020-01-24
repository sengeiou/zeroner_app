package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Beta;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessControlException;
import java.util.Locale;

@Beta
class DefaultCredentialProvider extends SystemEnvironmentProvider {
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    private GoogleCredential cachedCredential = null;
    private Environment detectedEnvironment = null;

    private static class ComputeGoogleCredential extends GoogleCredential {
        private static final String TOKEN_SERVER_ENCODED_URL = String.valueOf(OAuth2Utils.getMetadataServerUrl()).concat("/computeMetadata/v1/instance/service-accounts/default/token");

        ComputeGoogleCredential(HttpTransport transport, JsonFactory jsonFactory) {
            super(new Builder().setTransport(transport).setJsonFactory(jsonFactory).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
        }

        /* access modifiers changed from: protected */
        public TokenResponse executeRefreshToken() throws IOException {
            HttpRequest request = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
            JsonObjectParser parser = new JsonObjectParser(getJsonFactory());
            request.setParser(parser);
            request.getHeaders().set("Metadata-Flavor", (Object) "Google");
            request.setThrowExceptionOnExecuteError(false);
            HttpResponse response = request.execute();
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                InputStream content = response.getContent();
                if (content != null) {
                    return (TokenResponse) parser.parseAndClose(content, response.getContentCharset(), TokenResponse.class);
                }
                throw new IOException("Empty content from metadata token server request.");
            } else if (statusCode == 404) {
                throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", new Object[]{Integer.valueOf(statusCode)}));
            } else {
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", new Object[]{Integer.valueOf(statusCode), response.parseAsString()}));
            }
        }
    }

    private enum Environment {
        UNKNOWN,
        ENVIRONMENT_VARIABLE,
        WELL_KNOWN_FILE,
        CLOUD_SHELL,
        APP_ENGINE,
        COMPUTE_ENGINE
    }

    DefaultCredentialProvider() {
    }

    /* access modifiers changed from: 0000 */
    public final GoogleCredential getDefaultCredential(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = getDefaultCredentialUnsynchronized(transport, jsonFactory);
            }
            if (this.cachedCredential != null) {
                GoogleCredential googleCredential = this.cachedCredential;
                return googleCredential;
            }
            throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", new Object[]{CREDENTIAL_ENV_VAR, HELP_PERMALINK}));
        }
    }

    private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = detectEnvironment(transport);
        }
        switch (this.detectedEnvironment) {
            case ENVIRONMENT_VARIABLE:
                return getCredentialUsingEnvironmentVariable(transport, jsonFactory);
            case WELL_KNOWN_FILE:
                return getCredentialUsingWellKnownFile(transport, jsonFactory);
            case APP_ENGINE:
                return getAppEngineCredential(transport, jsonFactory);
            case CLOUD_SHELL:
                return getCloudShellCredential(jsonFactory);
            case COMPUTE_ENGINE:
                return getComputeCredential(transport, jsonFactory);
            default:
                return null;
        }
    }

    private final File getWellKnownCredentialsFile() {
        File cloudConfigPath;
        if (getProperty("os.name", "").toLowerCase(Locale.US).indexOf("windows") >= 0) {
            cloudConfigPath = new File(new File(getEnv("APPDATA")), CLOUDSDK_CONFIG_DIRECTORY);
        } else {
            cloudConfigPath = new File(new File(getProperty("user.home", ""), ".config"), CLOUDSDK_CONFIG_DIRECTORY);
        }
        return new File(cloudConfigPath, WELL_KNOWN_CREDENTIALS_FILE);
    }

    /* access modifiers changed from: 0000 */
    public boolean fileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    /* access modifiers changed from: 0000 */
    public String getProperty(String property, String def) {
        return System.getProperty(property, def);
    }

    /* access modifiers changed from: 0000 */
    public Class<?> forName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    private final Environment detectEnvironment(HttpTransport transport) throws IOException {
        if (runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (runningOnAppEngine()) {
            return Environment.APP_ENGINE;
        }
        if (runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (OAuth2Utils.runningOnComputeEngine(transport, this)) {
            return Environment.COMPUTE_ENGINE;
        }
        return Environment.UNKNOWN;
    }

    private boolean runningUsingEnvironmentVariable() throws IOException {
        String credentialsPath = getEnv(CREDENTIAL_ENV_VAR);
        if (credentialsPath == null || credentialsPath.length() == 0) {
            return false;
        }
        try {
            File credentialsFile = new File(credentialsPath);
            if (credentialsFile.exists() && !credentialsFile.isDirectory()) {
                return true;
            }
            throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", new Object[]{CREDENTIAL_ENV_VAR, credentialsPath}));
        } catch (AccessControlException e) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.api.client.googleapis.auth.oauth2.GoogleCredential getCredentialUsingEnvironmentVariable(com.google.api.client.http.HttpTransport r10, com.google.api.client.json.JsonFactory r11) throws java.io.IOException {
        /*
            r9 = this;
            java.lang.String r4 = "GOOGLE_APPLICATION_CREDENTIALS"
            java.lang.String r0 = r9.getEnv(r4)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0017 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0017 }
            com.google.api.client.googleapis.auth.oauth2.GoogleCredential r4 = com.google.api.client.googleapis.auth.oauth2.GoogleCredential.fromStream(r2, r10, r11)     // Catch:{ IOException -> 0x0048, all -> 0x0045 }
            if (r2 == 0) goto L_0x0016
            r2.close()
        L_0x0016:
            return r4
        L_0x0017:
            r3 = move-exception
        L_0x0018:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x003e }
            java.lang.String r5 = "Error reading credential file from environment variable %s, value '%s': %s"
            r6 = 3
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x003e }
            r7 = 0
            java.lang.String r8 = "GOOGLE_APPLICATION_CREDENTIALS"
            r6[r7] = r8     // Catch:{ all -> 0x003e }
            r7 = 1
            r6[r7] = r0     // Catch:{ all -> 0x003e }
            r7 = 2
            java.lang.String r8 = r3.getMessage()     // Catch:{ all -> 0x003e }
            r6[r7] = r8     // Catch:{ all -> 0x003e }
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch:{ all -> 0x003e }
            r4.<init>(r5)     // Catch:{ all -> 0x003e }
            java.lang.Throwable r4 = com.google.api.client.googleapis.auth.oauth2.OAuth2Utils.exceptionWithCause(r4, r3)     // Catch:{ all -> 0x003e }
            java.io.IOException r4 = (java.io.IOException) r4     // Catch:{ all -> 0x003e }
            throw r4     // Catch:{ all -> 0x003e }
        L_0x003e:
            r4 = move-exception
        L_0x003f:
            if (r1 == 0) goto L_0x0044
            r1.close()
        L_0x0044:
            throw r4
        L_0x0045:
            r4 = move-exception
            r1 = r2
            goto L_0x003f
        L_0x0048:
            r3 = move-exception
            r1 = r2
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.getCredentialUsingEnvironmentVariable(com.google.api.client.http.HttpTransport, com.google.api.client.json.JsonFactory):com.google.api.client.googleapis.auth.oauth2.GoogleCredential");
    }

    private boolean runningUsingWellKnownFile() {
        try {
            return fileExists(getWellKnownCredentialsFile());
        } catch (AccessControlException e) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.api.client.googleapis.auth.oauth2.GoogleCredential getCredentialUsingWellKnownFile(com.google.api.client.http.HttpTransport r10, com.google.api.client.json.JsonFactory r11) throws java.io.IOException {
        /*
            r9 = this;
            java.io.File r3 = r9.getWellKnownCredentialsFile()
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0014 }
            r1.<init>(r3)     // Catch:{ IOException -> 0x0014 }
            com.google.api.client.googleapis.auth.oauth2.GoogleCredential r4 = com.google.api.client.googleapis.auth.oauth2.GoogleCredential.fromStream(r1, r10, r11)     // Catch:{ IOException -> 0x0039, all -> 0x0036 }
            if (r1 == 0) goto L_0x0013
            r1.close()
        L_0x0013:
            return r4
        L_0x0014:
            r2 = move-exception
        L_0x0015:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x002f }
            java.lang.String r5 = "Error reading credential file from location %s: %s"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x002f }
            r7 = 0
            r6[r7] = r3     // Catch:{ all -> 0x002f }
            r7 = 1
            java.lang.String r8 = r2.getMessage()     // Catch:{ all -> 0x002f }
            r6[r7] = r8     // Catch:{ all -> 0x002f }
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch:{ all -> 0x002f }
            r4.<init>(r5)     // Catch:{ all -> 0x002f }
            throw r4     // Catch:{ all -> 0x002f }
        L_0x002f:
            r4 = move-exception
        L_0x0030:
            if (r0 == 0) goto L_0x0035
            r0.close()
        L_0x0035:
            throw r4
        L_0x0036:
            r4 = move-exception
            r0 = r1
            goto L_0x0030
        L_0x0039:
            r2 = move-exception
            r0 = r1
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.getCredentialUsingWellKnownFile(com.google.api.client.http.HttpTransport, com.google.api.client.json.JsonFactory):com.google.api.client.googleapis.auth.oauth2.GoogleCredential");
    }

    private boolean runningOnAppEngine() {
        Exception cause;
        try {
            try {
                Field environmentField = forName("com.google.appengine.api.utils.SystemProperty").getField("environment");
                if (environmentField.getType().getMethod("value", new Class[0]).invoke(environmentField.get(null), new Object[0]) != null) {
                    return true;
                }
                return false;
            } catch (NoSuchFieldException exception) {
                cause = exception;
            } catch (SecurityException exception2) {
                cause = exception2;
            } catch (IllegalArgumentException exception3) {
                cause = exception3;
            } catch (IllegalAccessException exception4) {
                cause = exception4;
            } catch (NoSuchMethodException exception5) {
                cause = exception5;
            } catch (InvocationTargetException exception6) {
                cause = exception6;
            }
            throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{cause.getMessage()})), cause));
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private final GoogleCredential getAppEngineCredential(HttpTransport transport, JsonFactory jsonFactory) throws IOException {
        Exception innerException;
        try {
            return (GoogleCredential) forName(APP_ENGINE_CREDENTIAL_CLASS).getConstructor(new Class[]{HttpTransport.class, JsonFactory.class}).newInstance(new Object[]{transport, jsonFactory});
        } catch (ClassNotFoundException e) {
            innerException = e;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (NoSuchMethodException e2) {
            innerException = e2;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (InstantiationException e3) {
            innerException = e3;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (IllegalAccessException e4) {
            innerException = e4;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        } catch (InvocationTargetException e5) {
            innerException = e5;
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{APP_ENGINE_CREDENTIAL_CLASS})), innerException));
        }
    }

    private boolean runningOnCloudShell() {
        return getEnv(CLOUD_SHELL_ENV_VAR) != null;
    }

    private GoogleCredential getCloudShellCredential(JsonFactory jsonFactory) {
        return new CloudShellCredential(Integer.parseInt(getEnv(CLOUD_SHELL_ENV_VAR)), jsonFactory);
    }

    private final GoogleCredential getComputeCredential(HttpTransport transport, JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(transport, jsonFactory);
    }
}
