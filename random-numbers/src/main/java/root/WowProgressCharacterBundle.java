package root;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintStream;

public class WowProgressCharacterBundle {

    Element originalRow;

    Element rankCell;
    Element classCell;
    Element guildCell;
    Element realmCell;
    Element scoreCell;

    Element characterLink;
    Element guildLink;
    Element realmLink;

    String characterFaction;
    String characterRace;
    String characterClass;
    String characterName;

    String guildName;
    String realmName;



    WowProgressCharacterBundle(Element tableRow) {
        this.originalRow = tableRow;

        Elements tableCells = tableRow.children();
        this.rankCell = tableCells.get(0);
        this.classCell = tableCells.get(1);
        this.guildCell = tableCells.get(2);
        this.realmCell = tableCells.get(3);
        this.scoreCell = tableCells.get(4);

        this.characterLink = classCell.child(0);
        this.characterRace = appendAllElementsExceptTheLast(characterLink.attr("data-hint").split(" "));
        this.characterClass = characterLink.className().split(" ")[1];
        this.characterName = characterLink.childNode(0).toString();

        this.guildLink = guildCell.child(0);
        this.characterFaction = guildLink.className().split(" ")[1];
        this.guildName = trimNewLine(guildLink.childNode(0).childNode(0).toString());

        this.realmLink = realmCell.child(0);
        this.realmName = realmLink.child(0).childNode(0).toString();
    }

    public void printCharacterReport(PrintStream out) {
        out.println(this.characterName + " <" + this.guildName + " - " + this.realmName + ">");
        out.println(this.characterFaction + " " + this.characterRace + " " + this.characterClass);
    }

    private String appendAllElementsExceptTheLast(String[] stringArray) {
        if (stringArray.length == 1)
            return stringArray[0];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stringArray.length - 1; i++)
            builder.append(stringArray[i]);
        return builder.toString();
    }

    private String trimNewLine(String toTrim) {
        String[] parts = toTrim.split("\\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String onePart : parts)
            stringBuilder.append(onePart);
        return stringBuilder.toString();
    }

    public Element getOriginalRow() {
        return originalRow;
    }

    public Element getRankCell() {
        return rankCell;
    }

    public Element getClassCell() {
        return classCell;
    }

    public Element getGuildCell() {
        return guildCell;
    }

    public Element getRealmCell() {
        return realmCell;
    }

    public Element getScoreCell() {
        return scoreCell;
    }

    public Element getCharacterLink() {
        return characterLink;
    }

    public Element getGuildLink() {
        return guildLink;
    }

    public Element getRealmLink() {
        return realmLink;
    }

    public String getCharacterFaction() {
        return characterFaction;
    }

    public String getCharacterRace() {
        return characterRace;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getRealmName() {
        return realmName;
    }

    public boolean isNiermanCandidate(){
        return this.characterFaction.equals("alliance") && this.characterClass.equals("druid");
    }

}
