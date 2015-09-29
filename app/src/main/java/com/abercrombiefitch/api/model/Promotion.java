package com.abercrombiefitch.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "button",
        "description",
        "footer",
        "image",
        "title"
})
public class Promotion {

    @JsonProperty("button")
    private List<Button> button = new ArrayList<Button>();
    @JsonProperty("description")
    private String description;
    @JsonProperty("footer")
    private String footer;
    @JsonProperty("image")
    private String image;
    @JsonProperty("title")
    private String title;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The button
     */
    @JsonProperty("button")
    public List<Button> getButton() {
        return button;
    }

    /**
     *
     * @param button
     * The button
     */
    @JsonProperty("button")
    public void setButton(List<Button> button) {
        this.button = button;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The footer
     */
    @JsonProperty("footer")
    public String getFooter() {
        return footer;
    }

    /**
     *
     * @param footer
     * The footer
     */
    @JsonProperty("footer")
    public void setFooter(String footer) {
        this.footer = footer;
    }

    /**
     *
     * @return
     * The image
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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