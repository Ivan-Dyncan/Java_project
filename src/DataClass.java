import java.util.ArrayList;

public class DataClass {
    String section;
    String subsection;
    String title;
    String description;
    String dataStart;
    String dataEnd;
    String address;
    int participants;


    public DataClass(String s){
        var d = s.split(";");
        section = d[0];
        subsection = d[1];
        title = d[2].replace("'", "\"");
        description = d[3].replace("'", "\"");
        dataStart = d[4];
        dataEnd = d[5];
        address = d[6].split(",")[0].replace("\"", "");;
        try {
            participants = Integer.parseInt(d[7]);
        }
        catch (Exception e){
            participants = 0;
        }
    }
}


