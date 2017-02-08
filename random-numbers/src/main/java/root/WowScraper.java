package root;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WowScraper {

    /*
    import requests, BeautifulSoup
base = "http://www.wowprogress.com"
hordeDruids = []
restoDruids = []
hordeRestoLeaders = []
# try:
# 	pagedBase = "http://www.wowprogress.com/mythic_plus_score/us/char_rating/next/%s/class.druid#char_rating"
# 	i = 50
# 	while i < 75:
# 		for tr in BeautifulSoup.BeautifulSoup(requests.get(pagedBase%i).content).findAll("tr"):
# 			if "horde" in str(tr) and "character" in str(tr):
# 				hordeDruids.append(base + tr.find("a")['href'])
# 		i+=1
# 		print "Page %i of 50"%i
# 	print "Found %i Horde Druids"%len(hordeDruids)
# 	i=1
# 	for druid in hordeDruids:
# 		page = BeautifulSoup.BeautifulSoup(requests.get(druid).content)
# 		for tr in page.findAll("tr"):
# 			try:
# 				if "Restoration" in str(tr.findAll("td")[0]) and "*" in str(tr.findAll("td")[-1]):
# 					for link in page.findAll("a"):
# 						if "battle.net/wow/character" in link['href']:
# 							print link['href']
# 							restoDruids.append(link['href'])
# 					break
# 			except:
# 				pass
# 		print "Checked %i of %i Horde Druids"%(i, len(hordeDruids))
# 		i+=1
armoryBase = "http://us.battle.net"
guilderLeaders = []
for armory in open("hordeResto", "r").read().split("\n"):
	character = armory.split("/")[-2]
	page = BeautifulSoup.BeautifulSoup(requests.get(armory).content)
	print armory
	for link in page.findAll("a"):
		try:
			if "wow/en/guild" in link['href']:
				roster = armoryBase + link['href'].split("?")[0] + "/roster?sort=rank&dir=a"
				guildPage = BeautifulSoup.BeautifulSoup(requests.get(roster).content)
				for tr in guildPage.findAll("tr"):
					if "Guild Master" in str(tr) and character in str(tr):
						guilderLeaders.append(armory)
						print armory
						print tr.find("td")
						print "%s guild master"%character
						break
				print "%s not guild master"%character
				break
		except:
			pass
for leader in guilderLeaders:
	print guilderLeaders
# except:
# 	pass
# for restoDruid in set(restoDruids):
# 	print restoDruid
     */

    public WowScraper() {
    }

    public void run() throws Exception {
        Connection connection;
        List<Element> candidateRows = new ArrayList<Element>();
        for (int i = 0; i < 20; i++) {
            connection = Jsoup.connect(String.format("https://www.wowprogress.com/mythic_plus_score/us/char_rating/next/%d/class.druid#char_rating", i))
                    .validateTLSCertificates(false)
                    .timeout(10000);

            Element htmlBody = connection.get().body();

            Element insideElement = htmlBody.getElementById("primary").getElementsByClass("inside").get(0);
            Element primaryElement = insideElement.getElementsByClass("primary").get(0);
            Element tableElement = primaryElement.getElementById("char_rating_container").getElementsByTag("table").get(0);
            Element tableBody = tableElement.getElementsByTag("tbody").get(0);
            Elements tableRows = tableBody.getElementsByTag("tr");
            tableRows = new Elements(tableRows.subList(1, tableRows.size()));

            for (Element tableRow : tableRows) {
                WowProgressCharacterBundle wpCharacterInfo = new WowProgressCharacterBundle(tableRow);
                if(wpCharacterInfo.isNiermanCandidate()) {
                    System.out.println("Nierman candidate found: ");
                    wpCharacterInfo.printCharacterReport(System.out);
                    System.out.println("********************************************************************************");
                    candidateRows.add(tableRow);
                }
            }
        }
        System.out.println(candidateRows.size() + " candidates found.");
    }

    public static void main(String[] args) throws Exception {
        new WowScraper().run();
    }
}
