package com.google.api.client.googleapis.notifications;

import com.google.api.client.util.Beta;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Objects.ToStringHelper;
import com.google.api.client.util.Preconditions;

@Beta
public abstract class AbstractNotification {
    private String changed;
    private String channelExpiration;
    private String channelId;
    private String channelToken;
    private long messageNumber;
    private String resourceId;
    private String resourceState;
    private String resourceUri;

    protected AbstractNotification(long messageNumber2, String resourceState2, String resourceId2, String resourceUri2, String channelId2) {
        setMessageNumber(messageNumber2);
        setResourceState(resourceState2);
        setResourceId(resourceId2);
        setResourceUri(resourceUri2);
        setChannelId(channelId2);
    }

    protected AbstractNotification(AbstractNotification source) {
        this(source.getMessageNumber(), source.getResourceState(), source.getResourceId(), source.getResourceUri(), source.getChannelId());
        setChannelExpiration(source.getChannelExpiration());
        setChannelToken(source.getChannelToken());
        setChanged(source.getChanged());
    }

    public String toString() {
        return toStringHelper().toString();
    }

    /* access modifiers changed from: protected */
    public ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this).add("messageNumber", Long.valueOf(this.messageNumber)).add("resourceState", this.resourceState).add("resourceId", this.resourceId).add("resourceUri", this.resourceUri).add("channelId", this.channelId).add("channelExpiration", this.channelExpiration).add("channelToken", this.channelToken).add("changed", this.changed);
    }

    public final long getMessageNumber() {
        return this.messageNumber;
    }

    public AbstractNotification setMessageNumber(long messageNumber2) {
        Preconditions.checkArgument(messageNumber2 >= 1);
        this.messageNumber = messageNumber2;
        return this;
    }

    public final String getResourceState() {
        return this.resourceState;
    }

    public AbstractNotification setResourceState(String resourceState2) {
        this.resourceState = (String) Preconditions.checkNotNull(resourceState2);
        return this;
    }

    public final String getResourceId() {
        return this.resourceId;
    }

    public AbstractNotification setResourceId(String resourceId2) {
        this.resourceId = (String) Preconditions.checkNotNull(resourceId2);
        return this;
    }

    public final String getResourceUri() {
        return this.resourceUri;
    }

    public AbstractNotification setResourceUri(String resourceUri2) {
        this.resourceUri = (String) Preconditions.checkNotNull(resourceUri2);
        return this;
    }

    public final String getChannelId() {
        return this.channelId;
    }

    public AbstractNotification setChannelId(String channelId2) {
        this.channelId = (String) Preconditions.checkNotNull(channelId2);
        return this;
    }

    public final String getChannelExpiration() {
        return this.channelExpiration;
    }

    public AbstractNotification setChannelExpiration(String channelExpiration2) {
        this.channelExpiration = channelExpiration2;
        return this;
    }

    public final String getChannelToken() {
        return this.channelToken;
    }

    public AbstractNotification setChannelToken(String channelToken2) {
        this.channelToken = channelToken2;
        return this;
    }

    public final String getChanged() {
        return this.changed;
    }

    public AbstractNotification setChanged(String changed2) {
        this.changed = changed2;
        return this;
    }
}
