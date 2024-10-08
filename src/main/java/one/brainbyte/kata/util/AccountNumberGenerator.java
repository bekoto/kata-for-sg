package one.brainbyte.kata.util;
import java.util.Random;
public class AccountNumberGenerator {


    private AccountNumberGenerator(){}

    public static String generateAccountNumber() {
        Random random = new Random();

        // Générer un nombre aléatoire entre 10000 et 99999
        int randomNumber = 0 + random.nextInt(5000);
        // Convertir le nombre en chaîne de caractères
        String randomString = String.valueOf(randomNumber);

        return randomString;
    }
}
