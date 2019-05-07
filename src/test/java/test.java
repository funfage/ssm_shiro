import java.util.UUID;

public class test {

    public static void main(String[] args) {
        int hashcodeV = UUID.randomUUID().hashCode();
        if(hashcodeV < 0){
            hashcodeV = -hashcodeV;
        }
        String uuidSalt = String.format("%016d", hashcodeV);
        System.out.println(uuidSalt);
    }
}
