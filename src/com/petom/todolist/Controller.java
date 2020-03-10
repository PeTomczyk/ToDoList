package com.petom.todolist;

import com.petom.todolist.datamodel.ToDoData;
import com.petom.todolist.datamodel.ToDoItem;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    private List<ToDoItem> toDoItems;

    @FXML
    private ListView<ToDoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList<ToDoItem> filteredList;

    private Predicate<ToDoItem> wantAllItems;
    private Predicate<ToDoItem> wantTodaysItems;

    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

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

        // Filtered list
        wantAllItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem item) {
                return true;
            }
        };
        wantTodaysItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem item) {
                return (item.getDeadline().equals(LocalDate.now()));
            }
        };
        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(), wantAllItems);
//        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(),
//                (ToDoItem item) -> {
//                    return true;
//                }
//        );
//        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(),
//                new Predicate<ToDoItem>() {
//                    @Override
//                    public boolean test(ToDoItem item) {
//                        return false;
//                    }
//                }
//        );

        // Sorted list
        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(filteredList,
//        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(ToDoData.getInstance().getToDoItems(),
                Comparator.comparing(ToDoItem::getDeadline)
        );

//        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(ToDoData.getInstance().getToDoItems(),
//                (o1, o2) -> {
//                    return o1.getDeadline().compareTo(o2.getDeadline());
//                }
//        );

//        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(ToDoData.getInstance().getToDoItems(),
//                new Comparator<ToDoItem>() {
//                    @Override
//                    public int compare(ToDoItem o1, ToDoItem o2) {
//                        return o1.getDeadline().compareTo(o2.getDeadline());
//                    }
//                });


//        todoListView.getItems().setAll(toDoItems);
//        todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
//        todoListView.setItems(ToDoData.getInstance().getToDoItems());
        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        // Color list item according to date
        todoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>() {
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New ToDo Item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResults();
//            todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
            todoListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
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

    public void deleteItem(ToDoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete ToDo Item");
        alert.setHeaderText("Delete item: " + item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    public void handleFilterButton() {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodaysItems);
            if (filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if (filteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }
}
