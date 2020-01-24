package com.facebook.stetho.inspector.database;

import android.net.Uri;

public class ContentProviderSchema {
    private final String[] mProjection;
    private final String mTableName;
    private final Uri mUri;

    public static class Builder {
        /* access modifiers changed from: private */
        public Table mTable;

        public Builder table(Table table) {
            this.mTable = table;
            return this;
        }

        public ContentProviderSchema build() {
            return new ContentProviderSchema(this);
        }
    }

    public static class Table {
        /* access modifiers changed from: private */
        public String[] mProjection;
        /* access modifiers changed from: private */
        public String mTableName;
        /* access modifiers changed from: private */
        public Uri mUri;

        public static class Builder {
            /* access modifiers changed from: private */
            public String[] mProjection;
            /* access modifiers changed from: private */
            public String mTableName;
            /* access modifiers changed from: private */
            public Uri mUri;

            public Builder uri(Uri contentUri) {
                this.mUri = contentUri;
                return this;
            }

            public Builder projection(String[] columns) {
                this.mProjection = columns;
                return this;
            }

            public Builder name(String tableName) {
                this.mTableName = tableName;
                return this;
            }

            public Table build() {
                return new Table(this);
            }
        }

        private Table(Builder builder) {
            this.mUri = builder.mUri;
            this.mProjection = builder.mProjection;
            this.mTableName = builder.mTableName;
            if (this.mTableName == null) {
                this.mTableName = this.mUri.getLastPathSegment();
            }
        }
    }

    private ContentProviderSchema(Builder builder) {
        this.mTableName = builder.mTable.mTableName;
        this.mUri = builder.mTable.mUri;
        this.mProjection = builder.mTable.mProjection;
    }

    public String getTableName() {
        return this.mTableName;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String[] getProjection() {
        return this.mProjection;
    }
}
