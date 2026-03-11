package classeJeu;
import java.util.Random;

public class ItemFactory {
    private static final Random random = new Random();

    public static Item creerItem(String type) {
        switch (type.toLowerCase()) {
            case "bonus":
                return new Element(random.nextInt(10) + 1);
            case "malus":
                return new Element(-(random.nextInt(10) + 1));
            case "feu":
                return new AdaptateurFeu(new Feu());
            case "eau":
                return new AdaptateurEau(new Eau());
            case "poison":
                return new Poison();
            case "antidote":
                return new Antidote();
            case "piege":
                return new Piege();
            default:
                return null;
        }
    }
}