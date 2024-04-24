package business.entity;

public class SekiroCharacter extends FromSoftwareCharacter {

    @Override
    public String stringifiesToHtml() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + super.getCharacterName() + "</font>\n" +
                "<li>Nombre de mort : <font color=red>" + super.getDeathCount() + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }
}
