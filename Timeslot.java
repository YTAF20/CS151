package s25.cs151.application.model;

import java.io.Serializable;

public class Timeslot implements Serializable {
    private String startHour;
    private String startMinute;
    private String startAmPm;
    private String endHour;
    private String endMinute;
    private String endAmPm;

    public Timeslot(String startHour, String startMinute, String startAmPm,
                    String endHour, String endMinute, String endAmPm) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.startAmPm = startAmPm;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.endAmPm = endAmPm;
    }

    public String getFormattedStartTime() {
        return String.format("%s:%s %s", startHour, startMinute, startAmPm);
    }

    public String getFormattedEndTime() {
        return String.format("%s:%s %s", endHour, endMinute, endAmPm);
    }

    public String getStartHour() { return startHour; }
    public String getStartMinute() { return startMinute; }
    public String getStartAmPm() { return startAmPm; }
}