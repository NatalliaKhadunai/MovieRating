package main.model.entity;

import java.util.EnumSet;

public enum Status {
    BAN(-1,-1), ADMIN(0,0), BRONZE(1,1.9), SILVER(2,2.9), GOLD(3,3.9);

    public final double lowerThreshold;
    public final double upperThreshold;

    Status(double lowerThreshold, double upperThreshold) {
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
    }

    public static Status minimalStatus() {
        return BRONZE;
    }

    public static Status maximumStatus() {
        return GOLD;
    }

    public static Status adminStatus() {
        return ADMIN;
    }

    public static Status banedStatus() {
        return BAN;
    }

    public static Status lowerStatus(Status status) {
        if (!status.equals(Status.ADMIN) && !status.equals(Status.BAN) && !status.equals(Status.BRONZE)) {
            Status resultStatus = null;
            EnumSet<Status> enumSet = EnumSet.range(Status.minimalStatus(), Status.maximumStatus());
            for (Status st : enumSet) {
                if (st.upperThreshold + 0.1 == status.lowerThreshold) resultStatus = st;
            }
            return resultStatus;
        }
        else return null;
    }

    public static Status upperStatus(Status status) {
        if (!status.equals(Status.ADMIN) && !status.equals(Status.BAN) && !status.equals(Status.GOLD)) {
            Status resultStatus = null;
            EnumSet<Status> enumSet = EnumSet.range(Status.BRONZE, Status.GOLD);
            for (Status st : enumSet) {
                if (st.lowerThreshold - 0.1 == status.upperThreshold) resultStatus = st;
            }
            return resultStatus;
        }
        else return null;
    }

    public static Status defineStatus(double statusCoefficient) {
        Status result = null;
        for (Status st : Status.values()) {
            if (st.lowerThreshold<=statusCoefficient && st.upperThreshold >= statusCoefficient) result = st;
        }
        return result;
    }
}
