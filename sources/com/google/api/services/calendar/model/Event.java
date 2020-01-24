package com.google.api.services.calendar.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

public final class Event extends GenericJson {
    @Key
    private Boolean anyoneCanAddSelf;
    @Key
    private List<EventAttachment> attachments;
    @Key
    private List<EventAttendee> attendees;
    @Key
    private Boolean attendeesOmitted;
    @Key
    private String colorId;
    @Key
    private ConferenceData conferenceData;
    @Key
    private DateTime created;
    @Key
    private Creator creator;
    @Key
    private String description;
    @Key
    private EventDateTime end;
    @Key
    private Boolean endTimeUnspecified;
    @Key
    private String etag;
    @Key
    private ExtendedProperties extendedProperties;
    @Key
    private Gadget gadget;
    @Key
    private Boolean guestsCanInviteOthers;
    @Key
    private Boolean guestsCanModify;
    @Key
    private Boolean guestsCanSeeOtherGuests;
    @Key
    private String hangoutLink;
    @Key
    private String htmlLink;
    @Key
    private String iCalUID;
    @Key
    private String id;
    @Key
    private String kind;
    @Key
    private String location;
    @Key
    private Boolean locked;
    @Key
    private Organizer organizer;
    @Key
    private EventDateTime originalStartTime;
    @Key
    private Boolean privateCopy;
    @Key
    private List<String> recurrence;
    @Key
    private String recurringEventId;
    @Key
    private Reminders reminders;
    @Key
    private Integer sequence;
    @Key
    private Source source;
    @Key
    private EventDateTime start;
    @Key
    private String status;
    @Key
    private String summary;
    @Key
    private String transparency;
    @Key
    private DateTime updated;
    @Key
    private String visibility;

    public static final class Creator extends GenericJson {
        @Key
        private String displayName;
        @Key
        private String email;
        @Key
        private String id;
        @Key
        private Boolean self;

        public String getDisplayName() {
            return this.displayName;
        }

        public Creator setDisplayName(String str) {
            this.displayName = str;
            return this;
        }

        public String getEmail() {
            return this.email;
        }

        public Creator setEmail(String str) {
            this.email = str;
            return this;
        }

        public String getId() {
            return this.id;
        }

        public Creator setId(String str) {
            this.id = str;
            return this;
        }

        public Boolean getSelf() {
            return this.self;
        }

        public Creator setSelf(Boolean bool) {
            this.self = bool;
            return this;
        }

        public boolean isSelf() {
            if (this.self == null || this.self == Data.NULL_BOOLEAN) {
                return false;
            }
            return this.self.booleanValue();
        }

        public Creator set(String str, Object obj) {
            return (Creator) super.set(str, obj);
        }

        public Creator clone() {
            return (Creator) super.clone();
        }
    }

    public static final class ExtendedProperties extends GenericJson {
        @Key("private")
        private Map<String, String> private__;
        @Key
        private Map<String, String> shared;

        public Map<String, String> getPrivate() {
            return this.private__;
        }

        public ExtendedProperties setPrivate(Map<String, String> map) {
            this.private__ = map;
            return this;
        }

        public Map<String, String> getShared() {
            return this.shared;
        }

        public ExtendedProperties setShared(Map<String, String> map) {
            this.shared = map;
            return this;
        }

        public ExtendedProperties set(String str, Object obj) {
            return (ExtendedProperties) super.set(str, obj);
        }

        public ExtendedProperties clone() {
            return (ExtendedProperties) super.clone();
        }
    }

    public static final class Gadget extends GenericJson {
        @Key
        private String display;
        @Key
        private Integer height;
        @Key
        private String iconLink;
        @Key
        private String link;
        @Key
        private Map<String, String> preferences;
        @Key
        private String title;
        @Key
        private String type;
        @Key
        private Integer width;

        public String getDisplay() {
            return this.display;
        }

        public Gadget setDisplay(String str) {
            this.display = str;
            return this;
        }

        public Integer getHeight() {
            return this.height;
        }

        public Gadget setHeight(Integer num) {
            this.height = num;
            return this;
        }

        public String getIconLink() {
            return this.iconLink;
        }

        public Gadget setIconLink(String str) {
            this.iconLink = str;
            return this;
        }

        public String getLink() {
            return this.link;
        }

        public Gadget setLink(String str) {
            this.link = str;
            return this;
        }

        public Map<String, String> getPreferences() {
            return this.preferences;
        }

        public Gadget setPreferences(Map<String, String> map) {
            this.preferences = map;
            return this;
        }

        public String getTitle() {
            return this.title;
        }

        public Gadget setTitle(String str) {
            this.title = str;
            return this;
        }

        public String getType() {
            return this.type;
        }

        public Gadget setType(String str) {
            this.type = str;
            return this;
        }

        public Integer getWidth() {
            return this.width;
        }

        public Gadget setWidth(Integer num) {
            this.width = num;
            return this;
        }

