package root.wow_scrape;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WowScraper {

    private static final int PAGES_TO_CHECK = 10;
    private static final String WP_BASE_URL = "https://www.wowprogress.com";

    public WowScraper() {
    }

    public void run() throws Exception {
        Connection connection;
        List<WowProgressCharacterBundle> candidates = new ArrayList<WowProgressCharacterBundle>();
        for (int i = 0; i < PAGES_TO_CHECK; i++) {
            connection = Jsoup.connect(String.format(WP_BASE_URL + "/mythic_plus_score/us/char_rating/next/%d/class.druid#char_rating", i))
                    .validateTLSCertificates(false)
                    .timeout(10000);

            Element htmlBody = connection.get().body();

            Element insideElement = htmlBody.getElementById("primary").getElementsByClass("inside").get(0);
            Element primaryElement = insideElement.getElementsByClass("primary").get(0);
            Element tableElement = primaryElement.getElementById("char_rating_container").getElementsByTag("table").get(0);
            Element tableBody = tableElement.getElementsByTag("tbody").get(0);
            Elements tableRows = tableBody.getElementsByTag("tr");
            tableRows = new Elements(tableRows.subList(1, tableRows.size()));

            int counter = 0;
            for (Element tableRow : tableRows) {
                WowProgressCharacterBundle wpCharacterInfo = new WowProgressCharacterBundle(tableRow);
                if (wpCharacterInfo.isNiermanCandidate()) {
                    candidates.add(wpCharacterInfo);
                    counter++;
                }
            }
            print("Found " + counter + " Alliance druids on page " + i + " of the leaderboard.");

        }
        print("Found " + candidates.size() + " Alliance druids on the first " + PAGES_TO_CHECK + " pages of the leaderboard.");

        List<WowProgressCharacterSheetBundle> secondPassCandidates = new ArrayList<WowProgressCharacterSheetBundle>();
        for (WowProgressCharacterBundle bundle : candidates) {
            WowProgressCharacterSheetBundle secondPassCandidate = new WowProgressCharacterSheetBundle(bundle.getOriginalRow());

            connection = Jsoup.connect(secondPassCandidate.getWpCharacterUrlString())
                    .validateTLSCertificates(false)
                    .timeout(15000);

            Element htmlBody = connection.get().body();

            Element insideElement = htmlBody.getElementById("primary").getElementsByClass("inside").get(0);
            Element primaryElement = insideElement.getElementsByClass("primary").get(0);

            Element nameSubtitleElement = primaryElement.getElementsByClass("nav_block").get(0);

            Element realmLinkElement = nameSubtitleElement.child(0);
            String wpRealmLink = WP_BASE_URL + realmLinkElement.attr("href");
            secondPassCandidate.setWpRealmLink(wpRealmLink);

            Element guildLinkElement = nameSubtitleElement.child(1);
            String wpGuildLink = (WP_BASE_URL + guildLinkElement.attr("href")).replace("gearscore", "mythic_plus_score");
            String rawGuildString = guildLinkElement.attr("title");
            String trueGuildName = rawGuildString.substring(rawGuildString.indexOf('<') + 1, rawGuildString.indexOf('>'));
            String armoryGuildLink = getArmoryGuildLinkFromWp(wpGuildLink);
            secondPassCandidate.setTrueGuildName(trueGuildName);
            secondPassCandidate.setArmoryGuildLinkUrl(armoryGuildLink);

            Element armoryLinkElement = nameSubtitleElement.getElementsByClass("armoryLink").get(0);
            secondPassCandidate.setArmoryLinkUrl(armoryLinkElement.attr("href"));

            Element firstTable = primaryElement.getElementsByTag("table").get(0);
            Elements specs = firstTable.child(0).child(0).child(0).child(0).child(0).children();

            for (Element oneSpec : specs) {
                Elements specRowDataCells = oneSpec.getElementsByTag("td");
                Element thisContainsTheSpecString = specRowDataCells.get(0);
                String specString = thisContainsTheSpecString.childNode(0).toString();
                boolean isMainSpec = !specRowDataCells.get(2).childNodes().isEmpty();
                if (isMainSpec)
                    secondPassCandidate.setPrimarySpec(specString);
                else
                    secondPassCandidate.setOffSpec(specString);
            }
            if (secondPassCandidate.isNiermanCandidate())
                secondPassCandidates.add(secondPassCandidate);
        }

//        for(WowProgressCharacterSheetBundle secondPassCandidate : secondPassCandidates){
//            print("***********************************");
//            secondPassCandidate.printCharacterReport(System.out);
//        }
        print(secondPassCandidates.size() + " second pass candidates found.");

        print("***********************************");
        print("***********************************");
        print("***********************************");
        print("***********************************");

        int acc = 0;
        for (WowProgressCharacterSheetBundle secondPassCandidate : secondPassCandidates) {

            String guildArmoryLink = secondPassCandidate.getArmoryGuildLinkUrl() + "/roster?sort=rank&dir=a";

            try {
                connection = Jsoup.connect(guildArmoryLink)
                        .validateTLSCertificates(false)
                        .timeout(10000);

                Element htmlBody = connection.get().body();

                Element contentTopBodyTop = (Element) htmlBody.getElementById("wrapper")
                        .getElementById("content")
                        .childNodes().get(1);

                Element contentBotClear = (Element) contentTopBodyTop
                        .childNodes().get(3);

                Elements tableRows = contentBotClear
                        .getElementById("profile-wrapper")
                        .getElementsByClass("profile-contents").get(0)
                        .getElementsByClass("profile-section").get(0)
                        .getElementById("roster")
                        .getElementsByTag("table").get(0)
                        .getElementsByTag("tbody").get(0)
                        .children();


//            print(guildArmoryLink);
                ArmoryRosterCharacterBundle guildLeaderBundle = new ArmoryRosterCharacterBundle(tableRows.get(0));
                if (guildLeaderBundle.isNiermanCandidate()) {
                    print("**************");
                    print("Candidate: " + secondPassCandidate.getCharacterName());
                    print(secondPassCandidate.getArmoryLinkUrl());
                    print(secondPassCandidate.getWpRealmLink());
                    print("With guild leader: " + guildLeaderBundle.getCharacterName() + " (" + guildLeaderBundle.getCharacterClass() + ")");
                    print("");
                    acc++;
                }
            } catch (Exception e) {
                print("**************");
                print("ERROR: " + e.getMessage());
                print("Candidate: " + secondPassCandidate.getCharacterName());
                print("WP link: " + secondPassCandidate.getWpCharacterUrlString());
                print("Guild armory link: " + guildArmoryLink);
                print("**************");
            }
        }
        print(acc + " third pass candidates found.");


    }

    private String getArmoryGuildLinkFromWp(String wpGuildLink) throws Exception {
        Connection connection = Jsoup.connect(wpGuildLink)
                .validateTLSCertificates(false)
                .timeout(10000);

        Element htmlBody = connection.get().body();
        Element insideElement = htmlBody.getElementById("primary").getElementsByClass("inside").get(0);
        Element primaryElement = insideElement.getElementsByClass("primary").get(0);
        Element armoryLinkElement = primaryElement.getElementsByClass("armoryLink").get(0);
        return armoryLinkElement.attr("href");
    }

    public void print(Object anything) {
        System.out.println(anything.toString());
    }

    public static void main(String[] args) throws Exception {
        new WowScraper().run();
    }
}
