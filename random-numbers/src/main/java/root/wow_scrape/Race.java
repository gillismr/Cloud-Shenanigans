package root.wow_scrape;

import java.util.ArrayList;
import java.util.List;

public enum Race {

    HUMAN("Alliance", "Human"),
    DWARF("Alliance", "Dwarf"),
    GNOME("Alliance", "Gnome"),
    NIGHT_ELF("Alliance", "Night Elf"),
    DRAENEI("Alliance", "Draenei"),
    WORGEN("Alliance", "Worgen"),

    ORC("Horde", "Orc"),
    TROLL("Horde", "Troll"),
    TAUREN("Horde", "Tauren"),
    UNDEAD("Horde", "Undead"),
    BLOOD_ELF("Horde", "Blood Elf"),
    GOBLIN("Horde", "Goblin"),

    PANDAREN("Unknown", "Pandaren");

    private String faction;
    private String prettyName;

    Race(String faction, String prettyName){
        this.faction = faction;
        this.prettyName = prettyName;
    }

    private static List<Race> alliance = new ArrayList<Race>();
    private static List<Race> horde = new ArrayList<Race>();

    static {
        alliance.add(HUMAN);
        alliance.add(DWARF);
        alliance.add(GNOME);
        alliance.add(NIGHT_ELF);
        alliance.add(DRAENEI);
        alliance.add(WORGEN);

        horde.add(ORC);
        horde.add(TROLL);
        horde.add(TAUREN);
        horde.add(UNDEAD);
        horde.add(BLOOD_ELF);
        horde.add(GOBLIN);
    }

    public static List<Race> getAlliance() {
        return alliance;
    }

    public static List<Race> getHorde() {
        return horde;
    }

    public String getFaction() {
        return faction;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public static Race fromWowProgressString(String wowProgressString){
        String justTheRacePart = Util.appendAllElementsExceptTheLast(wowProgressString.split(" "));
        for(Race race : Race.values()){
            if(race.prettyName.replace(" ", "").equalsIgnoreCase(justTheRacePart))
                return race;
        }
        throw new RuntimeException("Encountered unexpected wowProgressString: \"" + wowProgressString + "\", could not convert to Race enum.");
    }
}
