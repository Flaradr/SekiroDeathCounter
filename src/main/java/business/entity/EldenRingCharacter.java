package business.entity;

public class EldenRingCharacter extends FromSoftwareCharacter {

    public String stringifiesToHtml() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + super.getCharacterName() + "</font>\n" +
                "<li>Nombre de mort : <font color=red>" + super.getDeathCount() + "</font>\n" +
                "<li>Niveau : <font color=green>" + super.getCharacterLevel() + "</font>\n" +
                "<li>Durée de jeu : " + String.format("%02d heures %02d minutes", super.getSecondsPlayed() / 3600, ((super.getSecondsPlayed() % 3600) / 60)) + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }
}
