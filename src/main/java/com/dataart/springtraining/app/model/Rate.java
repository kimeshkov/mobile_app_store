package com.dataart.springtraining.app.model;

import javax.persistence.*;

@Embeddable
public class Rate {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User rateByUser;

    @Column(name = "value")
    private Integer value;

    public User getRateByUser() {
        return rateByUser;
    }

    public void setRateByUser(User rateByUser) {
        this.rateByUser = rateByUser;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
