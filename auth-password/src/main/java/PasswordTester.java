import encoder.MyPasswordEncoder;
import encoder.SaltSecureRandom;

public class PasswordTester {

    // TODO. 相同的密码 + 相同的Salt -> Hash加密成相同结果
    public static void main(String[] args) {
        SaltSecureRandom saltSecureRandom = new SaltSecureRandom();

        MyPasswordEncoder passwordEncoder = new MyPasswordEncoder();
        String encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        encodedPassword = passwordEncoder.encodePasswordBCrypt("password321", saltSecureRandom);
        System.out.println(encodedPassword);

        encodedPassword = passwordEncoder.encodePasswordBCrypt("password123", saltSecureRandom);
        System.out.println(encodedPassword);
    }
}
