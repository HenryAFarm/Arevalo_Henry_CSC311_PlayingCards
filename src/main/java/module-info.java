module org.example.arevalo_henry_csc311_playingcards {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.arevalo_henry_csc311_playingcards to javafx.fxml;
    exports org.example.arevalo_henry_csc311_playingcards;
}