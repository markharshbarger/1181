public class Clock {

    private int hours;
    private int minutes;
    private String meridian;

    public Clock() {
        hours = 12;
        minutes = 0;
        meridian = "a.m.";
    }

    public Clock(int hours, int minutes, String meridian) {
        this.hours = hours;
        this.minutes = minutes;
        this.meridian = meridian;
    }

    @Override
    public String toString() {
        String time = hours + ":";
        if (minutes < 10) {
            time += "0";
        }
        time += minutes + " " + meridian;
        return time;
    }

    public static Clock getEarlier(Clock c1, Clock c2) {
        if (c1.meridian.equals("a.m.") && c2.meridian.equals("p.m.")) {
            return c1;
        } else if (c1.meridian.equals("p.m.") && c2.meridian.equals("a.m.")) {
            return c2;
        } else {
            // there is a special case if it is 12-something a.m. or p.m. on one
            // but not both of the clocks (i.e. 12:00 a.m. is before 1:00 a.m.)
            if (c1.hours == 12 && c2.hours != 12) {
                return c1;
            } else if (c2.hours == 12 && c1.hours != 12) {
                return c2;
            } else {
                if (c1.hours < c2.hours) {
                    return c1;
                } else if (c2.hours < c1.hours) {
                    return c2;
                } else {
                    if (c1.minutes < c2.minutes) {
                        return c1;
                    } else if (c2.minutes < c1.minutes) {
                        return c2;
                    } else {
                        // the clocks have the same time
                        assert c1.toString().equals(c2.toString()) : c1.toString() + " " + c2.toString();
                        // we will arbitrarily return the first one
                        return c1;
                    }
                }
            }
        }
    }
}