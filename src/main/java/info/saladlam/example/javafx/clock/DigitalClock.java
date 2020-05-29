package info.saladlam.example.javafx.clock;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DigitalClock extends Application {

	private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load digit image
		Map<Integer, Image> digit = new HashMap<>();
		for (int i = 0; i <= 9; i++) {
			digit.put(i, new Image("info/saladlam/example/javafx/clock/ui/" + i + ".png"));
		}
		Image blank = new Image("info/saladlam/example/javafx/clock/ui/blank.png");
		Image dot = new Image("info/saladlam/example/javafx/clock/ui/dot.png");

		// Layout
		ImageView vHr1 = new ImageView();
		ImageView vHr2 = new ImageView();
		ImageView vDot = new ImageView();
		ImageView vMin1 = new ImageView();
		ImageView vMin2 = new ImageView();
		HBox group = new HBox(vHr1, vHr2, vDot, vMin1, vMin2);
		group.setAlignment(Pos.CENTER);
		group.setStyle("-fx-background-color: #000000");
		Scene scene = new Scene(group, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Digital Clock");
		primaryStage.setResizable(false);

		this.service.scheduleAtFixedRate(() -> {
			LocalTime now = LocalTime.now();
			vHr1.setImage(digit.get(now.getHour() / 10));
			vHr2.setImage(digit.get(now.getHour() % 10));
			vDot.setImage(now.getNano() > 500000000 ? dot : blank);
			vMin1.setImage(digit.get(now.getMinute() / 10));
			vMin2.setImage(digit.get(now.getMinute() % 10));
		}, 0, 500, TimeUnit.MILLISECONDS);

		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		this.service.shutdown();
	}

}
