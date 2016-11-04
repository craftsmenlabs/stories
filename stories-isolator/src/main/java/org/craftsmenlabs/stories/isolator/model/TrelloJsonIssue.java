
package org.craftsmenlabs.stories.isolator.model;

import java.util.*;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "checkItemStates",
    "closed",
    "dateLastActivity",
    "desc",
    "descData",
    "idBoard",
    "idList",
    "idMembersVoted",
    "idShort",
    "idAttachmentCover",
    "manualCoverAttachment",
    "idLabels",
    "name",
    "pos",
    "shortLink",
    "badges",
    "due",
    "idChecklists",
    "idMembers",
    "labels",
    "shortUrl",
    "subscribed",
    "url"
})
public class TrelloJsonIssue
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("checkItemStates")
    private Object checkItemStates;
    @JsonProperty("closed")
    private Boolean closed;
    @JsonProperty("dateLastActivity")
    private String dateLastActivity;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("descData")
    private Object descData;
    @JsonProperty("idBoard")
    private String idBoard;
    @JsonProperty("idList")
    private String idList;
    @JsonProperty("idMembersVoted")
    private List<Object> idMembersVoted = new ArrayList<Object>();
    @JsonProperty("idShort")
    private Integer idShort;
    @JsonProperty("idAttachmentCover")
    private Object idAttachmentCover;
    @JsonProperty("manualCoverAttachment")
    private Boolean manualCoverAttachment;
    @JsonProperty("idLabels")
    private List<Object> idLabels = new ArrayList<Object>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("pos")
    private Integer pos;
    @JsonProperty("shortLink")
    private String shortLink;
    @JsonProperty("badges")
    private Badges badges;
    @JsonProperty("due")
    private Object due;
    @JsonProperty("idChecklists")
    private List<Object> idChecklists = new ArrayList<Object>();
    @JsonProperty("idMembers")
    private List<Object> idMembers = new ArrayList<Object>();
    @JsonProperty("labels")
    private List<Object> labels = new ArrayList<Object>();
    @JsonProperty("shortUrl")
    private String shortUrl;
    @JsonProperty("subscribed")
    private Boolean subscribed;
    @JsonProperty("url")
    private String url;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The checkItemStates
     */
    @JsonProperty("checkItemStates")
    public Object getCheckItemStates() {
        return checkItemStates;
    }

    /**
     *
     * @param checkItemStates
     * The checkItemStates
     */
    @JsonProperty("checkItemStates")
    public void setCheckItemStates(Object checkItemStates) {
        this.checkItemStates = checkItemStates;
    }

    /**
     *
     * @return
     * The closed
     */
    @JsonProperty("closed")
    public Boolean getClosed() {
        return closed;
    }

    /**
     *
     * @param closed
     * The closed
     */
    @JsonProperty("closed")
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    /**
     *
     * @return
     * The dateLastActivity
     */
    @JsonProperty("dateLastActivity")
    public String getDateLastActivity() {
        return dateLastActivity;
    }

    /**
     *
     * @param dateLastActivity
     * The dateLastActivity
     */
    @JsonProperty("dateLastActivity")
    public void setDateLastActivity(String dateLastActivity) {
        this.dateLastActivity = dateLastActivity;
    }

    /**
     *
     * @return
     * The desc
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     * The desc
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     * The descData
     */
    @JsonProperty("descData")
    public Object getDescData() {
        return descData;
    }

    /**
     *
     * @param descData
     * The descData
     */
    @JsonProperty("descData")
    public void setDescData(Object descData) {
        this.descData = descData;
    }

    /**
     *
     * @return
     * The idBoard
     */
    @JsonProperty("idBoard")
    public String getIdBoard() {
        return idBoard;
    }

    /**
     *
     * @param idBoard
     * The idBoard
     */
    @JsonProperty("idBoard")
    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    /**
     *
     * @return
     * The idList
     */
    @JsonProperty("idList")
    public String getIdList() {
        return idList;
    }

    /**
     *
     * @param idList
     * The idList
     */
    @JsonProperty("idList")
    public void setIdList(String idList) {
        this.idList = idList;
    }

    /**
     *
     * @return
     * The idMembersVoted
     */
    @JsonProperty("idMembersVoted")
    public List<Object> getIdMembersVoted() {
        return idMembersVoted;
    }

    /**
     *
     * @param idMembersVoted
     * The idMembersVoted
     */
    @JsonProperty("idMembersVoted")
    public void setIdMembersVoted(List<Object> idMembersVoted) {
        this.idMembersVoted = idMembersVoted;
    }

    /**
     *
     * @return
     * The idShort
     */
    @JsonProperty("idShort")
    public Integer getIdShort() {
        return idShort;
    }

    /**
     *
     * @param idShort
     * The idShort
     */
    @JsonProperty("idShort")
    public void setIdShort(Integer idShort) {
        this.idShort = idShort;
    }

    /**
     *
     * @return
     * The idAttachmentCover
     */
    @JsonProperty("idAttachmentCover")
    public Object getIdAttachmentCover() {
        return idAttachmentCover;
    }

    /**
     *
     * @param idAttachmentCover
     * The idAttachmentCover
     */
    @JsonProperty("idAttachmentCover")
    public void setIdAttachmentCover(Object idAttachmentCover) {
        this.idAttachmentCover = idAttachmentCover;
    }

    /**
     *
     * @return
     * The manualCoverAttachment
     */
    @JsonProperty("manualCoverAttachment")
    public Boolean getManualCoverAttachment() {
        return manualCoverAttachment;
    }

    /**
     *
     * @param manualCoverAttachment
     * The manualCoverAttachment
     */
    @JsonProperty("manualCoverAttachment")
    public void setManualCoverAttachment(Boolean manualCoverAttachment) {
        this.manualCoverAttachment = manualCoverAttachment;
    }

    /**
     *
     * @return
     * The idLabels
     */
    @JsonProperty("idLabels")
    public List<Object> getIdLabels() {
        return idLabels;
    }

    /**
     *
     * @param idLabels
     * The idLabels
     */
    @JsonProperty("idLabels")
    public void setIdLabels(List<Object> idLabels) {
        this.idLabels = idLabels;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The pos
     */
    @JsonProperty("pos")
    public Integer getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     * The pos
     */
    @JsonProperty("pos")
    public void setPos(Integer pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     * The shortLink
     */
    @JsonProperty("shortLink")
    public String getShortLink() {
        return shortLink;
    }

    /**
     *
     * @param shortLink
     * The shortLink
     */
    @JsonProperty("shortLink")
    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    /**
     *
     * @return
     * The badges
     */
    @JsonProperty("badges")
    public Badges getBadges() {
        return badges;
    }

    /**
     *
     * @param badges
     * The badges
     */
    @JsonProperty("badges")
    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    /**
     *
     * @return
     * The due
     */
    @JsonProperty("due")
    public Object getDue() {
        return due;
    }

    /**
     *
     * @param due
     * The due
     */
    @JsonProperty("due")
    public void setDue(Object due) {
        this.due = due;
    }

    /**
     *
     * @return
     * The idChecklists
     */
    @JsonProperty("idChecklists")
    public List<Object> getIdChecklists() {
        return idChecklists;
    }

    /**
     *
     * @param idChecklists
     * The idChecklists
     */
    @JsonProperty("idChecklists")
    public void setIdChecklists(List<Object> idChecklists) {
        this.idChecklists = idChecklists;
    }

    /**
     *
     * @return
     * The idMembers
     */
    @JsonProperty("idMembers")
    public List<Object> getIdMembers() {
        return idMembers;
    }

    /**
     *
     * @param idMembers
     * The idMembers
     */
    @JsonProperty("idMembers")
    public void setIdMembers(List<Object> idMembers) {
        this.idMembers = idMembers;
    }

    /**
     *
     * @return
     * The labels
     */
    @JsonProperty("labels")
    public List<Object> getLabels() {
        return labels;
    }

    /**
     *
     * @param labels
     * The labels
     */
    @JsonProperty("labels")
    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    /**
     *
     * @return
     * The shortUrl
     */
    @JsonProperty("shortUrl")
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     *
     * @param shortUrl
     * The shortUrl
     */
    @JsonProperty("shortUrl")
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     *
     * @return
     * The subscribed
     */
    @JsonProperty("subscribed")
    public Boolean getSubscribed() {
        return subscribed;
    }

    /**
     *
     * @param subscribed
     * The subscribed
     */
    @JsonProperty("subscribed")
    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    /**
     *
     * @return
     * The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
