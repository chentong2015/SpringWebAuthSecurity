package encoder;

import java.security.SecureRandom;

public final class SecureSaltRandom extends SecureRandom {

    private final byte[] bytesSalt;

    public SecureSaltRandom(byte[] bytesSalt) {
        this.bytesSalt = bytesSalt;
    }

    @Override
    public void nextBytes(byte[] bytes) {
        bytes = this.bytesSalt;
    }

    public byte[] getSaltValue() {
        return this.bytesSalt;
    }
}
