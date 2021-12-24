import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Chart extends ApplicationFrame {

    public Chart(ResultSet resultSet , String applicationTitle , String chartTitle ) throws SQLException {
        super( applicationTitle );
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Вид спорта",
                "Количество участников",
                createDataset(resultSet),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        chartPanel.setPreferredSize(new java.awt.Dimension( 1000 , 500 ) );
        setContentPane( chartPanel );
    }

    private CategoryDataset createDataset(ResultSet resSet) throws SQLException {
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );
        var name = "data";
        while(resSet.next()){
            dataset.addValue(Double.parseDouble(resSet.getString("count")), name, resSet.getString("section"));
        }
        return dataset;
    }
}
