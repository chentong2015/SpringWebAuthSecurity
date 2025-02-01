package policy;

import java.util.List;

// 约束该类型只在当前包路径下可访问，避免外部使用Policy
class PasswordPolicy {

    private static final int MIN_LENGTH = 8;
    private static final int MIN_NUM_LENGTH = 1;
    private static final int MIN_NUM_SPECIAL_CHARS = 1;
    private final List<Character> charList;
    private final List<String> commonPasswords;

    // 使用非公共非常见的密码能够有效避免被暴力破解
    PasswordPolicy() {
        this.charList = List.of('&', '%', '-', '@', '/');
        this.commonPasswords = List.of("hello123&", "password123&", "test12345&");
    }

    boolean isMatched(String password) {
       return isMatchRequirements(password) && isNotCommonPassword(password);
    }

    private boolean isMatchRequirements(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }
        int countNum = 0;
        int countSpecialChar = 0;
        for (char c: password.toCharArray()) {
            if (Character.isDigit(c)) {
                countNum++;
            } else if (this.charList.contains(c)) {
                countSpecialChar++;
            }
        }
        if (countNum < MIN_NUM_LENGTH) {
            return false;
        }
        return countSpecialChar >= MIN_NUM_SPECIAL_CHARS;
    }

    private boolean isNotCommonPassword(String password) {
         return !this.commonPasswords.contains(password);
    }
}
