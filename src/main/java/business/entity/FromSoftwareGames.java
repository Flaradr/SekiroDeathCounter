package business.entity;

import java.util.stream.Stream;

public enum FromSoftwareGames {
    DARK_SOULS_1(1),
    DARK_SOULS_2(2),
    DARK_SOULS_3(3),
    SEKIRO(4),
    BLOODBORNE(5),
    ELDEN_RING(6);

    private int id;

    FromSoftwareGames(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
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
