package encoder;

import java.security.SecureRandom;

// TODO. 自定义实现SecureRandom.nextBytes()用于获取加密的Salt
// 利用Password+Salt才能验证生的Hash值是否一致
public final class SaltSecureRandom extends SecureRandom {

    private byte[] bytesSalt = new byte[16];

    // 生成一个随机的Salt Bytes用于加密密码
    public SaltSecureRandom() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(this.bytesSalt);
    }

    // 使用特定输入bytes作为Salt参与加密
    public SaltSecureRandom(byte[] bytesSalt) {

        this.bytesSalt = bytesSalt;
    }

    @Override
    public void nextBytes(byte[] bytes) {

        System.arraycopy(this.bytesSalt, 0, bytes, 0, this.bytesSalt.length);
    }

    public byte[] getBytesSalt() {
        return bytesSalt;
    }
}
