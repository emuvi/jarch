package br.com.pointel.jarch.mage;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import br.com.pointel.jarch.data.Pair;

public class WizString {

    private WizString() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Object.class);
    }

    public static String get(Object value) throws Exception {
        if (value == null) return null;
        if (value instanceof String string) {
            return string;
        }
        return String.valueOf(value);
    }

    public static final String simpleUpperChars = "ABCDEFGHIJKLMNOPQRSTUVWXZY";

    public static String newRandomString(int length) {
        var random = new Random();
        var randomNumberString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                int randomDigit = random.nextInt(10);
                randomNumberString.append(randomDigit);
            } else {
                char randomChar = simpleUpperChars.charAt(random.nextInt(simpleUpperChars.length()));
                randomNumberString.append(randomChar);
            }
        }
        return randomNumberString.toString();
    }

    public static String newRandomStringOnlyNumbers(int length) {
        var random = new Random();
        var randomNumberString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomDigit = random.nextInt(10);
            randomNumberString.append(randomDigit);
        }
        return randomNumberString.toString();
    }

    public static String getParameterName(String ofChars) {
        if (ofChars == null) {
            return null;
        }
        if (ofChars.isEmpty()) {
            return ofChars;
        }
        var builder = new StringBuilder();
        var lastIsUpper = isFirstUpperCase(ofChars);
        for (char ch : ofChars.toCharArray()){ 
            if (Character.isUpperCase(ch) && !lastIsUpper) {
                builder.append('_');
            }
            if (ch == ' ' || ch == '-') {
                builder.append('_');
            } else {
                builder.append(Character.toUpperCase(ch));
            }
            lastIsUpper = Character.isUpperCase(ch);
        }
        return builder.toString()
                .replaceAll("^_+", "")
                .replaceAll("_+$", "")
                .replaceAll("_+", "_");
    }

    public static String mountGrid(List<Pair<String, String>> grid) {
        var result = new StringBuilder();
        var max = 0;
        for (var line : grid) {
            max = Math.max(max, line.key.length());
        }
        for (var line : grid) {
            result.append(StringUtils.rightPad(line.key, max, '.'));
            result.append("...: ");
            result.append(line.val);
            result.append("\n");
        }
        return result.toString();
    }

    public static String getNameWithNewIndex(String name, int addIndex) {
        int begin = -1;
        int end = name.length();
        for (int i = 0; i < name.length(); i++) {
            char charAt = name.charAt(i);
            if (begin == -1) {
                if (Character.isDigit(charAt)) {
                    begin = i;
                }
            } else {
                if (!Character.isDigit(charAt)) {
                    end = i;
                    break;
                }
            }
        }
        if (begin == -1) {
            return name;
        }
        int number = Integer.parseInt(name.substring(begin, end));
        int newNumber = number + addIndex;
        String newNameNumber = StringUtils.leftPad(newNumber + "", end - begin, '0');
        return name.substring(0, begin) + newNameNumber + name.substring(end);
    }

    public static int count(String inString, String theString) {
        if (inString == null || theString == null || inString.isEmpty() || theString.isEmpty()) {
            return 0;
        }
        int result = 0;
        int index = 0;
        while ((index = inString.indexOf(theString, index)) != -1) {
            result++;
            index += theString.length();
        }
        return result;
    }

    public static Map<String, Integer> countWordsLike(String inString) {
        var result = new HashMap<String, Integer>();
        for (String word : getWordsLike(inString)) {
            result.put(word, result.getOrDefault(word, 0) + 1);
        }
        return result;
    }

    public static Map<String, Integer> countWords(String chars) {
        var result = new HashMap<String, Integer>();
        for (String word : getWords(chars)) {
            result.put(word, result.getOrDefault(word, 0) + 1);
        }
        return result;
    }

    public static Set<String> getWordsLikeKeySetOrdered(String chars) {
        return new LinkedHashSet<>(getWordsLike(chars).stream()
                .map(String::toLowerCase).toList());
    }

    public static Set<String> getWordsKeySetOrdered(String chars) {
        return new LinkedHashSet<>(getWordsList(chars).stream()
                .map(String::toLowerCase).toList());
    }

    public static Set<String> getWordsLikeKeySet(String chars) {
        return new HashSet<>(getWordsLike(chars).stream()
                .map(String::toLowerCase).toList());
    }

    public static Set<String> getWordsKeySet(String chars) {
        return new HashSet<>(getWordsList(chars).stream()
                .map(String::toLowerCase).toList());
    }

    public static Set<String> getWordsLikeSet(String source) {
        return new HashSet<>(getWordsLike(source));
    }

    public static Set<String> getWordsSet(String source) {
        return new HashSet<>(getWordsList(source));
    }

    public static List<String> getWordsLike(String source) {
        return getWordsList(source).stream()
                .map(WizString::removeAccents).toList();
    }

    public static String[] getWords(String chars) {
        return chars.split("\\s+");
    }

    public static List<String> getWordsList(String chars) {
        return Arrays.asList(getWords(chars));
    }

    public static List<String> getWordsOnDiffers(String chars) {
        var result = new ArrayList<String>();
        var spaced = chars.split("\\s+");
        for (String word : spaced) {
            if (!word.isEmpty()) {
                var lastIsLetter = Character.isLetter(word.charAt(0));
                var actualWord = new StringBuilder();
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    var actualIsLetter = Character.isLetter(ch);
                    if (lastIsLetter != actualIsLetter) {
                        if (!actualWord.isEmpty()) {
                            result.add(actualWord.toString());
                            actualWord = new StringBuilder();
                        }
                        lastIsLetter = actualIsLetter;
                    }
                    actualWord.append(ch);
                }
                if (!actualWord.isEmpty()) {
                    result.add(actualWord.toString());
                }
            }
        }
        return result;
    }

    public static String[] getLines(String chars) {
        return chars.split("\\r?\\n");
    }

    public static String getFirstLine(String chars) {
        if (chars == null) {
            return null;
        }
        int pos = -1;
        int posR = chars.indexOf("\r");
        int posN = chars.indexOf("\n");
        if (posR > -1 && posN > -1) {
            pos = Math.min(posR, posN);
        } else if (posR > -1) {
            pos = posR;
        } else if (posN > -1) {
            pos = posN;
        }
        if (pos == -1) {
            return chars;
        }
        return chars.substring(0, pos);
    }

    public static String capitalizeWords(String chars) {
        return capitalizeWords(chars, 4);
    }

    public static String capitalizeWords(String chars, int minSize) {
        var words = getWords(chars);
        var builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            var word = words[i].toLowerCase();
            if (word.contains("-")) {
                var parts = word.split("\\-");
                for (int i2 = 0; i2 < parts.length; i2++) {
                    var part = parts[i2];
                    if (i2 > 0) {
                        builder.append("-");
                    }
                    builder.append(part.substring(0, 1).toUpperCase());
                    builder.append(part.substring(1));
                }
            } else if (word.contains("_")) {
                var parts = word.split("\\_");
                for (int i2 = 0; i2 < parts.length; i2++) {
                    var part = parts[i2];
                    if (i2 > 0) {
                        builder.append("_");
                    }
                    builder.append(part.substring(0, 1).toUpperCase());
                    builder.append(part.substring(1));
                }
            } else {
                if (word.length() >= minSize) {
                    builder.append(word.substring(0, 1).toUpperCase());
                    builder.append(word.substring(1));
                } else {
                    builder.append(word);
                }
            }
        }
        return capitalizeFirstLetter(builder.toString());
    }

    public static String capitalizeFirstLetter(String words) {
        return words.length() > 1
                ? words.substring(0, 1).toUpperCase() + words.substring(1) 
                : words.toUpperCase();
    }

    public static String switchCase(String chars) {
        var result = new StringBuilder();
        for (char c : chars.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(Character.toUpperCase(c));
            }
        }
        return result.toString();
    }

    public static String removeAccents(String chars) {
        String decomposed = Normalizer.normalize(chars, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(decomposed).replaceAll("");
    }

    public static boolean isEmpty(String chars) {
        return chars == null || chars.isEmpty();
    }

    public static boolean isNotEmpty(String chars) {
        return chars != null && !chars.isEmpty();
    }

    public static String firstNonEmpty(String... charsList) {
        if (charsList == null) {
            return "";
        }
        for (String chars : charsList) {
            if (WizString.isNotEmpty(chars)) {
                return chars;
            }
        }
        return "";
    }

    public static String sum(String union, String... charsList) {
        return WizString.sum(union, null, charsList);
    }

    public static String sum(String union, StringBuilder andBuilder, String... charsList) {
        if (charsList == null) {
            return null;
        }
        if (union == null) {
            union = "";
        }
        var atLeastOne = false;
        var result = andBuilder != null ? andBuilder : new StringBuilder();
        for (String chars : charsList) {
            if (WizString.isNotEmpty(chars)) {
                if (atLeastOne) {
                    result.append(union);
                } else {
                    atLeastOne = true;
                }
                result.append(chars);
            }
        }
        return result.toString();
    }

    public static String insertSpaceInUppers(String chars) {
        if (chars == null) {
            return null;
        }
        var result = new StringBuilder();
        int len = chars.length();
        for (int i = 0; i < len; i++) {
            char c = chars.charAt(i);
            if (i > 0 && Character.isUpperCase(c) && Character.isLetter(c) && 
                    (!Character.isUpperCase(chars.charAt(i - 1)) || !Character.isLetter(chars.charAt(i - 1)))) {
                result.append(' ');
            }
            result.append(c);
        }
        return result.toString();
    }

    public static boolean isFirstUpperCase(String chars) {
        if (chars == null) {
            return false;
        }
        if (!chars.isEmpty()) {
            return Character.isUpperCase(chars.charAt(0));
        }
        return false;
    }

    public static String toUpperOnlyFirstChar(String chars) {
        var result = new StringBuilder();
        if (!chars.isEmpty()) {
            result.append(chars.substring(0, 1).toUpperCase());
        }
        if (chars.length() > 1) {
            result.append(chars.substring(1).toLowerCase());
        }
        return result.toString();
    }

    public static String toUpperFirstChar(String chars) {
        var result = new StringBuilder();
        if (!chars.isEmpty()) {
            result.append(chars.substring(0, 1).toUpperCase());
        }
        if (chars.length() > 1) {
            result.append(chars.substring(1));
        }
        return result.toString();
    }

    public static String getFromDoubleQuotes(String chars) {
        if ((chars == null) || (chars.length() < 2)) {
            return chars;
        }
        if (chars.charAt(0) == '"' && chars.charAt(chars.length() - 1) == '"') {
            return chars.substring(1, chars.length() - 1);
        } else {
            return chars;
        }
    }

    public static Number getNumber(String chars) {
        if (chars == null) {
            return null;
        }
        chars = getDigits(chars);
        if (chars.contains(".")) {
            return Double.parseDouble(chars);
        }
        return Integer.parseInt(chars);
    }

    public static String getDigits(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String getLetters(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isLetter(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String getNonLetters(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (!Character.isLetter(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String getNonLettersAndNonDigits(String chars) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String replaceLettersOrDigits(String chars, char withChar) {
        if (chars == null) {
            return null;
        }
        var builder = new StringBuilder();
        for (char ch : chars.toCharArray()) {
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                builder.append(withChar);
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String fill(char withChar, int untilLength) {
        return WizString.fill(null, withChar, untilLength, true);
    }

    public static String fill(String theString, char withChar, int untilLength) {
        return WizString.fill(theString, withChar, untilLength, false);
    }

    public static String fillAtStart(String chars, char withChar, int untilLength) {
        return WizString.fill(chars, withChar, untilLength, true);
    }

    public static String fill(String chars, char withChar, int untilLength, boolean atStart) {
        var result = new StringBuilder();
        var diference = untilLength - (chars != null ? chars.length() : 0);
        if (!atStart && chars != null) {
            result.append(chars);
        }
        for (var i = 0; i < diference; i++) {
            result.append(withChar);
        }
        if (atStart && chars != null) {
            result.append(chars);
        }
        return result.toString();
    }

    public static boolean contains(String chars, Character... anyChar) {
        if (WizString.isNotEmpty(chars) && anyChar != null && anyChar.length > 0) {
            for (var i = 0; i < chars.length(); i++) {
                if (WizArray.has(chars.charAt(i), anyChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String replaceAll(String chars, String[] from, String[] to) {
        if (from == null || chars == null || chars.isEmpty()) {
            return chars;
        }
        for (var i = 0; i < from.length; i++) {
            var newValue = to == null || i >= to.length ? "" : to[i];
            chars = chars.replace(from[i], newValue);
        }
        return chars;
    }

    public static String stripFirstLines(String chars, int size) {
        if (chars == null) {
            return null;
        }
        var lines = getLines(chars);
        var builder = new StringBuilder();
        for (var i = size; i < lines.length; i++) {
            builder.append(lines[i]);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String replaceBreaks(String chars) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        chars = chars.replace("\\", "\\\\");
        chars = chars.replace("\r", "\\r");
        chars = chars.replace("\n", "\\n");
        chars = chars.replace("\t", "\\t");
        chars = chars.replace("\f", "\\f");
        return chars.replace("\b", "\\b");
    }

    public static String remakeBreaks(String chars) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        chars = chars.replace("\\b", "\b");
        chars = chars.replace("\\f", "\f");
        chars = chars.replace("\\t", "\t");
        chars = chars.replace("\\n", "\n");
        chars = chars.replace("\\r", "\r");
        return chars.replace("\\\\", "\\");
    }

    public static String replaceVarsHolders(String chars) {
        var result = chars;
        var now = new Date();
        if (result.contains("${date}")) {
            result = result.replace("${date}", WizUtilDate.format(now));
        }
        if (result.contains("${dateUser}")) {
            result = result.replace("${dateUser}", WizUtilDate.formatDateUser(now));
        }
        if (result.contains("${timeUser}")) {
            result = result.replace("${timeUser}", WizUtilDate.formatTimeUser(now));
        }
        if (result.contains("${millisUser}")) {
            result = result.replace("${millisUser}", WizUtilDate.formatMillisUser(now));
        }
        if (result.contains("${dateTimeUser}")) {
            result = result.replace("${dateTimeUser}", WizUtilDate.formatDateTimeUser(now));
        }
        if (result.contains("${timestampUser}")) {
            result = result.replace("${timestampUser}", WizUtilDate.formatTimestampUser(now));
        }
        if (result.contains("${instantUser}")) {
            result = result.replace("${instantUser}", WizUtilDate.formatInstantUser(now));
        }
        if (result.contains("${dateMach}")) {
            result = result.replace("${dateMach}", WizUtilDate.formatDateMach(now));
        }
        if (result.contains("${timeMach}")) {
            result = result.replace("${timeMach}", WizUtilDate.formatTimeMach(now));
        }
        if (result.contains("${millisMach}")) {
            result = result.replace("${millisMach}", WizUtilDate.formatMillisMach(now));
        }
        if (result.contains("${dateTimeMach}")) {
            result = result.replace("${dateTimeMach}", WizUtilDate.formatDateTimeMach(now));
        }
        if (result.contains("${timestampMach}")) {
            result = result.replace("${timestampMach}", WizUtilDate.formatTimestampMach(now));
        }
        if (result.contains("${instantMach}")) {
            result = result.replace("${instantMach}", WizUtilDate.formatInstantMach(now));
        }
        if (result.contains("${dateFile}")) {
            result = result.replace("${dateFile}", WizUtilDate.formatDateFile(now));
        }
        if (result.contains("${timeFile}")) {
            result = result.replace("${timeFile}", WizUtilDate.formatTimeFile(now));
        }
        if (result.contains("${millisFile}")) {
            result = result.replace("${millisFile}", WizUtilDate.formatMillisFile(now));
        }
        if (result.contains("${dateTimeFile}")) {
            result = result.replace("${dateTimeFile}", WizUtilDate.formatDateTimeFile(now));
        }
        if (result.contains("${timestampFile}")) {
            result = result.replace("${timestampFile}", WizUtilDate.formatTimestampFile(now));
        }
        if (result.contains("${instantFile}")) {
            result = result.replace("${instantFile}", WizUtilDate.formatInstantFile(now));
        }
        result = replaceEnvVars(result);
        result = remakeBreaks(result);
        return result;
    }

    public static String replaceEnvVars(String chars) {
        return replaceEnvVars(chars, "env");
    }

    public static String replaceEnvVars(String chars, String withPrefix) {
        if (WizString.isEmpty(chars)) {
            return chars;
        }
        var result = chars;
        var envPos = result.indexOf("${" + withPrefix + ":");
        while (envPos > -1) {
            var envPosEnd = result.indexOf("}", envPos);
            if (envPosEnd == -1) {
                break;
            }
            var envName = result.substring(envPos + 6, envPosEnd);
            var envValue = System.getenv(envName);
            if (envValue == null) {
                envValue = "";
            }
            result = result.substring(0, envPos) + envValue + result.substring(envPosEnd + 1);
            envPos = result.indexOf("${" + withPrefix + ":", envPos + 1);
        }
        return result;
    }

    public static Map<String, String> getAssigned(String chars) {
        var result = new HashMap<String, String>();
        if (WizString.isEmpty(chars)) {
            return result;
        }
        var openQuotes = false;
        var foundEquals = false;
        var key = new StringBuilder();
        var val = new StringBuilder();
        for (var i = 0; i < chars.length(); i++) {
            var actual = chars.charAt(i);
            var next = i < chars.length() - 1 ? chars.charAt(i + 1) : ' ';
            if (openQuotes) {
                if (actual == '"') {
                    openQuotes = false;
                } else {
                    if (actual == '\\') {
                        if (next == '\\' || next == '"' || next == '=' || next == ';') {
                            actual = next;
                            i++;
                        } else if (next == 'n') {
                            actual = '\n';
                            i++;
                        } else if (next == 'r') {
                            actual = '\r';
                            i++;
                        } else if (next == 't') {
                            actual = '\t';
                            i++;
                        } else if (next == 'f') {
                            actual = '\f';
                            i++;
                        } else if (next == 'b') {
                            actual = '\b';
                            i++;
                        }
                    }
                    if (foundEquals) {
                        val.append(actual);
                    } else {
                        key.append(actual);
                    }
                }
            } else {
                if (actual == '"') {
                    openQuotes = true;
                }
                if (actual == '=') {
                    foundEquals = true;
                } else if (actual == ';') {
                    if (foundEquals) {
                        result.put(key.toString(), val.toString());
                        foundEquals = false;
                    } else {
                        result.put(key.toString(), "");
                    }
                    key = new StringBuilder();
                    val = new StringBuilder();
                } else {
                    if (foundEquals) {
                        val.append(actual);
                    } else {
                        key.append(actual);
                    }
                }
            }
        }
        return result;
    }

    public static String getNext(String lastChars, Boolean onlyNumbers) {
        StringBuilder result = new StringBuilder();
        boolean done = false;
        for (int i = lastChars.length() - 1; i > -1; i--) {
            char doing = lastChars.charAt(i);
            if (!done) {
                result.insert(0, getNext(doing, onlyNumbers));
                if (!isLastInOrd(doing, onlyNumbers)) {
                    done = true;
                }
            } else {
                result.insert(0, doing);
            }
        }
        return result.toString();
    }

    public static char getNext(char lastChar, boolean onlyNumbers) {
        char result = ' ';
        if (lastChar == ' ') {
            result = '0';
        } else if (lastChar == '0') {
            result = '1';
        } else if (lastChar == '1') {
            result = '2';
        } else if (lastChar == '2') {
            result = '3';
        } else if (lastChar == '3') {
            result = '4';
        } else if (lastChar == '4') {
            result = '5';
        } else if (lastChar == '5') {
            result = '6';
        } else if (lastChar == '6') {
            result = '7';
        } else if (lastChar == '7') {
            result = '8';
        } else if (lastChar == '8') {
            result = '9';
        } else if (lastChar == '9') {
            if (onlyNumbers) {
                result = '0';
            } else {
                result = 'A';
            }
        } else if (!onlyNumbers) {
            if (lastChar == 'A') {
                result = 'B';
            } else if (lastChar == 'B') {
                result = 'C';
            } else if (lastChar == 'C') {
                result = 'D';
            } else if (lastChar == 'D') {
                result = 'E';
            } else if (lastChar == 'E') {
                result = 'F';
            } else if (lastChar == 'F') {
                result = 'G';
            } else if (lastChar == 'G') {
                result = 'H';
            } else if (lastChar == 'H') {
                result = 'I';
            } else if (lastChar == 'I') {
                result = 'J';
            } else if (lastChar == 'J') {
                result = 'K';
            } else if (lastChar == 'K') {
                result = 'L';
            } else if (lastChar == 'L') {
                result = 'M';
            } else if (lastChar == 'M') {
                result = 'N';
            } else if (lastChar == 'N') {
                result = 'O';
            } else if (lastChar == 'O') {
                result = 'P';
            } else if (lastChar == 'P') {
                result = 'Q';
            } else if (lastChar == 'Q') {
                result = 'R';
            } else if (lastChar == 'R') {
                result = 'S';
            } else if (lastChar == 'S') {
                result = 'T';
            } else if (lastChar == 'T') {
                result = 'U';
            } else if (lastChar == 'U') {
                result = 'V';
            } else if (lastChar == 'V') {
                result = 'W';
            } else if (lastChar == 'W') {
                result = 'X';
            } else if (lastChar == 'X') {
                result = 'Y';
            } else if (lastChar == 'Y') {
                result = 'Z';
            } else if (lastChar == 'Z') {
                result = '0';
            }
        }
        return result;
    }

    public static boolean isLastInOrd(char ch, boolean onlyNumbers) {
        return (ch == '9' && onlyNumbers) || (ch == 'Z' && !onlyNumbers);
    }
    
}
