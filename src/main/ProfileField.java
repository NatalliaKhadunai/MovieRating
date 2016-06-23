package main;

public enum ProfileField {
    LOGIN("login"),
    EMAIL("email"),
    FULL_NAME("fullName"),
    SEX("sex"),
    PROFILE_PHOTO("profilePhoto");

    public final String value;

    ProfileField(String type) {
        this.value = type;
    }

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
