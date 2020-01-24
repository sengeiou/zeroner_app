package com.facebook.stetho.inspector.protocol.module;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.SparseArray;
import com.facebook.stetho.common.LogUtil;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.helper.ChromePeerManager;
import com.facebook.stetho.inspector.helper.ObjectIdMapper;
import com.facebook.stetho.inspector.helper.PeersRegisteredListener;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcResult;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcError;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcError.ErrorCode;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsMethod;
import com.facebook.stetho.inspector.protocol.module.BaseDatabaseDriver.ExecuteResultHandler;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.json.annotation.JsonProperty;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.commons.codec.CharEncoding;
import org.json.JSONObject;

@TargetApi(11)
public class Database implements ChromeDevtoolsDomain {
    private static final int MAX_BLOB_LENGTH = 512;
    private static final int MAX_EXECUTE_RESULTS = 250;
    private static final String UNKNOWN_BLOB_LABEL = "{blob}";
    private final ChromePeerManager mChromePeerManager = new ChromePeerManager();
    private List<DatabaseDriver2> mDatabaseDrivers = new ArrayList();
    private final ObjectMapper mObjectMapper;
    private final DatabasePeerRegistrationListener mPeerListener = new DatabasePeerRegistrationListener(this.mDatabaseDrivers);

    public static class AddDatabaseEvent {
        @JsonProperty(required = true)
        public DatabaseObject database;
    }

    @Deprecated
    public static abstract class DatabaseDriver extends BaseDatabaseDriver<String> {
        public DatabaseDriver(Context context) {
            super(context);
        }
    }

    public static class DatabaseObject {
        @JsonProperty(required = true)
        public String domain;
        @JsonProperty(required = true)
        public String id;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String version;
    }

    public static class Error {
        @JsonProperty(required = true)
        public int code;
        @JsonProperty(required = true)
        public String message;
    }

    public static class ExecuteSQLRequest {
        @JsonProperty(required = true)
        public String databaseId;
        @JsonProperty(required = true)
        public String query;
    }

    public static class ExecuteSQLResponse implements JsonRpcResult {
        @JsonProperty
        public List<String> columnNames;
        @JsonProperty
        public Error sqlError;
        @JsonProperty
        public List<String> values;
    }

    private static class GetDatabaseTableNamesRequest {
        @JsonProperty(required = true)
        public String databaseId;

        private GetDatabaseTableNamesRequest() {
        }
    }

    private static class GetDatabaseTableNamesResponse implements JsonRpcResult {
        @JsonProperty(required = true)
        public List<String> tableNames;

        private GetDatabaseTableNamesResponse() {
        }
    }

    private static class DatabaseDescriptorHolder {
        public final DatabaseDescriptor descriptor;
        public final DatabaseDriver2 driver;

        public DatabaseDescriptorHolder(DatabaseDriver2 driver2, DatabaseDescriptor descriptor2) {
            this.driver = driver2;
            this.descriptor = descriptor2;
        }
    }

    @ThreadSafe
    private static class DatabasePeerRegistrationListener extends PeersRegisteredListener {
        private final List<DatabaseDriver2> mDatabaseDrivers;
        @GuardedBy("this")
        private final SparseArray<DatabaseDescriptorHolder> mDatabaseHolders;
        @GuardedBy("this")
        private final ObjectIdMapper mDatabaseIdMapper;

        private DatabasePeerRegistrationListener(List<DatabaseDriver2> databaseDrivers) {
            this.mDatabaseHolders = new SparseArray<>();
            this.mDatabaseIdMapper = new ObjectIdMapper();
            this.mDatabaseDrivers = databaseDrivers;
        }

        public DatabaseDescriptorHolder getDatabaseDescriptorHolder(String databaseId) {
            return (DatabaseDescriptorHolder) this.mDatabaseHolders.get(Integer.parseInt(databaseId));
        }

