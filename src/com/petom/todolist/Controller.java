package com.petom.todolist;

import com.petom.todolist.datamodel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems;
    @FXML
//    private ListView todoListView;
    private ListView<ToDoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Test Listy 1", "Opis dla testu listy 1",
                LocalDate.of(2020, Month.MAY, 3));
        ToDoItem item2 = new ToDoItem("Test Listy 2", "Opis dla testu listy 2",
                LocalDate.of(2020, Month.DECEMBER, 10));
        ToDoItem item3 = new ToDoItem("Test Listy 3", "Opis dla testu listy 3",
                LocalDate.of(2020, Month.AUGUST, 7));
        ToDoItem item4 = new ToDoItem("Test Listy 4", "Opis dla testu listy 4",
                LocalDate.of(2020, Month.JANUARY, 13));

        toDoItems = new ArrayList<ToDoItem>();
        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);

        todoListView.getItems().setAll(toDoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    public void handleClickListView() {
//        ToDoItem item = (ToDoItem) todoListView.getSelectionModel().getSelectedItem();
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
//        itemDetailsTextArea.setText(item.getDetails());
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Termin: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString());

    }

}
