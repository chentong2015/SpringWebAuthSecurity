package org.example.credentials;

// 完整的用户Auth认证的Model, 包含所有认证相关信息
public class FullUserCredentials {

    private String username;
    private String password;
    private String group;
    private String desk;
    private TrustedToken jwtToken;

    private boolean passwordEncrypted;
    private boolean isTokenBased;
    private boolean isPasswordBased;

    public FullUserCredentials(String username, TrustedToken trustedToken) {
        this(username, trustedToken, null);
    }

    public FullUserCredentials(String username, TrustedToken trustedToken, String group) {
        notEmpty(username, "Username is null or empty; username: '" + username + "'");
        if ((trustedToken == null) || (trustedToken.getTokenString() == null) || trustedToken.getTokenString().isEmpty()) {
            throw new IllegalArgumentException("TrustedToken can not be empty");
        }
        this.username = username;
        this.jwtToken = trustedToken;
        this.group = group;
        this.isTokenBased = true;
    }

    public FullUserCredentials(String username, String password, boolean passwordEncrypted, String group, String desk) {
        notEmpty(username, "Username is null or empty; username: '" + username + "'");
        this.isPasswordBased = true;
        this.username = username;
        this.group = group;
        this.desk = desk;
        this.password = password;
        this.passwordEncrypted = passwordEncrypted;
    }

    public static void notEmpty(String string, String message) {
        if ((string == null) || (string.length() == 0)) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((desk == null) ? 0 : desk.hashCode());
        result = (prime * result) + ((group == null) ? 0 : group.hashCode());
        result = (prime * result) + ((password == null) ? 0 : password.hashCode());
        result = (prime * result) + (passwordEncrypted ? 1231 : 1237);
        result = (prime * result) + ((username == null) ? 0 : username.hashCode());
        result = (prime * result) + ((jwtToken == null) ? 0 : jwtToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FullUserCredentials other = (FullUserCredentials) obj;
        if (desk == null) {
            if (other.desk != null)
                return false;
        } else if (!desk.equals(other.desk))
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (passwordEncrypted != other.passwordEncrypted)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (jwtToken == null) {
            if (other.jwtToken != null)
                return false;
        } else if (!jwtToken.equals(other.jwtToken))
            return false;
        return true;
    }

    class TrustedToken {

        private String token;

        public TrustedToken(String token) {
            this.token = token;
        }

        public String getTokenString() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
