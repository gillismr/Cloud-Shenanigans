package root.wow_scrape;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintStream;

public class WowProgressCharacterBundle {

    private static final String WOW_PROGRESS_BASE_URL = "https://www.wowprogress.com";

    private Element originalRow;

    private Element rankCell;
    private Element classCell;
    private Element guildCell;
    private Element realmCell;
    private Element scoreCell;

    private Element characterLinkElement;
    private Element guildLinkElement;
    private Element realmLinkElement;

    private String characterFaction;
    private Race characterRace;
    private String characterClass;
    private String characterName;

    private String guildName;
    private String realmName;

    private String wpCharacterUrlString;

    WowProgressCharacterBundle(Element tableRow) {
        this.originalRow = tableRow;

        Elements tableCells = tableRow.children();
        this.rankCell = tableCells.get(0);
        this.classCell = tableCells.get(1);
        this.guildCell = tableCells.get(2);
        this.realmCell = tableCells.get(3);
        this.scoreCell = tableCells.get(4);

        this.characterLinkElement = classCell.child(0);
        this.characterRace = Race.fromWowProgressString(characterLinkElement.attr("data-hint"));
        this.characterClass = characterLinkElement.className().split(" ")[1];
        this.characterName = characterLinkElement.childNode(0).toString();

        this.guildLinkElement = guildCell.children().isEmpty() ? null : guildCell.child(0);
        this.characterFaction = guildLinkElement == null ? characterRace.getFaction() : guildLinkElement.className().split(" ")[1];
        this.guildName = guildLinkElement == null ? null : guildLinkElement.childNode(0).childNode(0).toString().replace("\n", "").trim();

        this.realmLinkElement = realmCell.child(0);
        this.realmName = realmLinkElement.child(0).childNode(0).toString().trim();

        this.wpCharacterUrlString = WOW_PROGRESS_BASE_URL + characterLinkElement.attr("href");

    }

    public void printCharacterReport(PrintStream out) {
        out.println(this.characterName + " <" + this.guildName + " - " + this.realmName + ">");
        out.println(this.characterFaction + " " + this.characterRace.getPrettyName() + " " + this.characterClass);
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

    public Element getCharacterLinkElement() {
        return characterLinkElement;
    }

    public Element getGuildLinkElement() {
        return guildLinkElement;
    }

    public Element getRealmLinkElement() {
        return realmLinkElement;
    }

    public String getCharacterFaction() {
        return characterFaction;
    }

    public Race getCharacterRace() {
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

    public String getWpCharacterUrlString() {
        return wpCharacterUrlString;
    }

    public boolean isNiermanCandidate(){
        return this.guildName != null && this.characterFaction.equals("alliance") && this.characterClass.equals("druid");
    }

}
