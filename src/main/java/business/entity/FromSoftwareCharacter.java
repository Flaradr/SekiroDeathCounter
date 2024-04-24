package business.entity;

public abstract class FromSoftwareCharacter {
    private int slotIndex;
    private String characterName;
    private int characterLevel;
    private int secondsPlayed;
    private int deathCount;

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
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
        String str = "\nName - " +
                characterName +
                "\nNumber of death : " +
                deathCount +
                "\nLevel : " +
                characterLevel;

        return str;
    }

    public String stringifiesToHtml() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + characterName + "</font>\n" +
                "<li>Nombre de mort : <font color=red>" + deathCount + "</font>\n" +
                "<li>Niveau : <font color=green>" + characterLevel + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }

}
