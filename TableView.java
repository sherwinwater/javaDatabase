package dbproject1;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.grios.tableadapter.DefaultTableAdapter;
 
public class TableView extends javafx.application.Application
{
    private BorderPane pnlRoot;
    private TableView myTableView;
    private DefaultTableAdapter dta;
 
    Scene scene;
 
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        initComponents();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    private void initComponents()
    {
        pnlRoot = new BorderPane();
        pnlRoot.prefWidthProperty().set(480);
        pnlRoot.prefHeightProperty().set(320);
        myTableView = new TableView();
        pnlRoot.setCenter(myTableView);
        buildTableData();
 
        scene = new Scene(pnlRoot);
    }
 
    private void buildTableData()
    {
        String[]    columnNames =   {   "First Name",
                                        "Last Name",
                                        "Sport",
                                        "# of Years",
                                        "Vegetarian"
                                    };
        Object[][]  data =  {
                                {"Kathy", "Smith",
                                 "Snowboarding", new Integer(5), new Boolean(false)},
                                {"John", "Doe",
                                 "Rowing", new Integer(3), new Boolean(true)},
                                {"Sue", "Black",
                                 "Knitting", new Integer(2), new Boolean(false)},
                                {"Jane", "White",
                                 "Speed reading", new Integer(20), new Boolean(true)},
                                {"Joe", "Brown",
                                 "Pool", new Integer(10), new Boolean(false)}
                            };
        dta = new DefaultTableAdapter(myTableView, data, columnNames);
    }
    public static void main(String[] args)
    {
        launch (args);
    }
}
