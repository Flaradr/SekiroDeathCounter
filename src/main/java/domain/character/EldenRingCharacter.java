package domain.character;

public class EldenRingCharacter extends FromSoftwareCharacter {

    @Override
    public String toHtmlString() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + super.getName() + "</font>\n" +
                "<li>Nombre de mort : <font color=red>" + super.getDeathCount() + "</font>\n" +
                "<li>Niveau : <font color=green>" + super.getLevel() + "</font>\n" +
                "<li>Durée de jeu : " + String.format("%02d heures %02d minutes", super.getSecondsPlayed() / 3600, ((super.getSecondsPlayed() % 3600) / 60)) + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }
}
