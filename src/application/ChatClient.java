package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChatClient extends Application {
	static String mensajeEnviado = "";
	DataInputStream dis;
	DataOutputStream dos;

	public ResourceBundle rb;

	public ChatClient(ResourceBundle rb) {
		this.rb = rb;
	}

	@Override
	public void start(Stage stage) throws Exception {

		// Generar menu
		// Menu
		MenuBar menu = new MenuBar();

		// Menu de regresar al menu principal
		Menu change = new Menu(rb.getString("back"));
		MenuItem backButton = new MenuItem(rb.getString("home"));
		change.getItems().addAll(backButton);

		// Añadir todo al menu
		menu.getMenus().addAll(change);

		// Creación de la ventana raíz y resultados
		VBox root = new VBox();
		FlowPane mainResults = new FlowPane();

		// Espacio para el historial de vectores
		VBox mainTable = new VBox();
		mainTable.setStyle("-fx-min-height:500px; -fx-min-width:100px; -fx-padding:5px; -fx-padding-top:10px;");
		Label messages = new Label(rb.getString("messages"));
		TextArea textmsgs = new TextArea();
		textmsgs.setStyle("-fx-min-height:400px");
		textmsgs.setEditable(false);
		Label writebox = new Label(rb.getString("current_message"));
		TextArea currentMessage = new TextArea();
		currentMessage.setPrefHeight(100d);
		Button send = new Button(rb.getString("send"));
		mainTable.setPadding(new Insets(10, 0, 0, 10));
		mainTable.setSpacing(5);
		mainTable.getChildren().addAll(messages, textmsgs, writebox, currentMessage, send);

		// Añadir historial y resultados a la pantalla
		mainResults.setPadding(new Insets(10, 0, 0, 10));
		mainResults.getChildren().addAll(mainTable);
		root.setSpacing(5d);
		root.getChildren().addAll(menu, mainResults);

		// Eventos
		// Regresar al menu principal
		backButton.setOnAction((event) -> {
			Start main = new Start();
			try {
				main.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		// Settings de stage
		Scene scene = new Scene(root, 1000, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setHeight(primaryScreenBounds.getHeight());
		stage.setMaxHeight(768d);
		stage.setWidth(515);
		stage.setResizable(false);
		stage.setTitle(rb.getString("chat_client_title"));
		stage.show();
		stage.centerOnScreen();

		// Comunicación mediante sockets manejada por el segundo hilo
		new Thread(() -> {
			send.setOnAction((event) -> {
				mensajeEnviado = currentMessage.getText().trim();
				currentMessage.setText("");
				textmsgs.setText(textmsgs.getText().trim() + "\n" + rb.getString("username") + ": " + mensajeEnviado);
				try {
					dos.writeUTF(mensajeEnviado);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, rb.getString("service_error"), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			String hostName = "localhost";
			int port = 10002;
			try (Socket socket = new Socket(hostName, port)) {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(rb.getLocale().getLanguage());
				dos.writeUTF(rb.getLocale().getCountry());
				textmsgs.setText(dis.readUTF());
				textmsgs.setText(textmsgs.getText() + "\n" + dis.readUTF());
				while (!mensajeEnviado.equals(rb.getString("confirmation"))) {
					String mensajeServidor = dis.readUTF();
					textmsgs.setText(textmsgs.getText().trim() + "\n Tutor: " + mensajeServidor);
				}
				socket.close();
				textmsgs.setText(textmsgs.getText().trim() + "\n" + rb.getString("session_ended"));
			} catch (UnknownHostException e1) {
				JOptionPane.showMessageDialog(null, rb.getString("service_error"), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, rb.getString("service_error"), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}).start();

	}

}
