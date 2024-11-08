package encoder;

import java.security.SecureRandom;

// TODO. 自定义实现SecureRandom.nextBytes()用于获取加密的Salt
// 利用Password+Salt才能验证生的Hash值是否一致
// - 如果不存在Salt则生成一个新的随机Salt
// - 如果已经存在Salt则设置到类型属性值保存
public final class SaltSecureRandom extends SecureRandom {

    private final byte[] bytesSalt;

    public SaltSecureRandom() {
        this.bytesSalt = SaltBytesGenerator.generateNewSaltBytes();
    }

    public SaltSecureRandom(byte[] bytesSalt) {
        this.bytesSalt = bytesSalt;
    }

    @Override
    public void nextBytes(byte[] bytes) {
        System.arraycopy(this.bytesSalt, 0, bytes, 0, this.bytesSalt.length);
    }
}
