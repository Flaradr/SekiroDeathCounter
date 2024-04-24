package business.entity;

import java.util.stream.Stream;

public enum FromSoftwareGames {
    DEMONS_SOULS(1, "Demon's Souls"),
    DARK_SOULS_1(2, "Dark Souls"),
    DARK_SOULS_2(3, "Dark Souls II"),
    DARK_SOULS_3(4, "Dark Souls III"),
    BLOODBORNE(5, "Bloodborne"),
    SEKIRO(6, "Sekiro: Shadows Die Twice"),
    ELDEN_RING(7, "Elden Ring");

    private final int id;
    private final String fullName;

    FromSoftwareGames(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public static Stream<FromSoftwareGames> stream() {
        return Stream.of(FromSoftwareGames.values());
    }

    public static FromSoftwareGames fromId(int id) {
        return FromSoftwareGames.stream()
                .filter(game -> game.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No game with the id : " + id));
    }
}
