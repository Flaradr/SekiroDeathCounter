package domain.character;

public abstract class FromSoftwareCharacter {
    private String name;
    private int level;
    private int secondsPlayed;
    private int deathCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSecondsPlayed() {
        return secondsPlayed;
    }

    public void setSecondsPlayed(int secondsPlayed) {
        this.secondsPlayed = secondsPlayed;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public String toString() {

        return "\nNom : " +
                name +
                "\nNombre de mort : " +
                deathCount +
                "\nNiveau : " +
                level;
    }

    public String toHtmlString() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + name + "</font>\n" +
                "<li>Nombre de mort : <font color=red>" + deathCount + "</font>\n" +
                "<li>Niveau : <font color=green>" + level + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }

}