        public Gadget set(String str, Object obj) {
            return (Gadget) super.set(str, obj);
        }

        public Gadget clone() {
            return (Gadget) super.clone();
        }
    }

    public static final class Organizer extends GenericJson {
        @Key
        private String displayName;
        @Key
        private String email;
        @Key
        private String id;
        @Key
        private Boolean self;

        public String getDisplayName() {
            return this.displayName;
        }

        public Organizer setDisplayName(String str) {
            this.displayName = str;
            return this;
        }

        public String getEmail() {
            return this.email;
        }

        public Organizer setEmail(String str) {
            this.email = str;
            return this;
        }

        public String getId() {
            return this.id;
        }

        public Organizer setId(String str) {
            this.id = str;
            return this;
        }

        public Boolean getSelf() {
            return this.self;
        }

        public Organizer setSelf(Boolean bool) {
            this.self = bool;
            return this;
        }

        public boolean isSelf() {
            if (this.self == null || this.self == Data.NULL_BOOLEAN) {
                return false;
            }
            return this.self.booleanValue();
        }

        public Organizer set(String str, Object obj) {
            return (Organizer) super.set(str, obj);
        }

        public Organizer clone() {
            return (Organizer) super.clone();
        }
    }

    public static final class Reminders extends GenericJson {
        @Key
        private List<EventReminder> overrides;
        @Key
        private Boolean useDefault;

        public List<EventReminder> getOverrides() {
            return this.overrides;
        }

        public Reminders setOverrides(List<EventReminder> list) {
            this.overrides = list;
            return this;
        }

        public Boolean getUseDefault() {
            return this.useDefault;
        }

        public Reminders setUseDefault(Boolean bool) {
            this.useDefault = bool;
            return this;
        }

        public Reminders set(String str, Object obj) {
            return (Reminders) super.set(str, obj);
        }

        public Reminders clone() {
            return (Reminders) super.clone();
        }
    }

    public static final class Source extends GenericJson {
        @Key
        private String title;
        @Key
        private String url;

        public String getTitle() {
            return this.title;
        }

        public Source setTitle(String str) {
            this.title = str;
            return this;
        }

        public String getUrl() {
            return this.url;
        }

        public Source setUrl(String str) {
            this.url = str;
            return this;
        }

        public Source set(String str, Object obj) {
            return (Source) super.set(str, obj);
        }

        public Source clone() {
            return (Source) super.clone();
        }
    }

    public Boolean getAnyoneCanAddSelf() {
        return this.anyoneCanAddSelf;
    }

    public Event setAnyoneCanAddSelf(Boolean bool) {
        this.anyoneCanAddSelf = bool;
        return this;
    }

