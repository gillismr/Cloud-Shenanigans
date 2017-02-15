package root.wow_scrape;

public class Util {

    public static String appendAllElementsExceptTheLast(String[] stringArray) {
        if (stringArray.length == 1)
            return stringArray[0];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stringArray.length - 1; i++)
            builder.append(stringArray[i]);
        return builder.toString();
    }

    public static String replaceSpacesWithPercentTwenty(String string) {
        return string.replace(" ", "%20");
    }
}
