package domain;

public enum FromSoftwareGames {
    DEMONS_SOULS("Demon's Souls"),
    DARK_SOULS_1("Dark Souls"),
    DARK_SOULS_2("Dark Souls II"),
    DARK_SOULS_3("Dark Souls III"),
    BLOODBORNE("Bloodborne"),
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
