package com.optily.challenge.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Campaign extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5560221391479816650L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CampaignGroup campaignGroup;

    private Double budget;

    private Double impressions;

    private Double revenue;

    public Campaign() {
    }

    public Campaign(
            Long id,
            String name,
            Double budget,
            CampaignGroup campaignGroup,
            Double impressions,
            Double revenue) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.campaignGroup = campaignGroup;
        this.impressions = impressions;
        this.revenue = revenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return {@link #campaignGroup}
     */
    public CampaignGroup getCampaignGroup() {
        return campaignGroup;
    }

    /**
     * @param campaignGroup {@link #campaignGroup}
     * @return <source>this</source>
     */
    public Campaign setCampaignGroup(CampaignGroup campaignGroup) {
        this.campaignGroup = campaignGroup;
        return this;
    }

    /**
     * @return {@link #budget}
     */
    public Double getBudget() {
        return budget;
    }

    /**
     * @param budget {@link #budget}
     * @return <source>this</source>
     */
    public Campaign setBudget(Double budget) {
        this.budget = budget;
        return this;
    }

    /**
     * @return {@link #impressions}
     */
    public Double getImpressions() {
        return impressions;
    }

    /**
     * @param impressions {@link #impressions}
     * @return <source>this</source>
     */
    public Campaign setImpressions(Double impressions) {
        this.impressions = impressions;
        return this;
    }

    /**
     * @return {@link #revenue}
     */
    public Double getRevenue() {
        return revenue;
    }

    /**
     * @param revenue {@link #revenue}
     * @return <source>this</source>
     */
    public Campaign setRevenue(Double revenue) {
        this.revenue = revenue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return budget == campaign.budget &&
                impressions == campaign.impressions &&
                revenue == campaign.revenue &&
                Objects.equals(id, campaign.id) &&
                Objects.equals(name, campaign.name) &&
                Objects.equals(campaignGroup, campaign.campaignGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, campaignGroup, budget, impressions, revenue);
    }

    public static class Builder {
        private Long id;
        private String name;
        private CampaignGroup campaignGroup;
        private Double budget;
        private Double impressions;
        private Double revenue;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBudget(Double budget) {
            this.budget = budget;
            return this;
        }

        public Builder setCampaignGroup(CampaignGroup campaignGroup) {
            this.campaignGroup = campaignGroup;
            return this;
        }

        public Builder setImpressions(Double impressions) {
            this.impressions = impressions;
            return this;
        }

        public Builder setRevenue(Double revenue) {
            this.revenue = revenue;
            return this;
        }

        public Campaign build() {
            return new Campaign(id, name, budget, campaignGroup, impressions, revenue);
        }
    }
}
