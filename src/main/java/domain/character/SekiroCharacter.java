package domain.character;

public class SekiroCharacter extends FromSoftwareCharacter {

    @Override
    public String toHtmlString() {
        return "<html>\n" +
                "<ul>\n" +
                "<li>Nom : " + super.getName() + "</font>\n" +
                "<li>Nombre de mort : " + super.getDeathCount() + "</font>\n" +
                "</ul>\n" +
                "</html>";
    }
}