        /* access modifiers changed from: protected */
        public synchronized void onFirstPeerRegistered() {
            for (DatabaseDriver2<?> driver : this.mDatabaseDrivers) {
                for (DatabaseDescriptor desc : driver.getDatabaseNames()) {
                    if (this.mDatabaseIdMapper.getIdForObject(desc) == null) {
                        this.mDatabaseHolders.put(Integer.valueOf(this.mDatabaseIdMapper.putObject(desc)).intValue(), new DatabaseDescriptorHolder(driver, desc));
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void onLastPeerUnregistered() {
            this.mDatabaseIdMapper.clear();
            this.mDatabaseHolders.clear();
        }

        /* access modifiers changed from: protected */
        public synchronized void onPeerAdded(JsonRpcPeer peer) {
            int N = this.mDatabaseHolders.size();
            for (int i = 0; i < N; i++) {
                int id = this.mDatabaseHolders.keyAt(i);
                DatabaseDescriptorHolder holder = (DatabaseDescriptorHolder) this.mDatabaseHolders.valueAt(i);
                DatabaseObject databaseParams = new DatabaseObject();
                databaseParams.id = String.valueOf(id);
                databaseParams.name = holder.descriptor.name();
                databaseParams.domain = holder.driver.getContext().getPackageName();
                databaseParams.version = "N/A";
                AddDatabaseEvent eventParams = new AddDatabaseEvent();
                eventParams.database = databaseParams;
                peer.invokeMethod("Database.addDatabase", eventParams, null);
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void onPeerRemoved(JsonRpcPeer peer) {
        }
    }

    public Database() {
        this.mChromePeerManager.setListener(this.mPeerListener);
        this.mObjectMapper = new ObjectMapper();
    }

    public void add(DatabaseDriver2 databaseDriver) {
        this.mDatabaseDrivers.add(databaseDriver);
    }

    @ChromeDevtoolsMethod
    public void enable(JsonRpcPeer peer, JSONObject params) {
        this.mChromePeerManager.addPeer(peer);
    }

    @ChromeDevtoolsMethod
    public void disable(JsonRpcPeer peer, JSONObject params) {
        this.mChromePeerManager.removePeer(peer);
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult getDatabaseTableNames(JsonRpcPeer peer, JSONObject params) throws JsonRpcException {
        DatabaseDescriptorHolder holder = this.mPeerListener.getDatabaseDescriptorHolder(((GetDatabaseTableNamesRequest) this.mObjectMapper.convertValue(params, GetDatabaseTableNamesRequest.class)).databaseId);
        try {
            GetDatabaseTableNamesResponse response = new GetDatabaseTableNamesResponse();
            response.tableNames = holder.driver.getTableNames(holder.descriptor);
            return response;
        } catch (SQLiteException e) {
            throw new JsonRpcException(new JsonRpcError(ErrorCode.INVALID_REQUEST, e.toString(), null));
        }
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult executeSQL(JsonRpcPeer peer, JSONObject params) {
        ExecuteSQLRequest request = (ExecuteSQLRequest) this.mObjectMapper.convertValue(params, ExecuteSQLRequest.class);
        DatabaseDescriptorHolder holder = this.mPeerListener.getDatabaseDescriptorHolder(request.databaseId);
        try {
            return holder.driver.executeSQL(holder.descriptor, request.query, new ExecuteResultHandler<ExecuteSQLResponse>() {
                public ExecuteSQLResponse handleRawQuery() throws SQLiteException {
                    ExecuteSQLResponse response = new ExecuteSQLResponse();
                    response.columnNames = Collections.singletonList("success");
                    response.values = Collections.singletonList("true");
                    return response;
                }

                public ExecuteSQLResponse handleSelect(Cursor result) throws SQLiteException {
                    ExecuteSQLResponse response = new ExecuteSQLResponse();
                    response.columnNames = Arrays.asList(result.getColumnNames());
                    response.values = Database.flattenRows(result, 250);
                    return response;
                }

                public ExecuteSQLResponse handleInsert(long insertedId) throws SQLiteException {
                    ExecuteSQLResponse response = new ExecuteSQLResponse();
                    response.columnNames = Collections.singletonList("ID of last inserted row");
                    response.values = Collections.singletonList(String.valueOf(insertedId));
                    return response;
                }

                public ExecuteSQLResponse handleUpdateDelete(int count) throws SQLiteException {
                    ExecuteSQLResponse response = new ExecuteSQLResponse();
                    response.columnNames = Collections.singletonList("Modified rows");
                    response.values = Collections.singletonList(String.valueOf(count));
                    return response;
                }
            });
        } catch (RuntimeException e) {
            LogUtil.e(e, "Exception executing: %s", request.query);
            Error error = new Error();
            error.code = 0;
            error.message = e.getMessage();
            ExecuteSQLResponse response = new ExecuteSQLResponse();
            response.sqlError = error;
            return response;
        }
    }

    /* access modifiers changed from: private */
    public static ArrayList<String> flattenRows(Cursor cursor, int limit) {
        Util.throwIfNot(limit >= 0);
        ArrayList<String> flatList = new ArrayList<>();
        int numColumns = cursor.getColumnCount();
        for (int row = 0; row < limit && cursor.moveToNext(); row++) {
            for (int column = 0; column < numColumns; column++) {
                switch (cursor.getType(column)) {
                    case 0:
                        flatList.add(null);
                        break;
                    case 1:
                        flatList.add(String.valueOf(cursor.getLong(column)));
                        break;
                    case 2:
                        flatList.add(String.valueOf(cursor.getDouble(column)));
                        break;
                    case 4:
                        flatList.add(blobToString(cursor.getBlob(column)));
                        break;
                    default:
                        flatList.add(cursor.getString(column));
                        break;
                }
            }
        }
        if (!cursor.isAfterLast()) {
            for (int column2 = 0; column2 < numColumns; column2++) {
                flatList.add("{truncated}");
            }
        }
        return flatList;
    }

    private static String blobToString(byte[] blob) {
        if (blob.length <= 512 && fastIsAscii(blob)) {
            try {
                return new String(blob, CharEncoding.US_ASCII);
            } catch (UnsupportedEncodingException e) {
            }
        }
        return UNKNOWN_BLOB_LABEL;
    }

    private static boolean fastIsAscii(byte[] blob) {
        for (byte b : blob) {
            if ((b & Byte.MIN_VALUE) != 0) {
                return false;
            }
        }
        return true;
    }
}
