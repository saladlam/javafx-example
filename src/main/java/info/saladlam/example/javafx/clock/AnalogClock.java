package info.saladlam.example.javafx.clock;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class AnalogClock extends Application {

	private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// clock panel
		Circle outer = new Circle(150, 150, 140);
		outer.setFill(Color.WHITE);
		outer.setStroke(Color.BLACK);
		outer.setStrokeWidth(2.0);
		Group group = new Group(outer);
		ObservableList<Node> groupList = group.getChildren();
		for (int i = 0; i < 12; i++) {
			Line a = new Line(150, 20, 150, 40);
			a.setStroke(Color.BLACK);
			a.setStrokeWidth(3.0);
			Rotate rA = new Rotate(i * 30, 150, 150);
			a.getTransforms().add(rA);
			groupList.add(a);
		}

		// clock needle
		Line hr = new Line(150, 80, 150, 155);
		hr.setStroke(Color.BLACK);
		hr.setStrokeWidth(8.0);
		Line min = new Line(150, 60, 150, 155);
		min.setStroke(Color.BLACK);
		min.setStrokeWidth(6.0);
		Line sec = new Line(150, 50, 150, 155);
		sec.setStroke(Color.BLACK);
		sec.setStrokeWidth(2.0);
		groupList.addAll(hr, min, sec);

		LocalTime current = LocalTime.now();
		final Rotate rHr = new Rotate(getHourDegree(current), 150, 150);
		hr.getTransforms().add(rHr);
		final Rotate rMin = new Rotate(getMinuteDegree(current), 150, 150);
		min.getTransforms().add(rMin);
		final Rotate rSec = new Rotate(getSecondDegree(current), 150, 150);
		sec.getTransforms().add(rSec);

		this.service.scheduleAtFixedRate(() -> {
			LocalTime now = LocalTime.now();
			rHr.setAngle(getHourDegree(now));
			rMin.setAngle(getMinuteDegree(now));
			rSec.setAngle(getSecondDegree(now));
		}, 1, 1, TimeUnit.SECONDS);

		Scene scene = new Scene(group, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Analog Clock");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		this.service.shutdown();
	}

	private static double getHourDegree(LocalTime time) {
		return ((time.getHour() % 12) * 60 + time.getMinute()) / 2;
	}

	private static double getMinuteDegree(LocalTime time) {
		return (time.getMinute() * 60 + time.getSecond()) / 10;
	}

	private static double getSecondDegree(LocalTime time) {
		return time.getSecond() * 6;
	}

}
