import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SqlLiteConnection {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        //statmt.execute("DROP TABLE IF EXISTS 'sport'");
        statmt.execute("CREATE TABLE if not exists 'sport' " +
                "(" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'section' text," +
                "'subsection' text," +
                "'title' text," +
                "'description' text," +
                "'dataStart' text," +
                "'dataEnd' text," +
                "'address' text," +
                "'participants' INT" +
                ");");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB(ArrayList<DataClass> data) throws SQLException
    {
        var c = 0;
        for (var obj : data) {
            var sql = String.format("INSERT INTO 'sport' ('section', 'subsection', 'title', 'description', 'dataStart', 'dataEnd', 'address', 'participants') " +
                    "VALUES " +
                    "('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d" +
                    ");",obj.section, obj.subsection, obj.title, obj.description, obj.dataStart, obj.dataEnd, obj.address, obj.participants);
            statmt.execute(sql);
            System.out.println(c);
            c+=1;
        }

        System.out.println("Таблица заполнена");
    }

    // -------- 1ая задача
    public static ResultSet FirstTask() throws SQLException {
        var sql = "select section, sum(participants) as 'count' from 'sport' " +
                "where unicode(substring(section, 1, 1)) between unicode('А') and unicode('Я') " +
                "group by section;";
        resSet = statmt.executeQuery(sql);
        return resSet;
    }

    // -------- 2ая задача ----------
    public static void SecondTask() throws SQLException {
        statmt.execute("DROP view IF EXISTS 'Country';");
        var sql1 = "create view 'Country' as " +
                "select address from 'sport' " +
                "where substring(dataEnd, 7, 4) = '2008' and charindex(' ', address)=0 " +
                "group by address;";
        statmt.execute(sql1);
        var sql2 = "Select Country.address, sum(participants) as 'count' from 'sport', 'Country' " +
                "where substring(dataEnd, 7, 4) = '2008' and Country.address = sport.address " +
                "group by Country.address";
        resSet = statmt.executeQuery(sql2);
        while(resSet.next()){
            System.out.println(resSet.getString("address") + " - " + resSet.getString("count"));
        }
    }

    // -------- Третья задача -------
    public static void ThirdTask() throws SQLException {
        var sql = "select title, sum(participants) as 'count' from 'sport' " +
                "where section = 'Восточное боевое единоборство' and subsection = 'Молодежный (резервный) состав' " +
                "group by title " +
                "order by count desc " +
                "limit 1";
        resSet = statmt.executeQuery(sql);
        while(resSet.next())
            System.out.println(resSet.getString("title") + " - " + resSet.getString("count"));
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM 'sport'");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  section = resSet.getString("section");
            String address = resSet.getString("address");
            int  participants = resSet.getInt("participants");
            System.out.println( "ID = " + id );
            System.out.println( "section = " + section );
            System.out.println( "address = " + address);
            System.out.println( "participants = " + participants );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}