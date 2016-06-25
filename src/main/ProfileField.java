package main;

/**
 * Enum, represents field of User entity.
 */

public enum ProfileField {
    /** Login field. */
    LOGIN("login"),
    /** Email field. */
    EMAIL("email"),
    /** Full name field. */
    FULL_NAME("fullName"),
    /** Sex field. */
    SEX("sex"),
    /** Profile photo field. */
    PROFILE_PHOTO("profilePhoto");

    /** String representation of field. */
    public final String value;

    ProfileField(String type) {
        this.value = type;
    }

    /**
     * Define field from its string representation.
     * @param value, string representation of field.
     * @return define field.
     */
    public static ProfileField fromValue(String value) {
        ProfileField profileField = null;
        for (ProfileField c: ProfileField.values()) {
            if (c.value.equals(value)) {
                profileField = c;
            }
        }
        return profileField;
    }
}
