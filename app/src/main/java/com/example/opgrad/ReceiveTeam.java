package com.example.opgrad;

public class ReceiveTeam {

    String teamName;    //recommend_id
    String compTitle;

    String teamLeaderName;

    String member1;
    String member2;
    String member3;

    String member1Id;
    String member2Id;
    String member3Id;
    String member4Id;

    String member1Distance;
    String member2Distance;
    String member3Distance;
    String member4Distance;

    String member1Responsible;
    String member2Responsible;
    String member3Responsible;
    String member4Responsible;
    public ReceiveTeam(String teamName, String compTitle,
                       String teamLeaderName,String member1Id ,String member1Distance, String member1Responsible,
                       String member1       ,String member2Id ,String member2Distance, String member2Responsible,
                       String member2       ,String member3Id ,String member3Distance, String member3Responsible,
                       String member3       ,String member4Id ,String member4Distance, String member4Responsible               ) {

        this.teamName = teamName;

        this.compTitle = compTitle;

        this.teamLeaderName = teamLeaderName;

        this.member1 = member1;
        this.member2 = member2;
        this.member3 = member3;

        this.member1Id = member1Id;
        this.member2Id = member2Id;
        this.member3Id = member3Id;
        this.member4Id = member4Id;

        this.member1Distance=member1Distance;
        this.member2Distance=member2Distance;
        this.member3Distance=member3Distance;
        this.member4Distance=member4Distance;

        this.member1Responsible=member1Responsible;
        this.member2Responsible=member2Responsible;
        this.member3Responsible=member3Responsible;
        this.member4Responsible=member4Responsible;

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCompTitle() {
        return compTitle;
    }

    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }

    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
    }

    public String getMember3() {
        return member3;
    }

    public void setMember3(String member3) {
        this.member3 = member3;
    }

    public String getMember1Id() {
        return member1Id;
    }
    public String getMember2Id() {
        return member2Id;
    }
    public String getMember3Id() {
        return member3Id;
    }
    public String getMember4Id() {
        return member4Id;
    }


    public String getMember1Distance() {
        return member1Distance;
    }
    public String getMember2Distance() {
        return member2Distance;
    }
    public String getMember3Distance() {
        return member3Distance;
    }
    public String getMember4Distance() {
        return member4Distance;
    }

    public String getMember1Responsible() {
        return member1Responsible;
    }
    public String getMember2Responsible() {
        return member2Responsible;
    }
    public String getMember3Responsible() {
        return member3Responsible;
    }
    public String getMember4Responsible() {
        return member4Responsible;
    }

}
