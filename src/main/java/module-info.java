module app {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.media;
	requires java.sql;
	requires javafx.graphics;

    opens app to javafx.fxml;
    exports app;
}
