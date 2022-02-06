package com.optily.challenge.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Recommendation extends AbstractEntity implements Serializable, Comparable<Recommendation> {

    private static final long serialVersionUID = 5560221391479816650L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Optimisation optimisation;

    private Double budget;

    public Recommendation() {
    }

    public Recommendation(
            Long id,
            Campaign campaign,
            Optimisation optimisation,
            Double budget) {
        this.id = id;
        this.campaign = campaign;
        this.optimisation = optimisation;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return {@link #campaign}
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * @param campaign {@link #campaign}
     * @return <source>this</source>
     */
    public Recommendation setCampaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    /**
     * @return {@link #optimisation}
     */
    public Optimisation getOptimisation() {
        return optimisation;
    }

    /**
     * @param optimisation {@link #optimisation}
     * @return <source>this</source>
     */
    public Recommendation setOptimisation(Optimisation optimisation) {
        this.optimisation = optimisation;
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
    public Recommendation setBudget(Double budget) {
        this.budget = budget;
        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(Recommendation o) {
        return (int) (o.getCreated().getTime() - this.getCreated().getTime());
    }


    public static class Builder {
        private Long id;
        private Campaign campaign;
        private Optimisation optimisation;
        private Double budget;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCampaign(Campaign campaign) {
            this.campaign = campaign;
            return this;
        }

        public Builder setOptimisation(Optimisation optimisation) {
            this.optimisation = optimisation;
            return this;
        }

        public Builder setBudget(Double budget) {
            this.budget = budget;
            return this;
        }

        public Recommendation build() {
            return new Recommendation(id, campaign, optimisation, budget);
        }
    }
}
