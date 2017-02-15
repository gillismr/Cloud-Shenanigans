package root.wow_scrape;

import org.jsoup.nodes.Element;

import java.io.PrintStream;

public class WowProgressCharacterSheetBundle extends WowProgressCharacterBundle {

    private String trueGuildName;
    private String armoryGuildLinkUrl;
    private String wpRealmLink;
    private String armoryLinkUrl;

    private String primarySpec = "not yet set";
    private String offSpec = "not yet set";

    public WowProgressCharacterSheetBundle(Element tableRow) {
        super(tableRow);
    }

    public String getGuildName() {
        return trueGuildName;
    }

    public void setTrueGuildName(String trueGuildName) {
        this.trueGuildName = trueGuildName;
    }

    public String getArmoryGuildLinkUrl() {
        return armoryGuildLinkUrl;
    }

    public void setArmoryGuildLinkUrl(String armoryGuildLinkUrl) {
        this.armoryGuildLinkUrl = armoryGuildLinkUrl;
    }

    public String getWpRealmLink() {
        return wpRealmLink;
    }

    public void setWpRealmLink(String wpRealmLink) {
        this.wpRealmLink = wpRealmLink;
    }

    public String getArmoryLinkUrl() {
        return armoryLinkUrl;
    }

    public void setArmoryLinkUrl(String armoryLinkUrl) {
        this.armoryLinkUrl = armoryLinkUrl;
    }

    public String getPrimarySpec() {
        return primarySpec;
    }

    public void setPrimarySpec(String primarySpec) {
        this.primarySpec = primarySpec;
    }

    public String getOffSpec() {
        return offSpec;
    }

    public void setOffSpec(String offSpec) {
        this.offSpec = offSpec;
    }

    public boolean isNiermanCandidate() {
        return super.isNiermanCandidate() && (primarySpec.equals("Healing (Restoration)") || offSpec.equals("Healing (Restoration)"));
    }

    @Override
    public void printCharacterReport(PrintStream out) {
        super.printCharacterReport(out);
        out.println("Main spec: " + primarySpec);
        out.println("Off spec: " + offSpec);
    }
}
