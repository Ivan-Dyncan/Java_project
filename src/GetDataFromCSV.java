import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetDataFromCSV {
    String path;
    ArrayList<String> rows = new ArrayList<>();

    public GetDataFromCSV(String path){
        this.path = path;
        rows = readCSV(path);
    }

    private ArrayList<String> readCSV(String path) {
        ArrayList<String> res = new ArrayList<>();

        String line = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            line = bufferedReader.readLine();
            while (line != null) {
                res.add(line);
                try {
                    line = bufferedReader.readLine();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Чтение файла не удалось");
        }
        res.remove(0);
        return res;
    }
}
