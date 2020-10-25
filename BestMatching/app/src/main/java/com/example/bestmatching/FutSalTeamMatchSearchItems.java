package com.example.bestmatching;

public class FutSalTeamMatchSearchItems {

    private String teamtitleStr;
    private String teamgroundStr;
    private String teamstartTimeStr;
    private String teamendTimeStr;
    private String teamparticipantsStr;
    private String teammaxUserStr;

    public void setTeamTitle(String title) {
        teamtitleStr = title;
    }

    public void setTeamGround(String ground) {
        teamgroundStr = ground;
    }


    public void setTeamStartTime(String start_time) {
        teamstartTimeStr = start_time;
    }

    public void setTeamEndtime(String end_time) {
        teamendTimeStr = end_time;
    }

    public void setTeamParticipants(String participants) {
        teamparticipantsStr = participants;
    }

    public void setTeamMaxUser(String max_user) {
        teammaxUserStr = max_user;
    }

    ////////////////////////////////////////////


    public String getTeamTitle() {
        return this.teamtitleStr;
    }

    public String getTeamGround() {
        return this.teamgroundStr;
    }

    public String getTeamStartTime() {
        return this.teamstartTimeStr;
    }

    public String getTeamEndTime() {
        return this.teamendTimeStr;
    }

    public String getTeamParticipants() {
        return this.teamparticipantsStr;
    }

    public String getTeamMaxUser() {
        return this.teammaxUserStr;
    }


}