    public boolean isAnyoneCanAddSelf() {
        if (this.anyoneCanAddSelf == null || this.anyoneCanAddSelf == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.anyoneCanAddSelf.booleanValue();
    }

    public List<EventAttachment> getAttachments() {
        return this.attachments;
    }

    public Event setAttachments(List<EventAttachment> list) {
        this.attachments = list;
        return this;
    }

    public List<EventAttendee> getAttendees() {
        return this.attendees;
    }

    public Event setAttendees(List<EventAttendee> list) {
        this.attendees = list;
        return this;
    }

    public Boolean getAttendeesOmitted() {
        return this.attendeesOmitted;
    }

    public Event setAttendeesOmitted(Boolean bool) {
        this.attendeesOmitted = bool;
        return this;
    }

    public boolean isAttendeesOmitted() {
        if (this.attendeesOmitted == null || this.attendeesOmitted == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.attendeesOmitted.booleanValue();
    }

    public String getColorId() {
        return this.colorId;
    }

    public Event setColorId(String str) {
        this.colorId = str;
        return this;
    }

    public ConferenceData getConferenceData() {
        return this.conferenceData;
    }

    public Event setConferenceData(ConferenceData conferenceData2) {
        this.conferenceData = conferenceData2;
        return this;
    }

    public DateTime getCreated() {
        return this.created;
    }

    public Event setCreated(DateTime dateTime) {
        this.created = dateTime;
        return this;
    }

    public Creator getCreator() {
        return this.creator;
    }

    public Event setCreator(Creator creator2) {
        this.creator = creator2;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Event setDescription(String str) {
        this.description = str;
        return this;
    }

    public EventDateTime getEnd() {
        return this.end;
    }

    public Event setEnd(EventDateTime eventDateTime) {
        this.end = eventDateTime;
        return this;
    }

    public Boolean getEndTimeUnspecified() {
        return this.endTimeUnspecified;
    }

    public Event setEndTimeUnspecified(Boolean bool) {
        this.endTimeUnspecified = bool;
        return this;
    }

    public boolean isEndTimeUnspecified() {
        if (this.endTimeUnspecified == null || this.endTimeUnspecified == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.endTimeUnspecified.booleanValue();
    }

    public String getEtag() {
        return this.etag;
    }

    public Event setEtag(String str) {
        this.etag = str;
        return this;
    }

    public ExtendedProperties getExtendedProperties() {
        return this.extendedProperties;
    }

    public Event setExtendedProperties(ExtendedProperties extendedProperties2) {
        this.extendedProperties = extendedProperties2;
        return this;
    }

    public Gadget getGadget() {
        return this.gadget;
    }

    public Event setGadget(Gadget gadget2) {
        this.gadget = gadget2;
        return this;
    }

    public Boolean getGuestsCanInviteOthers() {
        return this.guestsCanInviteOthers;
    }

    public Event setGuestsCanInviteOthers(Boolean bool) {
        this.guestsCanInviteOthers = bool;
        return this;
    }

    public boolean isGuestsCanInviteOthers() {
        if (this.guestsCanInviteOthers == null || this.guestsCanInviteOthers == Data.NULL_BOOLEAN) {
            return true;
        }
        return this.guestsCanInviteOthers.booleanValue();
    }

    public Boolean getGuestsCanModify() {
        return this.guestsCanModify;
    }

    public Event setGuestsCanModify(Boolean bool) {
        this.guestsCanModify = bool;
        return this;
    }

    public boolean isGuestsCanModify() {
        if (this.guestsCanModify == null || this.guestsCanModify == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.guestsCanModify.booleanValue();
    }

    public Boolean getGuestsCanSeeOtherGuests() {
        return this.guestsCanSeeOtherGuests;
    }

    public Event setGuestsCanSeeOtherGuests(Boolean bool) {
        this.guestsCanSeeOtherGuests = bool;
        return this;
    }

    public boolean isGuestsCanSeeOtherGuests() {
        if (this.guestsCanSeeOtherGuests == null || this.guestsCanSeeOtherGuests == Data.NULL_BOOLEAN) {
            return true;
        }
        return this.guestsCanSeeOtherGuests.booleanValue();
    }

    public String getHangoutLink() {
        return this.hangoutLink;
    }

    public Event setHangoutLink(String str) {
        this.hangoutLink = str;
        return this;
    }

    public String getHtmlLink() {
        return this.htmlLink;
    }

    public Event setHtmlLink(String str) {
        this.htmlLink = str;
        return this;
    }

    public String getICalUID() {
        return this.iCalUID;
    }

    public Event setICalUID(String str) {
        this.iCalUID = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Event setId(String str) {
        this.id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Event setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public Event setLocation(String str) {
        this.location = str;
        return this;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public Event setLocked(Boolean bool) {
        this.locked = bool;
        return this;
    }

    public boolean isLocked() {
        if (this.locked == null || this.locked == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.locked.booleanValue();
    }

    public Organizer getOrganizer() {
        return this.organizer;
    }

    public Event setOrganizer(Organizer organizer2) {
        this.organizer = organizer2;
        return this;
    }

    public EventDateTime getOriginalStartTime() {
        return this.originalStartTime;
    }

    public Event setOriginalStartTime(EventDateTime eventDateTime) {
        this.originalStartTime = eventDateTime;
        return this;
    }

    public Boolean getPrivateCopy() {
        return this.privateCopy;
    }

    public Event setPrivateCopy(Boolean bool) {
        this.privateCopy = bool;
        return this;
    }

    public boolean isPrivateCopy() {
        if (this.privateCopy == null || this.privateCopy == Data.NULL_BOOLEAN) {
            return false;
        }
        return this.privateCopy.booleanValue();
    }

    public List<String> getRecurrence() {
        return this.recurrence;
    }

    public Event setRecurrence(List<String> list) {
        this.recurrence = list;
        return this;
    }

    public String getRecurringEventId() {
        return this.recurringEventId;
    }

    public Event setRecurringEventId(String str) {
        this.recurringEventId = str;
        return this;
    }

    public Reminders getReminders() {
        return this.reminders;
    }

    public Event setReminders(Reminders reminders2) {
        this.reminders = reminders2;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public Event setSequence(Integer num) {
        this.sequence = num;
        return this;
    }

    public Source getSource() {
        return this.source;
    }

    public Event setSource(Source source2) {
        this.source = source2;
        return this;
    }

    public EventDateTime getStart() {
        return this.start;
    }

    public Event setStart(EventDateTime eventDateTime) {
        this.start = eventDateTime;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Event setStatus(String str) {
        this.status = str;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public Event setSummary(String str) {
        this.summary = str;
        return this;
    }

    public String getTransparency() {
        return this.transparency;
    }

    public Event setTransparency(String str) {
        this.transparency = str;
        return this;
    }

    public DateTime getUpdated() {
        return this.updated;
    }

    public Event setUpdated(DateTime dateTime) {
        this.updated = dateTime;
        return this;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public Event setVisibility(String str) {
        this.visibility = str;
        return this;
    }

    public Event set(String str, Object obj) {
        return (Event) super.set(str, obj);
    }

    public Event clone() {
        return (Event) super.clone();
    }
}
