package com.example.tap2024_7.VISTAS;

import com.example.tap2024_7.COMPONENTS.ButtonCell;
import com.example.tap2024_7.MODELS.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tbvClientes;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaClientes() {
        CrearUI();
        this.setTitle("Lista de Clientes");
        Scene scene = new Scene(vBox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/listaclientes.css").toExternalForm());
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        // Crear ToolBar
        tlbMenu = new ToolBar();
        tlbMenu.getStyleClass().add("tool-bar");

        ImageView imv = new ImageView(getClass().getResource("/images/next.png").toString());
        Button btnAddCte = new Button("Agregar Cliente");
        btnAddCte.getStyleClass().add("button");
        btnAddCte.setOnAction(actionEvent -> new FormCliente(tbvClientes, null));
        btnAddCte.setGraphic(imv);

        tlbMenu.getItems().add(btnAddCte);

        // Crear TableView
        tbvClientes = new TableView<>();
        tbvClientes.getStyleClass().add("table-view");
        CrearTable();

        // Crear VBox
        vBox = new VBox(tlbMenu, tbvClientes);
        vBox.getStyleClass().add("vbox");
    }

    private void CrearTable() {
        ClienteDAO objCte = new ClienteDAO();

        TableColumn<ClienteDAO, String> tbcNomCte = new TableColumn<>("Cliente");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClienteDAO, String> tbcEmailCte = new TableColumn<>("Email");
        tbcEmailCte.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClienteDAO, String> tbcTelCte = new TableColumn<>("Teléfono");
        tbcTelCte.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClienteDAO, String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> param) {
                return new ButtonCell("Editar");
            }
        });

        TableColumn<ClienteDAO, String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> param) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte, tbcEmailCte, tbcTelCte, tbcEditar, tbcEliminar);
        tbvClientes.setItems(objCte.SELECTALL());
    }
}
