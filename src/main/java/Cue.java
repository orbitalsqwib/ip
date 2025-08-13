import divider.*;

public class Cue {
    public static void main(String[] args) {
        Divider div = new Divider(80);
        String logo = "  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

        div.print();
        System.out.println("Hello from\n" + logo);
        div.print();
    }
}
