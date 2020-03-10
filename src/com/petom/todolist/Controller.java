package com.petom.todolist;

import com.petom.todolist.datamodel.ToDoData;
import com.petom.todolist.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems;
    @FXML
//    private ListView todoListView;
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;


    public void initialize() {
//        ToDoItem item1 = new ToDoItem("Test Listy 1", "Opis dla testu listy 1",
//                LocalDate.of(2020, Month.MAY, 3));
//        ToDoItem item2 = new ToDoItem("Test Listy 2", "Opis dla testu listy 2",
//                LocalDate.of(2020, Month.DECEMBER, 10));
//        ToDoItem item3 = new ToDoItem("Test Listy 3", "Opis dla testu listy 3",
//                LocalDate.of(2020, Month.AUGUST, 7));
//        ToDoItem item4 = new ToDoItem("Test Listy 4", "Opis dla testu listy 4",
//                LocalDate.of(2020, Month.JANUARY, 13));
//
//        toDoItems = new ArrayList<ToDoItem>();
//        toDoItems.add(item1);
//        toDoItems.add(item2);
//        toDoItems.add(item3);
//        toDoItems.add(item4);
//
//        ToDoData.getInstance().setToDoItems(toDoItems);

//        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
//            @Override
//            public void changed(ObservableValue<? extends ToDoItem> observableValue, ToDoItem toDoItem, ToDoItem t1) {
//                if(t1 != null){
//                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
//                    itemDetailsTextArea.setText(item.getDetails());
//                    deadlineLabel.setText(item.getDeadline().toString());
//                }
//            }
//        });

        todoListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends ToDoItem> observableValue, ToDoItem toDoItem, ToDoItem t1) -> {
                    if (t1 != null) {
                        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                        itemDetailsTextArea.setText(item.getDetails());
//                        deadlineLabel.setText(item.getDeadline().toString());
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMMM, yyyy");
                        deadlineLabel.setText(df.format(item.getDeadline()));
                    }
                });

//        todoListView.getItems().setAll(toDoItems);
        todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleClickListView() {
//        ToDoItem item = (ToDoItem) todoListView.getSelectionModel().getSelectedItem();
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());

//        System.out.println("The selected item is " + item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Termin: ");
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString());


    }

}
