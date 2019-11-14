package com.zj.caoshangfei.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jin.zhang@fuwo.com on 2017/11/16.
 */
public class RegexpUtils {


    private static final Pattern PATTERN_NON_UNICODE_CHARACTER = Pattern.compile(
            "\\W+", Pattern.UNICODE_CHARACTER_CLASS);

    public static String removeNonUnicodeCharacter(CharSequence input) {
        Matcher m = PATTERN_NON_UNICODE_CHARACTER.matcher(input);
        return m.replaceAll("");
    }
}
