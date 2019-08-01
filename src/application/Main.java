package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;


public class Main extends Application {
	
	/* ===================================================

	Metodo          - start
	Descricao       - Carrega a janela inicial da interface grafica.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("login_perfil.fxml"));
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
