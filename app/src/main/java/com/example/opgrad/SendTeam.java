package com.example.opgrad;

public class SendTeam {

    String teamName;
    String compTitle;

    String member1Name;
    String member1State;

    String member2Name;
    String member2State;

    String member3Name;
    String member3State;

    String member4Name;
    String member4State;

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

    public SendTeam(String teamName, String compTitle,
                    String member1Name, String member1State,String member1Id,  String member1Distance,String member1Responsible,
                    String member2Name, String member2State,String member2Id,  String member2Distance,String member2Responsible,
                    String member3Name, String member3State,String member3Id,  String member3Distance,String member3Responsible,
                    String member4Name, String member4State ,String member4Id, String member4Distance,String member4Responsible ) {

        this.teamName = teamName;
        this.compTitle = compTitle;

        this.member1Name = member1Name;

        this.member1State = member1State;

        this.member2Name = member2Name;

        this.member2State = member2State;

        this.member3Name = member3Name;

        this.member3State = member3State;

        this.member4Name = member4Name;

        this.member4State = member4State;

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

    public String getMember1Name() {
        return member1Name;
    }

    public void setMember1Name(String member1Name) {
        this.member1Name = member1Name;
    }



    public String getMember1State() {
        return member1State;
    }

    public void setMember1State(String member1State) {
        this.member1State = member1State;
    }

    public String getMember2Name() {
        return member2Name;
    }

    public void setMember2Name(String member2Name) {
        this.member2Name = member2Name;
    }



    public String getMember2State() {
        return member2State;
    }

    public void setMember2State(String member2State) {
        this.member2State = member2State;
    }

    public String getMember3Name() {
        return member3Name;
    }

    public void setMember3Name(String member3Name) {
        this.member3Name = member3Name;
    }



    public String getMember3State() {
        return member3State;
    }

    public void setMember3State(String member3State) {
        this.member3State = member3State;
    }

    public String getMember4Name() {
        return member4Name;
    }

    public void setMember4Name(String member4Name) {
        this.member4Name = member4Name;
    }



    public String getMember4State() {
        return member4State;
    }

    public void setMember4State(String member4State) {
        this.member4State = member4State;
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
