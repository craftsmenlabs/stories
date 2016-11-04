package org.craftsmenlabs.stories.isolator.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"votes",
	"viewingMemberVoted",
	"subscribed",
	"fogbugz",
	"checkItems",
	"checkItemsChecked",
	"comments",
	"attachments",
	"description",
	"due"
})
public class Badges
{

	@JsonProperty("votes")
	private Integer votes;
	@JsonProperty("viewingMemberVoted")
	private Boolean viewingMemberVoted;
	@JsonProperty("subscribed")
	private Boolean subscribed;
	@JsonProperty("fogbugz")
	private String fogbugz;
	@JsonProperty("checkItems")
	private Integer checkItems;
	@JsonProperty("checkItemsChecked")
	private Integer checkItemsChecked;
	@JsonProperty("comments")
	private Integer comments;
	@JsonProperty("attachments")
	private Integer attachments;
	@JsonProperty("description")
	private Boolean description;
	@JsonProperty("due")
	private Object due;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * @return The votes
	 */
	@JsonProperty("votes")
	public Integer getVotes()
	{
		return votes;
	}

	/**
	 * @param votes The votes
	 */
	@JsonProperty("votes")
	public void setVotes(Integer votes)
	{
		this.votes = votes;
	}

	/**
	 * @return The viewingMemberVoted
	 */
	@JsonProperty("viewingMemberVoted")
	public Boolean getViewingMemberVoted()
	{
		return viewingMemberVoted;
	}

	/**
	 * @param viewingMemberVoted The viewingMemberVoted
	 */
	@JsonProperty("viewingMemberVoted")
	public void setViewingMemberVoted(Boolean viewingMemberVoted)
	{
		this.viewingMemberVoted = viewingMemberVoted;
	}

	/**
	 * @return The subscribed
	 */
	@JsonProperty("subscribed")
	public Boolean getSubscribed()
	{
		return subscribed;
	}

	/**
	 * @param subscribed The subscribed
	 */
	@JsonProperty("subscribed")
	public void setSubscribed(Boolean subscribed)
	{
		this.subscribed = subscribed;
	}

	/**
	 * @return The fogbugz
	 */
	@JsonProperty("fogbugz")
	public String getFogbugz()
	{
		return fogbugz;
	}

	/**
	 * @param fogbugz The fogbugz
	 */
	@JsonProperty("fogbugz")
	public void setFogbugz(String fogbugz)
	{
		this.fogbugz = fogbugz;
	}

	/**
	 * @return The checkItems
	 */
	@JsonProperty("checkItems")
	public Integer getCheckItems()
	{
		return checkItems;
	}

	/**
	 * @param checkItems The checkItems
	 */
	@JsonProperty("checkItems")
	public void setCheckItems(Integer checkItems)
	{
		this.checkItems = checkItems;
	}

	/**
	 * @return The checkItemsChecked
	 */
	@JsonProperty("checkItemsChecked")
	public Integer getCheckItemsChecked()
	{
		return checkItemsChecked;
	}

	/**
	 * @param checkItemsChecked The checkItemsChecked
	 */
	@JsonProperty("checkItemsChecked")
	public void setCheckItemsChecked(Integer checkItemsChecked)
	{
		this.checkItemsChecked = checkItemsChecked;
	}

	/**
	 * @return The comments
	 */
	@JsonProperty("comments")
	public Integer getComments()
	{
		return comments;
	}

	/**
	 * @param comments The comments
	 */
	@JsonProperty("comments")
	public void setComments(Integer comments)
	{
		this.comments = comments;
	}

	/**
	 * @return The attachments
	 */
	@JsonProperty("attachments")
	public Integer getAttachments()
	{
		return attachments;
	}

	/**
	 * @param attachments The attachments
	 */
	@JsonProperty("attachments")
	public void setAttachments(Integer attachments)
	{
		this.attachments = attachments;
	}

	/**
	 * @return The description
	 */
	@JsonProperty("description")
	public Boolean getDescription()
	{
		return description;
	}

	/**
	 * @param description The description
	 */
	@JsonProperty("description")
	public void setDescription(Boolean description)
	{
		this.description = description;
	}

	/**
	 * @return The due
	 */
	@JsonProperty("due")
	public Object getDue()
	{
		return due;
	}

	/**
	 * @param due The due
	 */
	@JsonProperty("due")
	public void setDue(Object due)
	{
		this.due = due;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value)
	{
		this.additionalProperties.put(name, value);
	}
}
