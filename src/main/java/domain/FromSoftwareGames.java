package domain;

public enum FromSoftwareGames {
    SEKIRO("Sekiro: Shadows Die Twice"),
    ELDEN_RING("Elden Ring");

    private final String fullName;

    FromSoftwareGames(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

}
