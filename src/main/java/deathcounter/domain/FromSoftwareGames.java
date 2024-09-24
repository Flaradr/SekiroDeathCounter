package deathcounter.domain;

public enum FromSoftwareGames {
    ELDEN_RING("Elden Ring"),
    SEKIRO("Sekiro: Shadows Die Twice");

    private final String fullName;

    FromSoftwareGames(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

}
