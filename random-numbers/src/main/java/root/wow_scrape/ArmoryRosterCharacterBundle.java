package root.wow_scrape;

import org.jsoup.nodes.Element;

public class ArmoryRosterCharacterBundle {

    private String characterName;
    private String characterClass;


    public ArmoryRosterCharacterBundle(Element tableRow) {
        Element nameElement = tableRow.getElementsByClass("name").get(0).child(0);
        this.characterName = nameElement.childNode(0).childNode(0).toString();
        Element classElement = tableRow.getElementsByClass("cls").get(0);
        this.characterClass = classElement.child(0).attr("data-tooltip");
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public boolean isNiermanCandidate(){
        return this.characterClass.equals("Paladin") || this.characterClass.equals("Warlock");
    }

    public boolean isStrongNiermanCandidate(String name){
        return this.characterName.equals(name);
    }

}
