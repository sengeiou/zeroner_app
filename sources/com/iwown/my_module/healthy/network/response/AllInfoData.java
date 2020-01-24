package com.iwown.my_module.healthy.network.response;

public class AllInfoData {
    private AllUserInfoData account;
    private AllGoalData goal;
    private AllHealthData health;
    private AllWeightData weight;

    public AllWeightData getWeight() {
        return this.weight;
    }

    public void setWeight(AllWeightData weight2) {
        this.weight = weight2;
    }

    public AllUserInfoData getAccount() {
        return this.account;
    }

    public void setAccount(AllUserInfoData account2) {
        this.account = account2;
    }

    public AllHealthData getHealth() {
        return this.health;
    }

    public void setHealth(AllHealthData health2) {
        this.health = health2;
    }

    public AllGoalData getGoal() {
        return this.goal;
    }

    public void setGoal(AllGoalData goal2) {
        this.goal = goal2;
    }
}
