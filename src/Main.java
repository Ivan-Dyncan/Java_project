import org.jfree.ui.RefineryUtilities;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        var a = new GetDataFromCSV("Спорт.csv");
        var parsData = new ArrayList<DataClass>();
        for (var i = 0; i<a.rows.size(); i++) {
            if (a.rows.get(i).split(";").length==8)
                parsData.add(new DataClass(a.rows.get(i)));
        }

        SqlLiteConnection.Conn();
        SqlLiteConnection.CreateDB();
        //SqlLiteConnection.WriteDB(parsData);
        //SqlLiteConnection.ReadDB();

        //SqlLiteConnection.SecondTask();
        Chart chart = new Chart(SqlLiteConnection.FirstTask(),"Гистограмма",
                "Кол-во спортсменов/виды спорта");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        //SqlLiteConnection.ThirdTask();
        SqlLiteConnection.CloseDB();

    }
}
