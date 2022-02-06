package com.optily.challenge.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Optimisation extends AbstractEntity implements Serializable, Comparable<Optimisation> {
    private static final long serialVersionUID = 5560221391479816650L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CampaignGroup campaignGroup;

    private Status status;

    public Optimisation() {
    }

    public Optimisation(
            Long id,
            CampaignGroup campaignGroup,
            Status status) {
        this.id = id;
        this.campaignGroup = campaignGroup;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Optimisation setCampaignGroup(CampaignGroup campaignGroup) {
        this.campaignGroup = campaignGroup;
        return this;
    }

    /**
     * @return {@link #status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status {@link #status}
     * @return <source>this</source>
     */
    public Optimisation setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Optimisation that = (Optimisation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(campaignGroup, that.campaignGroup) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campaignGroup, status);
    }

    @Override
    public int compareTo(Optimisation o) {
        return (int) (o.getCreated().getTime() - this.getCreated().getTime());
    }

    public static class Builder {
        private Long id;
        private CampaignGroup campaignGroup;
        private Status status;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCampaignGroup(CampaignGroup campaignGroup) {
            this.campaignGroup = campaignGroup;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Optimisation build() {
            return new Optimisation(id, campaignGroup, status);
        }
    }
}
