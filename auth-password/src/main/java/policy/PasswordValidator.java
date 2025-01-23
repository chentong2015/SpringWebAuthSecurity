package policy;

// Password Validator: 密码验证器
// - 验证密码的创建合规
// - 验证密码的更新合规
public class PasswordValidator {

    private final PasswordPolicy passwordPolicy;

    public PasswordValidator() {
        this.passwordPolicy = new PasswordPolicy();
    }

    // 密码不应该围绕用户名称来建立
    public boolean validatePasswordCreation(String username, String password) {
        return passwordPolicy.isMatched(password) && !password.contains(username);
    }

    public boolean validatePasswordCreationWithEmail(String email, String password) {
        // validate email
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        String name = email.split("@")[0];
        return passwordPolicy.isMatched(password) && !password.contains(name);
    }

    // 新密码不应该建立在旧密码的基础上
    public boolean validatePasswordUpdate(String oldPassword, String newPassword, String newPasswordAgain) {
        if (!newPassword.equals(newPasswordAgain)) {
            return false;
        }
        if (!passwordPolicy.isMatched(newPassword)) {
            return false;
        }
        return !newPassword.equals(oldPassword) && !newPassword.contains(oldPassword);
    }
}
