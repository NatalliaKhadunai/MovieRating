package main.model.entity;

import java.util.EnumSet;

/**
 * Enum {@code Status} represents Status entity.
 */

public enum Status {
    /** Constant for representing baned status. */
    BAN(-1,-1),
    /** Constant for representing administrator status. */
    ADMIN(0,0),
    /** Constant for representing bronze (first level) status. */
    BRONZE(1,1.9),
    /** Constant for representing silver (second level) status. */
    SILVER(2,2.9),
    /** Constant for representing gold (third level) status. */
    GOLD(3,3.9);

    /** {@code Status} lower threshold coefficient */
    public final double lowerThreshold;
    /** {@code Status} upper threshold coefficient */
    public final double upperThreshold;

    Status(double lowerThreshold, double upperThreshold) {
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
    }

    /**
     * Return minimal status (first level status) in the system.
     * @return constant value for representing bronze (first level) status.
     */
    public static Status minimalStatus() {
        return BRONZE;
    }

    /**
     * Return maximum status in the system.
     * @return constant value for representing gold (third level) status.
     */
    public static Status maximumStatus() {
        return GOLD;
    }

    /**
     * Return administrator status in the system.
     * @return constant value for representing bronze (first level) status.
     */
    public static Status adminStatus() {
        return ADMIN;
    }

    /**
     * Return baned status in the system.
     * @return constant value for representing baned status.
     */
    public static Status banedStatus() {
        return BAN;
    }

    /**
     * Return {@code Status} that is lower in hierarchy than the argument {@code Status} object.
     * @param status value, according to which a lower status is sought.
     * @return status value, that is lower in hierarchy than the argument {@code Status} object
     * if such value exists, else return null.
     */
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

    /**
     * Return {@code Status} that is upper in hierarchy than the argument {@code Status} object.
     * @param status value, according to which a upper status is sought.
     * @return {@code Status} value, that is upper in hierarchy than the argument {@code Status} object
     * if such value exists, else return null.
     */
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

    /**
     * Return {@code Status} object, defined by the given status coefficient.
     * @param statusCoefficient value, according to which suitable {@code Status} object is sought.
     * @return {@code Status} value, that suites given status coefficient.
     */
    public static Status defineStatus(double statusCoefficient) {
        Status result = null;
        for (Status st : Status.values()) {
            if (st.lowerThreshold<=statusCoefficient && st.upperThreshold >= statusCoefficient) result = st;
        }
        return result;
    }
}
