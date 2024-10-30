package com.example.tap2024_7.COMPONENTS;

import com.example.tap2024_7.MODELS.ClienteDAO;
import com.example.tap2024_7.VISTAS.FormCliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCell extends TableCell<ClienteDAO, String> {
    Button btnCelda;

    public ButtonCell(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> EventoVoton(str));
    }

    public ButtonCell() {

    }

    private void EventoVoton(String str) {
        ClienteDAO objCliente = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormCliente(this.getTableView(),objCliente);

        } else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Mensaje del sistema");
            alerta.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if(opcion.get() == ButtonType.OK){
                objCliente.DELETE();
                this.getTableView().setItems(objCliente.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}