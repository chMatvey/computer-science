package com.github.chMatvey.codewar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PigLatin {
    public static String pigIt2(String str) {
        return str.replaceAll("(\\w)(\\w*)", "$2$1ay");
    }

    public static String pigIt(String str) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(str);
        List<String> words = new ArrayList<>();
        int lastEnd = 0;
        while (matcher.find()) {
            if (lastEnd != matcher.start())
                words.add(str.substring(lastEnd, matcher.start()));
            String word = str.substring(matcher.start(), matcher.end());
            words.add(word.substring(1) + word.charAt(0) + "ay");
            lastEnd = matcher.end();
        }
        if (lastEnd != str.length())
            words.add(str.substring(lastEnd));

        return String.join("", words);
    }
}
