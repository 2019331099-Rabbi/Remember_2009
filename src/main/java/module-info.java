module rabbimidu.remember_2009 {
    requires javafx.controls;
    requires javafx.fxml;


    opens rabbimidu.remember_2009 to javafx.fxml;
    exports rabbimidu.remember_2009;
    exports rabbimidu.remember_2009.LevelControllers;
    opens rabbimidu.remember_2009.LevelControllers to javafx.fxml;
}