package com.google.api.services.calendar;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Channel;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.google.api.services.calendar.model.Setting;
import java.io.IOException;

public class Calendar extends AbstractGoogleJsonClient {
    public static final String DEFAULT_BASE_URL = "https://www.googleapis.com/calendar/v3/";
    public static final String DEFAULT_BATCH_PATH = "batch/calendar/v3";
    public static final String DEFAULT_ROOT_URL = "https://www.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "calendar/v3/";

    public class Acl {

        public class Delete extends CalendarRequest<Void> {
            private static final String REST_PATH = "calendars/{calendarId}/acl/{ruleId}";
            @Key
            private String calendarId;
            @Key
            private String ruleId;

            protected Delete(String str, String str2) {
                super(Calendar.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.ruleId = (String) Preconditions.checkNotNull(str2, "Required parameter ruleId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Delete setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getRuleId() {
                return this.ruleId;
            }

            public Delete setRuleId(String str) {
                this.ruleId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends CalendarRequest<AclRule> {
            private static final String REST_PATH = "calendars/{calendarId}/acl/{ruleId}";
            @Key
            private String calendarId;
            @Key
            private String ruleId;

            protected Get(String str, String str2) {
                super(Calendar.this, "GET", REST_PATH, null, AclRule.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.ruleId = (String) Preconditions.checkNotNull(str2, "Required parameter ruleId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Get setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getRuleId() {
                return this.ruleId;
            }

            public Get setRuleId(String str) {
                this.ruleId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends CalendarRequest<AclRule> {
            private static final String REST_PATH = "calendars/{calendarId}/acl";
            @Key
            private String calendarId;
            @Key
            private Boolean sendNotifications;

            protected Insert(String str, AclRule aclRule) {
                super(Calendar.this, "POST", REST_PATH, aclRule, AclRule.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                checkRequiredParameter(aclRule, "content");
                checkRequiredParameter(aclRule.getRole(), "AclRule.getRole()");
                checkRequiredParameter(aclRule, "content");
                checkRequiredParameter(aclRule.getScope(), "AclRule.getScope()");
                checkRequiredParameter(aclRule, "content");
                checkRequiredParameter(aclRule.getScope().getType(), "AclRule.getScope().getType()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Insert setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Insert setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends CalendarRequest<com.google.api.services.calendar.model.Acl> {
            private static final String REST_PATH = "calendars/{calendarId}/acl";
            @Key
            private String calendarId;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private Boolean showDeleted;
            @Key
            private String syncToken;

            protected List(String str) {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Acl.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public List setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public List setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public List setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends CalendarRequest<AclRule> {
            private static final String REST_PATH = "calendars/{calendarId}/acl/{ruleId}";
            @Key
            private String calendarId;
            @Key
            private String ruleId;
            @Key
            private Boolean sendNotifications;

            protected Patch(String str, String str2, AclRule aclRule) {
                super(Calendar.this, "PATCH", REST_PATH, aclRule, AclRule.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.ruleId = (String) Preconditions.checkNotNull(str2, "Required parameter ruleId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Patch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getRuleId() {
                return this.ruleId;
            }

            public Patch setRuleId(String str) {
                this.ruleId = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Patch setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends CalendarRequest<AclRule> {
            private static final String REST_PATH = "calendars/{calendarId}/acl/{ruleId}";
            @Key
            private String calendarId;
            @Key
            private String ruleId;
            @Key
            private Boolean sendNotifications;

            protected Update(String str, String str2, AclRule aclRule) {
                super(Calendar.this, HttpMethods.PUT, REST_PATH, aclRule, AclRule.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.ruleId = (String) Preconditions.checkNotNull(str2, "Required parameter ruleId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Update setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getRuleId() {
                return this.ruleId;
            }

            public Update setRuleId(String str) {
                this.ruleId = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Update setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public class Watch extends CalendarRequest<Channel> {
            private static final String REST_PATH = "calendars/{calendarId}/acl/watch";
            @Key
            private String calendarId;
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private Boolean showDeleted;
            @Key
            private String syncToken;

            protected Watch(String str, Channel channel) {
                super(Calendar.this, "POST", REST_PATH, channel, Channel.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Watch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Watch setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Watch setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public Watch setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public Watch setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public Acl() {
        }

        public Delete delete(String str, String str2) throws IOException {
            Delete delete = new Delete(str, str2);
            Calendar.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            Get get = new Get(str, str2);
            Calendar.this.initialize(get);
            return get;
        }

        public Insert insert(String str, AclRule aclRule) throws IOException {
            Insert insert = new Insert(str, aclRule);
            Calendar.this.initialize(insert);
            return insert;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            Calendar.this.initialize(list);
            return list;
        }

        public Patch patch(String str, String str2, AclRule aclRule) throws IOException {
            Patch patch = new Patch(str, str2, aclRule);
            Calendar.this.initialize(patch);
            return patch;
        }

        public Update update(String str, String str2, AclRule aclRule) throws IOException {
            Update update = new Update(str, str2, aclRule);
            Calendar.this.initialize(update);
            return update;
        }

        public Watch watch(String str, Channel channel) throws IOException {
            Watch watch = new Watch(str, channel);
            Calendar.this.initialize(watch);
            return watch;
        }
    }

    public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, jsonFactory, Calendar.DEFAULT_ROOT_URL, Calendar.DEFAULT_SERVICE_PATH, httpRequestInitializer, false);
            setBatchPath(Calendar.DEFAULT_BATCH_PATH);
        }

        public Calendar build() {
            return new Calendar(this);
        }

        public Builder setRootUrl(String str) {
            return (Builder) super.setRootUrl(str);
        }

        public Builder setServicePath(String str) {
            return (Builder) super.setServicePath(str);
        }

        public Builder setBatchPath(String str) {
            return (Builder) super.setBatchPath(str);
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        public Builder setApplicationName(String str) {
            return (Builder) super.setApplicationName(str);
        }

        public Builder setSuppressPatternChecks(boolean z) {
            return (Builder) super.setSuppressPatternChecks(z);
        }

        public Builder setSuppressRequiredParameterChecks(boolean z) {
            return (Builder) super.setSuppressRequiredParameterChecks(z);
        }

        public Builder setSuppressAllChecks(boolean z) {
            return (Builder) super.setSuppressAllChecks(z);
        }

        public Builder setCalendarRequestInitializer(CalendarRequestInitializer calendarRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer((GoogleClientRequestInitializer) calendarRequestInitializer);
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }
    }

    public class CalendarList {

        public class Delete extends CalendarRequest<Void> {
            private static final String REST_PATH = "users/me/calendarList/{calendarId}";
            @Key
            private String calendarId;

            protected Delete(String str) {
                super(Calendar.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Delete setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends CalendarRequest<CalendarListEntry> {
            private static final String REST_PATH = "users/me/calendarList/{calendarId}";
            @Key
            private String calendarId;

            protected Get(String str) {
                super(Calendar.this, "GET", REST_PATH, null, CalendarListEntry.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Get setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends CalendarRequest<CalendarListEntry> {
            private static final String REST_PATH = "users/me/calendarList";
            @Key
            private Boolean colorRgbFormat;

            protected Insert(CalendarListEntry calendarListEntry) {
                super(Calendar.this, "POST", REST_PATH, calendarListEntry, CalendarListEntry.class);
                checkRequiredParameter(calendarListEntry, "content");
                checkRequiredParameter(calendarListEntry.getId(), "CalendarListEntry.getId()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public Boolean getColorRgbFormat() {
                return this.colorRgbFormat;
            }

            public Insert setColorRgbFormat(Boolean bool) {
                this.colorRgbFormat = bool;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class List extends CalendarRequest<com.google.api.services.calendar.model.CalendarList> {
            private static final String REST_PATH = "users/me/calendarList";
            @Key
            private Integer maxResults;
            @Key
            private String minAccessRole;
            @Key
            private String pageToken;
            @Key
            private Boolean showDeleted;
            @Key
            private Boolean showHidden;
            @Key
            private String syncToken;

            protected List() {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.CalendarList.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getMinAccessRole() {
                return this.minAccessRole;
            }

            public List setMinAccessRole(String str) {
                this.minAccessRole = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public List setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public Boolean getShowHidden() {
                return this.showHidden;
            }

            public List setShowHidden(Boolean bool) {
                this.showHidden = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public List setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Patch extends CalendarRequest<CalendarListEntry> {
            private static final String REST_PATH = "users/me/calendarList/{calendarId}";
            @Key
            private String calendarId;
            @Key
            private Boolean colorRgbFormat;

            protected Patch(String str, CalendarListEntry calendarListEntry) {
                super(Calendar.this, "PATCH", REST_PATH, calendarListEntry, CalendarListEntry.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Patch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Boolean getColorRgbFormat() {
                return this.colorRgbFormat;
            }

            public Patch setColorRgbFormat(Boolean bool) {
                this.colorRgbFormat = bool;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends CalendarRequest<CalendarListEntry> {
            private static final String REST_PATH = "users/me/calendarList/{calendarId}";
            @Key
            private String calendarId;
            @Key
            private Boolean colorRgbFormat;

            protected Update(String str, CalendarListEntry calendarListEntry) {
                super(Calendar.this, HttpMethods.PUT, REST_PATH, calendarListEntry, CalendarListEntry.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Update setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Boolean getColorRgbFormat() {
                return this.colorRgbFormat;
            }

            public Update setColorRgbFormat(Boolean bool) {
                this.colorRgbFormat = bool;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public class Watch extends CalendarRequest<Channel> {
            private static final String REST_PATH = "users/me/calendarList/watch";
            @Key
            private Integer maxResults;
            @Key
            private String minAccessRole;
            @Key
            private String pageToken;
            @Key
            private Boolean showDeleted;
            @Key
            private Boolean showHidden;
            @Key
            private String syncToken;

            protected Watch(Channel channel) {
                super(Calendar.this, "POST", REST_PATH, channel, Channel.class);
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Watch setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getMinAccessRole() {
                return this.minAccessRole;
            }

            public Watch setMinAccessRole(String str) {
                this.minAccessRole = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Watch setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public Watch setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public Boolean getShowHidden() {
                return this.showHidden;
            }

            public Watch setShowHidden(Boolean bool) {
                this.showHidden = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public Watch setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public CalendarList() {
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            Calendar.this.initialize(delete);
            return delete;
        }

        public Get get(String str) throws IOException {
            Get get = new Get(str);
            Calendar.this.initialize(get);
            return get;
        }

        public Insert insert(CalendarListEntry calendarListEntry) throws IOException {
            Insert insert = new Insert(calendarListEntry);
            Calendar.this.initialize(insert);
            return insert;
        }

        public List list() throws IOException {
            List list = new List();
            Calendar.this.initialize(list);
            return list;
        }

        public Patch patch(String str, CalendarListEntry calendarListEntry) throws IOException {
            Patch patch = new Patch(str, calendarListEntry);
            Calendar.this.initialize(patch);
            return patch;
        }

        public Update update(String str, CalendarListEntry calendarListEntry) throws IOException {
            Update update = new Update(str, calendarListEntry);
            Calendar.this.initialize(update);
            return update;
        }

        public Watch watch(Channel channel) throws IOException {
            Watch watch = new Watch(channel);
            Calendar.this.initialize(watch);
            return watch;
        }
    }

    public class Calendars {

        public class Clear extends CalendarRequest<Void> {
            private static final String REST_PATH = "calendars/{calendarId}/clear";
            @Key
            private String calendarId;

            protected Clear(String str) {
                super(Calendar.this, "POST", REST_PATH, null, Void.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Clear setAlt(String str) {
                return (Clear) super.setAlt(str);
            }

            public Clear setFields(String str) {
                return (Clear) super.setFields(str);
            }

            public Clear setKey(String str) {
                return (Clear) super.setKey(str);
            }

            public Clear setOauthToken(String str) {
                return (Clear) super.setOauthToken(str);
            }

            public Clear setPrettyPrint(Boolean bool) {
                return (Clear) super.setPrettyPrint(bool);
            }

            public Clear setQuotaUser(String str) {
                return (Clear) super.setQuotaUser(str);
            }

            public Clear setUserIp(String str) {
                return (Clear) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Clear setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Clear set(String str, Object obj) {
                return (Clear) super.set(str, obj);
            }
        }

        public class Delete extends CalendarRequest<Void> {
            private static final String REST_PATH = "calendars/{calendarId}";
            @Key
            private String calendarId;

            protected Delete(String str) {
                super(Calendar.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Delete setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends CalendarRequest<com.google.api.services.calendar.model.Calendar> {
            private static final String REST_PATH = "calendars/{calendarId}";
            @Key
            private String calendarId;

            protected Get(String str) {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Calendar.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Get setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Patch extends CalendarRequest<com.google.api.services.calendar.model.Calendar> {
            private static final String REST_PATH = "calendars/{calendarId}";
            @Key
            private String calendarId;

            protected Patch(String str, com.google.api.services.calendar.model.Calendar calendar) {
                super(Calendar.this, "PATCH", REST_PATH, calendar, com.google.api.services.calendar.model.Calendar.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Patch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class Update extends CalendarRequest<com.google.api.services.calendar.model.Calendar> {
            private static final String REST_PATH = "calendars/{calendarId}";
            @Key
            private String calendarId;

            protected Update(String str, com.google.api.services.calendar.model.Calendar calendar) {
                super(Calendar.this, HttpMethods.PUT, REST_PATH, calendar, com.google.api.services.calendar.model.Calendar.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Update setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public class Insert extends CalendarRequest<com.google.api.services.calendar.model.Calendar> {
            private static final String REST_PATH = "calendars";

            protected Insert(com.google.api.services.calendar.model.Calendar calendar) {
                super(Calendar.this, "POST", REST_PATH, calendar, com.google.api.services.calendar.model.Calendar.class);
                checkRequiredParameter(calendar, "content");
                checkRequiredParameter(calendar.getSummary(), "Calendar.getSummary()");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public Calendars() {
        }

        public Clear clear(String str) throws IOException {
            Clear clear = new Clear(str);
            Calendar.this.initialize(clear);
            return clear;
        }

        public Delete delete(String str) throws IOException {
            Delete delete = new Delete(str);
            Calendar.this.initialize(delete);
            return delete;
        }

        public Get get(String str) throws IOException {
            Get get = new Get(str);
            Calendar.this.initialize(get);
            return get;
        }

        public Insert insert(com.google.api.services.calendar.model.Calendar calendar) throws IOException {
            Insert insert = new Insert(calendar);
            Calendar.this.initialize(insert);
            return insert;
        }

        public Patch patch(String str, com.google.api.services.calendar.model.Calendar calendar) throws IOException {
            Patch patch = new Patch(str, calendar);
            Calendar.this.initialize(patch);
            return patch;
        }

        public Update update(String str, com.google.api.services.calendar.model.Calendar calendar) throws IOException {
            Update update = new Update(str, calendar);
            Calendar.this.initialize(update);
            return update;
        }
    }

    public class Channels {

        public class Stop extends CalendarRequest<Void> {
            private static final String REST_PATH = "channels/stop";

            protected Stop(Channel channel) {
                super(Calendar.this, "POST", REST_PATH, channel, Void.class);
            }

            public Stop setAlt(String str) {
                return (Stop) super.setAlt(str);
            }

            public Stop setFields(String str) {
                return (Stop) super.setFields(str);
            }

            public Stop setKey(String str) {
                return (Stop) super.setKey(str);
            }

            public Stop setOauthToken(String str) {
                return (Stop) super.setOauthToken(str);
            }

            public Stop setPrettyPrint(Boolean bool) {
                return (Stop) super.setPrettyPrint(bool);
            }

            public Stop setQuotaUser(String str) {
                return (Stop) super.setQuotaUser(str);
            }

            public Stop setUserIp(String str) {
                return (Stop) super.setUserIp(str);
            }

            public Stop set(String str, Object obj) {
                return (Stop) super.set(str, obj);
            }
        }

        public Channels() {
        }

        public Stop stop(Channel channel) throws IOException {
            Stop stop = new Stop(channel);
            Calendar.this.initialize(stop);
            return stop;
        }
    }

    public class Colors {

        public class Get extends CalendarRequest<com.google.api.services.calendar.model.Colors> {
            private static final String REST_PATH = "colors";

            protected Get() {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Colors.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public Colors() {
        }

        public Get get() throws IOException {
            Get get = new Get();
            Calendar.this.initialize(get);
            return get;
        }
    }

    public class Events {

        public class CalendarImport extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/import";
            @Key
            private String calendarId;
            @Key
            private Integer conferenceDataVersion;
            @Key
            private Boolean supportsAttachments;

            protected CalendarImport(String str, Event event) {
                super(Calendar.this, "POST", REST_PATH, event, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                checkRequiredParameter(event, "content");
                checkRequiredParameter(event.getICalUID(), "Event.getICalUID()");
            }

            public CalendarImport setAlt(String str) {
                return (CalendarImport) super.setAlt(str);
            }

            public CalendarImport setFields(String str) {
                return (CalendarImport) super.setFields(str);
            }

            public CalendarImport setKey(String str) {
                return (CalendarImport) super.setKey(str);
            }

            public CalendarImport setOauthToken(String str) {
                return (CalendarImport) super.setOauthToken(str);
            }

            public CalendarImport setPrettyPrint(Boolean bool) {
                return (CalendarImport) super.setPrettyPrint(bool);
            }

            public CalendarImport setQuotaUser(String str) {
                return (CalendarImport) super.setQuotaUser(str);
            }

            public CalendarImport setUserIp(String str) {
                return (CalendarImport) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public CalendarImport setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Integer getConferenceDataVersion() {
                return this.conferenceDataVersion;
            }

            public CalendarImport setConferenceDataVersion(Integer num) {
                this.conferenceDataVersion = num;
                return this;
            }

            public Boolean getSupportsAttachments() {
                return this.supportsAttachments;
            }

            public CalendarImport setSupportsAttachments(Boolean bool) {
                this.supportsAttachments = bool;
                return this;
            }

            public CalendarImport set(String str, Object obj) {
                return (CalendarImport) super.set(str, obj);
            }
        }

        public class Delete extends CalendarRequest<Void> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}";
            @Key
            private String calendarId;
            @Key
            private String eventId;
            @Key
            private Boolean sendNotifications;

            protected Delete(String str, String str2) {
                super(Calendar.this, HttpMethods.DELETE, REST_PATH, null, Void.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
            }

            public Delete setAlt(String str) {
                return (Delete) super.setAlt(str);
            }

            public Delete setFields(String str) {
                return (Delete) super.setFields(str);
            }

            public Delete setKey(String str) {
                return (Delete) super.setKey(str);
            }

            public Delete setOauthToken(String str) {
                return (Delete) super.setOauthToken(str);
            }

            public Delete setPrettyPrint(Boolean bool) {
                return (Delete) super.setPrettyPrint(bool);
            }

            public Delete setQuotaUser(String str) {
                return (Delete) super.setQuotaUser(str);
            }

            public Delete setUserIp(String str) {
                return (Delete) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Delete setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Delete setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Delete setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Delete set(String str, Object obj) {
                return (Delete) super.set(str, obj);
            }
        }

        public class Get extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private String eventId;
            @Key
            private Integer maxAttendees;
            @Key
            private String timeZone;

            protected Get(String str, String str2) {
                super(Calendar.this, "GET", REST_PATH, null, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Get setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Get setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public Get setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Get setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public String getTimeZone() {
                return this.timeZone;
            }

            public Get setTimeZone(String str) {
                this.timeZone = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class Insert extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events";
            @Key
            private String calendarId;
            @Key
            private Integer conferenceDataVersion;
            @Key
            private Integer maxAttendees;
            @Key
            private Boolean sendNotifications;
            @Key
            private Boolean supportsAttachments;

            protected Insert(String str, Event event) {
                super(Calendar.this, "POST", REST_PATH, event, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Insert setAlt(String str) {
                return (Insert) super.setAlt(str);
            }

            public Insert setFields(String str) {
                return (Insert) super.setFields(str);
            }

            public Insert setKey(String str) {
                return (Insert) super.setKey(str);
            }

            public Insert setOauthToken(String str) {
                return (Insert) super.setOauthToken(str);
            }

            public Insert setPrettyPrint(Boolean bool) {
                return (Insert) super.setPrettyPrint(bool);
            }

            public Insert setQuotaUser(String str) {
                return (Insert) super.setQuotaUser(str);
            }

            public Insert setUserIp(String str) {
                return (Insert) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Insert setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Integer getConferenceDataVersion() {
                return this.conferenceDataVersion;
            }

            public Insert setConferenceDataVersion(Integer num) {
                this.conferenceDataVersion = num;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Insert setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Insert setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Boolean getSupportsAttachments() {
                return this.supportsAttachments;
            }

            public Insert setSupportsAttachments(Boolean bool) {
                this.supportsAttachments = bool;
                return this;
            }

            public Insert set(String str, Object obj) {
                return (Insert) super.set(str, obj);
            }
        }

        public class Instances extends CalendarRequest<com.google.api.services.calendar.model.Events> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}/instances";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private String eventId;
            @Key
            private Integer maxAttendees;
            @Key
            private Integer maxResults;
            @Key
            private String originalStart;
            @Key
            private String pageToken;
            @Key
            private Boolean showDeleted;
            @Key
            private DateTime timeMax;
            @Key
            private DateTime timeMin;
            @Key
            private String timeZone;

            protected Instances(String str, String str2) {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Events.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Instances setAlt(String str) {
                return (Instances) super.setAlt(str);
            }

            public Instances setFields(String str) {
                return (Instances) super.setFields(str);
            }

            public Instances setKey(String str) {
                return (Instances) super.setKey(str);
            }

            public Instances setOauthToken(String str) {
                return (Instances) super.setOauthToken(str);
            }

            public Instances setPrettyPrint(Boolean bool) {
                return (Instances) super.setPrettyPrint(bool);
            }

            public Instances setQuotaUser(String str) {
                return (Instances) super.setQuotaUser(str);
            }

            public Instances setUserIp(String str) {
                return (Instances) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Instances setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Instances setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public Instances setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Instances setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Instances setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getOriginalStart() {
                return this.originalStart;
            }

            public Instances setOriginalStart(String str) {
                this.originalStart = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Instances setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public Instances setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public DateTime getTimeMax() {
                return this.timeMax;
            }

            public Instances setTimeMax(DateTime dateTime) {
                this.timeMax = dateTime;
                return this;
            }

            public DateTime getTimeMin() {
                return this.timeMin;
            }

            public Instances setTimeMin(DateTime dateTime) {
                this.timeMin = dateTime;
                return this;
            }

            public String getTimeZone() {
                return this.timeZone;
            }

            public Instances setTimeZone(String str) {
                this.timeZone = str;
                return this;
            }

            public Instances set(String str, Object obj) {
                return (Instances) super.set(str, obj);
            }
        }

        public class List extends CalendarRequest<com.google.api.services.calendar.model.Events> {
            private static final String REST_PATH = "calendars/{calendarId}/events";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private String iCalUID;
            @Key
            private Integer maxAttendees;
            @Key
            private Integer maxResults;
            @Key
            private String orderBy;
            @Key
            private String pageToken;
            @Key
            private java.util.List<String> privateExtendedProperty;
            @Key
            private String q;
            @Key
            private java.util.List<String> sharedExtendedProperty;
            @Key
            private Boolean showDeleted;
            @Key
            private Boolean showHiddenInvitations;
            @Key
            private Boolean singleEvents;
            @Key
            private String syncToken;
            @Key
            private DateTime timeMax;
            @Key
            private DateTime timeMin;
            @Key
            private String timeZone;
            @Key
            private DateTime updatedMin;

            protected List(String str) {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Events.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public List setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public List setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public String getICalUID() {
                return this.iCalUID;
            }

            public List setICalUID(String str) {
                this.iCalUID = str;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public List setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getOrderBy() {
                return this.orderBy;
            }

            public List setOrderBy(String str) {
                this.orderBy = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public java.util.List<String> getPrivateExtendedProperty() {
                return this.privateExtendedProperty;
            }

            public List setPrivateExtendedProperty(java.util.List<String> list) {
                this.privateExtendedProperty = list;
                return this;
            }

            public String getQ() {
                return this.q;
            }

            public List setQ(String str) {
                this.q = str;
                return this;
            }

            public java.util.List<String> getSharedExtendedProperty() {
                return this.sharedExtendedProperty;
            }

            public List setSharedExtendedProperty(java.util.List<String> list) {
                this.sharedExtendedProperty = list;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public List setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public Boolean getShowHiddenInvitations() {
                return this.showHiddenInvitations;
            }

            public List setShowHiddenInvitations(Boolean bool) {
                this.showHiddenInvitations = bool;
                return this;
            }

            public Boolean getSingleEvents() {
                return this.singleEvents;
            }

            public List setSingleEvents(Boolean bool) {
                this.singleEvents = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public List setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public DateTime getTimeMax() {
                return this.timeMax;
            }

            public List setTimeMax(DateTime dateTime) {
                this.timeMax = dateTime;
                return this;
            }

            public DateTime getTimeMin() {
                return this.timeMin;
            }

            public List setTimeMin(DateTime dateTime) {
                this.timeMin = dateTime;
                return this;
            }

            public String getTimeZone() {
                return this.timeZone;
            }

            public List setTimeZone(String str) {
                this.timeZone = str;
                return this;
            }

            public DateTime getUpdatedMin() {
                return this.updatedMin;
            }

            public List setUpdatedMin(DateTime dateTime) {
                this.updatedMin = dateTime;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Move extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}/move";
            @Key
            private String calendarId;
            @Key
            private String destination;
            @Key
            private String eventId;
            @Key
            private Boolean sendNotifications;

            protected Move(String str, String str2, String str3) {
                super(Calendar.this, "POST", REST_PATH, null, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
                this.destination = (String) Preconditions.checkNotNull(str3, "Required parameter destination must be specified.");
            }

            public Move setAlt(String str) {
                return (Move) super.setAlt(str);
            }

            public Move setFields(String str) {
                return (Move) super.setFields(str);
            }

            public Move setKey(String str) {
                return (Move) super.setKey(str);
            }

            public Move setOauthToken(String str) {
                return (Move) super.setOauthToken(str);
            }

            public Move setPrettyPrint(Boolean bool) {
                return (Move) super.setPrettyPrint(bool);
            }

            public Move setQuotaUser(String str) {
                return (Move) super.setQuotaUser(str);
            }

            public Move setUserIp(String str) {
                return (Move) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Move setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Move setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public String getDestination() {
                return this.destination;
            }

            public Move setDestination(String str) {
                this.destination = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Move setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Move set(String str, Object obj) {
                return (Move) super.set(str, obj);
            }
        }

        public class Patch extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private Integer conferenceDataVersion;
            @Key
            private String eventId;
            @Key
            private Integer maxAttendees;
            @Key
            private Boolean sendNotifications;
            @Key
            private Boolean supportsAttachments;

            protected Patch(String str, String str2, Event event) {
                super(Calendar.this, "PATCH", REST_PATH, event, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
            }

            public Patch setAlt(String str) {
                return (Patch) super.setAlt(str);
            }

            public Patch setFields(String str) {
                return (Patch) super.setFields(str);
            }

            public Patch setKey(String str) {
                return (Patch) super.setKey(str);
            }

            public Patch setOauthToken(String str) {
                return (Patch) super.setOauthToken(str);
            }

            public Patch setPrettyPrint(Boolean bool) {
                return (Patch) super.setPrettyPrint(bool);
            }

            public Patch setQuotaUser(String str) {
                return (Patch) super.setQuotaUser(str);
            }

            public Patch setUserIp(String str) {
                return (Patch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Patch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Patch setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public Patch setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public Integer getConferenceDataVersion() {
                return this.conferenceDataVersion;
            }

            public Patch setConferenceDataVersion(Integer num) {
                this.conferenceDataVersion = num;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Patch setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Patch setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Boolean getSupportsAttachments() {
                return this.supportsAttachments;
            }

            public Patch setSupportsAttachments(Boolean bool) {
                this.supportsAttachments = bool;
                return this;
            }

            public Patch set(String str, Object obj) {
                return (Patch) super.set(str, obj);
            }
        }

        public class QuickAdd extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/quickAdd";
            @Key
            private String calendarId;
            @Key
            private Boolean sendNotifications;
            @Key
            private String text;

            protected QuickAdd(String str, String str2) {
                super(Calendar.this, "POST", REST_PATH, null, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.text = (String) Preconditions.checkNotNull(str2, "Required parameter text must be specified.");
            }

            public QuickAdd setAlt(String str) {
                return (QuickAdd) super.setAlt(str);
            }

            public QuickAdd setFields(String str) {
                return (QuickAdd) super.setFields(str);
            }

            public QuickAdd setKey(String str) {
                return (QuickAdd) super.setKey(str);
            }

            public QuickAdd setOauthToken(String str) {
                return (QuickAdd) super.setOauthToken(str);
            }

            public QuickAdd setPrettyPrint(Boolean bool) {
                return (QuickAdd) super.setPrettyPrint(bool);
            }

            public QuickAdd setQuotaUser(String str) {
                return (QuickAdd) super.setQuotaUser(str);
            }

            public QuickAdd setUserIp(String str) {
                return (QuickAdd) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public QuickAdd setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getText() {
                return this.text;
            }

            public QuickAdd setText(String str) {
                this.text = str;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public QuickAdd setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public QuickAdd set(String str, Object obj) {
                return (QuickAdd) super.set(str, obj);
            }
        }

        public class Update extends CalendarRequest<Event> {
            private static final String REST_PATH = "calendars/{calendarId}/events/{eventId}";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private Integer conferenceDataVersion;
            @Key
            private String eventId;
            @Key
            private Integer maxAttendees;
            @Key
            private Boolean sendNotifications;
            @Key
            private Boolean supportsAttachments;

            protected Update(String str, String str2, Event event) {
                super(Calendar.this, HttpMethods.PUT, REST_PATH, event, Event.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
                this.eventId = (String) Preconditions.checkNotNull(str2, "Required parameter eventId must be specified.");
            }

            public Update setAlt(String str) {
                return (Update) super.setAlt(str);
            }

            public Update setFields(String str) {
                return (Update) super.setFields(str);
            }

            public Update setKey(String str) {
                return (Update) super.setKey(str);
            }

            public Update setOauthToken(String str) {
                return (Update) super.setOauthToken(str);
            }

            public Update setPrettyPrint(Boolean bool) {
                return (Update) super.setPrettyPrint(bool);
            }

            public Update setQuotaUser(String str) {
                return (Update) super.setQuotaUser(str);
            }

            public Update setUserIp(String str) {
                return (Update) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Update setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public String getEventId() {
                return this.eventId;
            }

            public Update setEventId(String str) {
                this.eventId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public Update setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public Integer getConferenceDataVersion() {
                return this.conferenceDataVersion;
            }

            public Update setConferenceDataVersion(Integer num) {
                this.conferenceDataVersion = num;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Update setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Boolean getSendNotifications() {
                return this.sendNotifications;
            }

            public Update setSendNotifications(Boolean bool) {
                this.sendNotifications = bool;
                return this;
            }

            public Boolean getSupportsAttachments() {
                return this.supportsAttachments;
            }

            public Update setSupportsAttachments(Boolean bool) {
                this.supportsAttachments = bool;
                return this;
            }

            public Update set(String str, Object obj) {
                return (Update) super.set(str, obj);
            }
        }

        public class Watch extends CalendarRequest<Channel> {
            private static final String REST_PATH = "calendars/{calendarId}/events/watch";
            @Key
            private Boolean alwaysIncludeEmail;
            @Key
            private String calendarId;
            @Key
            private String iCalUID;
            @Key
            private Integer maxAttendees;
            @Key
            private Integer maxResults;
            @Key
            private String orderBy;
            @Key
            private String pageToken;
            @Key
            private java.util.List<String> privateExtendedProperty;
            @Key
            private String q;
            @Key
            private java.util.List<String> sharedExtendedProperty;
            @Key
            private Boolean showDeleted;
            @Key
            private Boolean showHiddenInvitations;
            @Key
            private Boolean singleEvents;
            @Key
            private String syncToken;
            @Key
            private DateTime timeMax;
            @Key
            private DateTime timeMin;
            @Key
            private String timeZone;
            @Key
            private DateTime updatedMin;

            protected Watch(String str, Channel channel) {
                super(Calendar.this, "POST", REST_PATH, channel, Channel.class);
                this.calendarId = (String) Preconditions.checkNotNull(str, "Required parameter calendarId must be specified.");
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public String getCalendarId() {
                return this.calendarId;
            }

            public Watch setCalendarId(String str) {
                this.calendarId = str;
                return this;
            }

            public Boolean getAlwaysIncludeEmail() {
                return this.alwaysIncludeEmail;
            }

            public Watch setAlwaysIncludeEmail(Boolean bool) {
                this.alwaysIncludeEmail = bool;
                return this;
            }

            public String getICalUID() {
                return this.iCalUID;
            }

            public Watch setICalUID(String str) {
                this.iCalUID = str;
                return this;
            }

            public Integer getMaxAttendees() {
                return this.maxAttendees;
            }

            public Watch setMaxAttendees(Integer num) {
                this.maxAttendees = num;
                return this;
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Watch setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getOrderBy() {
                return this.orderBy;
            }

            public Watch setOrderBy(String str) {
                this.orderBy = str;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Watch setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public java.util.List<String> getPrivateExtendedProperty() {
                return this.privateExtendedProperty;
            }

            public Watch setPrivateExtendedProperty(java.util.List<String> list) {
                this.privateExtendedProperty = list;
                return this;
            }

            public String getQ() {
                return this.q;
            }

            public Watch setQ(String str) {
                this.q = str;
                return this;
            }

            public java.util.List<String> getSharedExtendedProperty() {
                return this.sharedExtendedProperty;
            }

            public Watch setSharedExtendedProperty(java.util.List<String> list) {
                this.sharedExtendedProperty = list;
                return this;
            }

            public Boolean getShowDeleted() {
                return this.showDeleted;
            }

            public Watch setShowDeleted(Boolean bool) {
                this.showDeleted = bool;
                return this;
            }

            public Boolean getShowHiddenInvitations() {
                return this.showHiddenInvitations;
            }

            public Watch setShowHiddenInvitations(Boolean bool) {
                this.showHiddenInvitations = bool;
                return this;
            }

            public Boolean getSingleEvents() {
                return this.singleEvents;
            }

            public Watch setSingleEvents(Boolean bool) {
                this.singleEvents = bool;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public Watch setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public DateTime getTimeMax() {
                return this.timeMax;
            }

            public Watch setTimeMax(DateTime dateTime) {
                this.timeMax = dateTime;
                return this;
            }

            public DateTime getTimeMin() {
                return this.timeMin;
            }

            public Watch setTimeMin(DateTime dateTime) {
                this.timeMin = dateTime;
                return this;
            }

            public String getTimeZone() {
                return this.timeZone;
            }

            public Watch setTimeZone(String str) {
                this.timeZone = str;
                return this;
            }

            public DateTime getUpdatedMin() {
                return this.updatedMin;
            }

            public Watch setUpdatedMin(DateTime dateTime) {
                this.updatedMin = dateTime;
                return this;
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public Events() {
        }

        public Delete delete(String str, String str2) throws IOException {
            Delete delete = new Delete(str, str2);
            Calendar.this.initialize(delete);
            return delete;
        }

        public Get get(String str, String str2) throws IOException {
            Get get = new Get(str, str2);
            Calendar.this.initialize(get);
            return get;
        }

        public CalendarImport calendarImport(String str, Event event) throws IOException {
            CalendarImport calendarImport = new CalendarImport(str, event);
            Calendar.this.initialize(calendarImport);
            return calendarImport;
        }

        public Insert insert(String str, Event event) throws IOException {
            Insert insert = new Insert(str, event);
            Calendar.this.initialize(insert);
            return insert;
        }

        public Instances instances(String str, String str2) throws IOException {
            Instances instances = new Instances(str, str2);
            Calendar.this.initialize(instances);
            return instances;
        }

        public List list(String str) throws IOException {
            List list = new List(str);
            Calendar.this.initialize(list);
            return list;
        }

        public Move move(String str, String str2, String str3) throws IOException {
            Move move = new Move(str, str2, str3);
            Calendar.this.initialize(move);
            return move;
        }

        public Patch patch(String str, String str2, Event event) throws IOException {
            Patch patch = new Patch(str, str2, event);
            Calendar.this.initialize(patch);
            return patch;
        }

        public QuickAdd quickAdd(String str, String str2) throws IOException {
            QuickAdd quickAdd = new QuickAdd(str, str2);
            Calendar.this.initialize(quickAdd);
            return quickAdd;
        }

        public Update update(String str, String str2, Event event) throws IOException {
            Update update = new Update(str, str2, event);
            Calendar.this.initialize(update);
            return update;
        }

        public Watch watch(String str, Channel channel) throws IOException {
            Watch watch = new Watch(str, channel);
            Calendar.this.initialize(watch);
            return watch;
        }
    }

    public class Freebusy {

        public class Query extends CalendarRequest<FreeBusyResponse> {
            private static final String REST_PATH = "freeBusy";

            protected Query(FreeBusyRequest freeBusyRequest) {
                super(Calendar.this, "POST", REST_PATH, freeBusyRequest, FreeBusyResponse.class);
            }

            public Query setAlt(String str) {
                return (Query) super.setAlt(str);
            }

            public Query setFields(String str) {
                return (Query) super.setFields(str);
            }

            public Query setKey(String str) {
                return (Query) super.setKey(str);
            }

            public Query setOauthToken(String str) {
                return (Query) super.setOauthToken(str);
            }

            public Query setPrettyPrint(Boolean bool) {
                return (Query) super.setPrettyPrint(bool);
            }

            public Query setQuotaUser(String str) {
                return (Query) super.setQuotaUser(str);
            }

            public Query setUserIp(String str) {
                return (Query) super.setUserIp(str);
            }

            public Query set(String str, Object obj) {
                return (Query) super.set(str, obj);
            }
        }

        public Freebusy() {
        }

        public Query query(FreeBusyRequest freeBusyRequest) throws IOException {
            Query query = new Query(freeBusyRequest);
            Calendar.this.initialize(query);
            return query;
        }
    }

    public class Settings {

        public class Get extends CalendarRequest<Setting> {
            private static final String REST_PATH = "users/me/settings/{setting}";
            @Key
            private String setting;

            protected Get(String str) {
                super(Calendar.this, "GET", REST_PATH, null, Setting.class);
                this.setting = (String) Preconditions.checkNotNull(str, "Required parameter setting must be specified.");
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public Get setAlt(String str) {
                return (Get) super.setAlt(str);
            }

            public Get setFields(String str) {
                return (Get) super.setFields(str);
            }

            public Get setKey(String str) {
                return (Get) super.setKey(str);
            }

            public Get setOauthToken(String str) {
                return (Get) super.setOauthToken(str);
            }

            public Get setPrettyPrint(Boolean bool) {
                return (Get) super.setPrettyPrint(bool);
            }

            public Get setQuotaUser(String str) {
                return (Get) super.setQuotaUser(str);
            }

            public Get setUserIp(String str) {
                return (Get) super.setUserIp(str);
            }

            public String getSetting() {
                return this.setting;
            }

            public Get setSetting(String str) {
                this.setting = str;
                return this;
            }

            public Get set(String str, Object obj) {
                return (Get) super.set(str, obj);
            }
        }

        public class List extends CalendarRequest<com.google.api.services.calendar.model.Settings> {
            private static final String REST_PATH = "users/me/settings";
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private String syncToken;

            protected List() {
                super(Calendar.this, "GET", REST_PATH, null, com.google.api.services.calendar.model.Settings.class);
            }

            public HttpResponse executeUsingHead() throws IOException {
                return super.executeUsingHead();
            }

            public HttpRequest buildHttpRequestUsingHead() throws IOException {
                return super.buildHttpRequestUsingHead();
            }

            public List setAlt(String str) {
                return (List) super.setAlt(str);
            }

            public List setFields(String str) {
                return (List) super.setFields(str);
            }

            public List setKey(String str) {
                return (List) super.setKey(str);
            }

            public List setOauthToken(String str) {
                return (List) super.setOauthToken(str);
            }

            public List setPrettyPrint(Boolean bool) {
                return (List) super.setPrettyPrint(bool);
            }

            public List setQuotaUser(String str) {
                return (List) super.setQuotaUser(str);
            }

            public List setUserIp(String str) {
                return (List) super.setUserIp(str);
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public List setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public List setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public List setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public List set(String str, Object obj) {
                return (List) super.set(str, obj);
            }
        }

        public class Watch extends CalendarRequest<Channel> {
            private static final String REST_PATH = "users/me/settings/watch";
            @Key
            private Integer maxResults;
            @Key
            private String pageToken;
            @Key
            private String syncToken;

            protected Watch(Channel channel) {
                super(Calendar.this, "POST", REST_PATH, channel, Channel.class);
            }

            public Watch setAlt(String str) {
                return (Watch) super.setAlt(str);
            }

            public Watch setFields(String str) {
                return (Watch) super.setFields(str);
            }

            public Watch setKey(String str) {
                return (Watch) super.setKey(str);
            }

            public Watch setOauthToken(String str) {
                return (Watch) super.setOauthToken(str);
            }

            public Watch setPrettyPrint(Boolean bool) {
                return (Watch) super.setPrettyPrint(bool);
            }

            public Watch setQuotaUser(String str) {
                return (Watch) super.setQuotaUser(str);
            }

            public Watch setUserIp(String str) {
                return (Watch) super.setUserIp(str);
            }

            public Integer getMaxResults() {
                return this.maxResults;
            }

            public Watch setMaxResults(Integer num) {
                this.maxResults = num;
                return this;
            }

            public String getPageToken() {
                return this.pageToken;
            }

            public Watch setPageToken(String str) {
                this.pageToken = str;
                return this;
            }

            public String getSyncToken() {
                return this.syncToken;
            }

            public Watch setSyncToken(String str) {
                this.syncToken = str;
                return this;
            }

            public Watch set(String str, Object obj) {
                return (Watch) super.set(str, obj);
            }
        }

        public Settings() {
        }

        public Get get(String str) throws IOException {
            Get get = new Get(str);
            Calendar.this.initialize(get);
            return get;
        }

        public List list() throws IOException {
            List list = new List();
            Calendar.this.initialize(list);
            return list;
        }

        public Watch watch(Channel channel) throws IOException {
            Watch watch = new Watch(channel);
            Calendar.this.initialize(watch);
            return watch;
        }
    }

    static {
        Preconditions.checkState(GoogleUtils.MAJOR_VERSION.intValue() == 1 && GoogleUtils.MINOR_VERSION.intValue() >= 15, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.23.0 of the Calendar API library.", GoogleUtils.VERSION);
    }

    public Calendar(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }

    Calendar(Builder builder) {
        super(builder);
    }

    /* access modifiers changed from: protected */
    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
    }

    public Acl acl() {
        return new Acl();
    }

    public CalendarList calendarList() {
        return new CalendarList();
    }

    public Calendars calendars() {
        return new Calendars();
    }

    public Channels channels() {
        return new Channels();
    }

    public Colors colors() {
        return new Colors();
    }

    public Events events() {
        return new Events();
    }

    public Freebusy freebusy() {
        return new Freebusy();
    }

    public Settings settings() {
        return new Settings();
    }
}
