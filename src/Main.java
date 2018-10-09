import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Parser ps = new Parser();

        String[] key = {"Gundam", "Mobile Suit", "Gundam", "Wikia"};

        try{
            ps.getURL(key);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
