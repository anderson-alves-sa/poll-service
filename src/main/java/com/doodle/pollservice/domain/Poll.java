package com.doodle.pollservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "polls")
public final class Poll {

    public Poll() {

    }

    public Poll(String id, String title, Date createdOn, String email) {
        this.id = id;
        this.title = title;
        this.createdOn = createdOn;
        this.initiator = new Initiator();
        this.initiator.setEmail(email);
    }

    @Id
    private String id;
    private String adminKey;
    private LocalDateTime latestChange;
    private LocalDateTime initiated;
    private Integer participantsCount;
    private Integer inviteesCount;
    private Type type;
    private Boolean hidden;
    private PreferencesType preferencesType;
    private State state;
    private String locale;
    @Indexed
    private String title;
    private Initiator initiator;
    private List<Option> options;
    private String optionsHash;
    private List<Participant> participants;
    private List<Integer> invitees;
    private Boolean multiDay;
    private String device;
    private String levels;
    private Date createdOn;

    public enum Type {
        TEXT, DATE
    }

    public enum State {
        OPEN
    }

    public enum PreferencesType {
        YESNOIFNEEDBE, YESNO
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    public LocalDateTime getLatestChange() {
        return latestChange;
    }

    public void setLatestChange(LocalDateTime latestChange) {
        this.latestChange = latestChange;
    }

    public LocalDateTime getInitiated() {
        return initiated;
    }

    public void setInitiated(LocalDateTime initiated) {
        this.initiated = initiated;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public Integer getInviteesCount() {
        return inviteesCount;
    }

    public void setInviteesCount(Integer inviteesCount) {
        this.inviteesCount = inviteesCount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public PreferencesType getPreferencesType() {
        return preferencesType;
    }

    public void setPreferencesType(PreferencesType preferencesType) {
        this.preferencesType = preferencesType;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Initiator getInitiator() {
        return initiator;
    }

    public void setInitiator(Initiator initiator) {
        this.initiator = initiator;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getOptionsHash() {
        return optionsHash;
    }

    public void setOptionsHash(String optionsHash) {
        this.optionsHash = optionsHash;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public List<Integer> getInvitees() {
        return invitees;
    }

    public void setInvitees(List<Integer> invitees) {
        this.invitees = invitees;
    }

    public Boolean getMultiDay() {
        return multiDay;
    }

    public void setMultiDay(Boolean multiDay) {
        this.multiDay = multiDay;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
