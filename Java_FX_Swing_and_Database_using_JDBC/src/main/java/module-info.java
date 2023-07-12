module exercise1.liangyuwang_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens exercise1.liangyuwang_comp228lab5 to javafx.fxml;
    exports exercise1.liangyuwang_comp228lab5;
}