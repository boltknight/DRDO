package com.example.swarm.ui;

import java.util.List;

import com.example.swarm.model.Drone;
import com.example.swarm.model.DroneSwarm;
import com.example.swarm.model.Target;
import com.example.swarm.model.TargetType;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class LandscapeVisualizationFX extends StackPane {

    // base canvas (800 × 600 like your Swing panel)
    private final Canvas canvas = new Canvas(Screen.getPrimary().getVisualBounds().getWidth()*0.60,Screen.getPrimary().getVisualBounds().getHeight());

    private double waterAnim = 0;
    
    private DroneSwarm droneSwarm;

    public LandscapeVisualizationFX(DroneSwarm pDroneSwarm) {

//    	canvas = new Canvas(screenWidth*0.65, screenHeight*8.0);
        setPrefSize(canvas.getWidth(), canvas.getHeight());
        canvas.autosize();
        droneSwarm = pDroneSwarm;
        getChildren().add(canvas);
 

        // 60 fps animation loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                waterAnim += 0.08;
                if (waterAnim > 2 * Math.PI) waterAnim = 0;
                draw();
            }
        };
        timer.start();
    }

    private void draw() {
       
        GraphicsContext g = canvas.getGraphicsContext2D();

        // background
        g.setFill(Color.web("#F0F0F0"));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // water body (animated ellipse)
        double offset = Math.sin(waterAnim) * 5;
        g.setFill(Color.web("#4682B4"));  // steel blue
        g.fillOval(200, 430, 400, 140 + offset);

        // ripples
        g.setStroke(Color.web("#87CEEB")); // sky blue
        g.setLineWidth(2);
        g.beginPath();
        g.moveTo(350, 485);
        g.quadraticCurveTo(380, 475, 420, 485);
        g.quadraticCurveTo(460, 495, 480, 485);
        g.stroke();
        g.beginPath();
        g.moveTo(330, 505);
        g.quadraticCurveTo(370, 515, 410, 505);
        g.quadraticCurveTo(450, 495, 490, 505);
        g.stroke();

        // road & bridge
        g.setFill(Color.web("#333333"));
        g.fillRect(160, 420, 400, 40);

        g.setFill(Color.web("#555555"));
        g.fillRect(350, 420, 100, 40);
        g.setStroke(Color.web("#222222"));
        g.strokeRect(350, 420, 100, 40);
        g.setStroke(Color.web("#666666"));
        g.setLineWidth(2);
        g.strokeLine(350, 430, 450, 430);
        g.strokeLine(350, 450, 450, 450);

        // vegetation & objects
        drawTree(g, 100, 350, 10, 30, 20, Color.web("#228B22"));
        drawTree(g, 150, 380, 10, 25, 15, Color.web("#228B22"));
        drawTree(g, 600, 320, 12, 35, 25, Color.web("#228B22"));
        drawPine(g, 680, 360, 8, 30);
        drawTree(g, 250, 470, 8, 20, 15, Color.web("#2E8B57")); // sea-green

        // building & shed
        g.setFill(Color.web("#808080"));
        g.fillRect(240, 180, 120, 120);
        g.setFill(Color.web("#8B4513"));
        g.fillRect(480, 240, 60, 60);

        // drones + radius
        
//        drawDrone(g, 300, 200, 50);
//        drawDrone(g, 450, 300, 40);
        updateTargetLocation(g);
        updateDroneLocation(g);


        // legend
        drawLegend(g);
    }

    public void updateDroneLocation(GraphicsContext g) {
    	List<Drone> drones = droneSwarm.getDrones();
    	for (Drone d : drones) {
    		drawDrone(g, d.getPosition().getX(), d.getPosition().getY(), 30);
    	}
    }
    
    public void updateTargetLocation(GraphicsContext g) {
    	List<Target> targets = droneSwarm.getTargets();
    	for (Target t : targets) {
    		drawTarget(g, t.getLocation().getX(), t.getLocation().getY(), 40, t.getType());
    	}
    }
    
    /* ---------- helper drawing methods ---------- */

    private void drawTree(GraphicsContext g, double x, double y,
                          double trunkW, double trunkH,
                          double crownR, Color crown) {
        g.setFill(Color.web("#8B4513"));
        g.fillRect(x - trunkW / 2, y, trunkW, trunkH);
        g.setFill(crown);
        g.fillOval(x - crownR, y - trunkH / 2 - crownR, crownR * 2, crownR * 2);
    }

    private void drawPine(GraphicsContext g, double x, double y,
                          double trunkW, double trunkH) {
        g.setFill(Color.web("#8B4513"));
        g.fillRect(x - trunkW / 2, y, trunkW, trunkH);
        g.setFill(Color.web("#006400"));
        g.fillPolygon(new double[]{x - 20, x + 20, x}, new double[]{y, y, y - 40}, 3);
        g.fillPolygon(new double[]{x - 15, x + 15, x}, new double[]{y - 20, y - 20, y - 50}, 3);
    }

    private void drawDrone(GraphicsContext g, double x, double y, double radius) {
        g.setFill(Color.web("#0000FF20")); // transparent blue radius
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setFill(Color.BLUE);
        g.fillOval(x - 5, y - 5, 10, 10);
    }
    
    private void drawTarget(GraphicsContext g, double x, double y, double radius, TargetType targetType) {
    	Color color = null ;
    	String webcolor = "";
    	switch (targetType) {
	    	case RADAR : color = Color.DARKSEAGREEN;
	    	webcolor = "#3320AF60";
	    	break;
	    	case SAM : color = Color.BEIGE;
	    	webcolor = "#3320AF60";
	    	break;
	    	case ARTILLERY : color = Color.CRIMSON;
	    	webcolor = "#4320AF60";
	    	break;
	    	default: 
	    		color = Color.BROWN;
	    		webcolor = "#0320AF60";
    	}
        g.setFill(Color.web(webcolor)); // @todo need to see color transparent blue radius
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        
        g.setFill(color);
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    private void drawLegend(GraphicsContext g) {
    	
        Screen screen = Screen.getPrimary();
        double screenHeight = screen.getVisualBounds().getHeight();
        double startX = 10;
        double startY = screenHeight-200;
        
        g.setFill(Color.WHITE);
        g.fillRect(startX, startY, 120, 150);
        g.setStroke(Color.LIGHTGRAY);
        g.strokeRect(startX, startY, 120, 150);

        g.setFill(Color.BLACK);
        g.setFont(Font.font("SansSerif", 12));
        g.fillText("Legend:", startX+10, startY+20);

        g.setFill(Color.web("#808080")); g.fillRect(startX+10, startY+30, 20, 20);
        g.setFill(Color.BLACK); g.fillText("Building", startX+45, startY+45);

        g.setFill(Color.web("#8B4513")); g.fillRect(startX+10, startY+60, 20, 20);
        g.setFill(Color.BLACK); g.fillText("Shed",startX+45, startY+75);

        g.setFill(Color.web("#228B22")); g.fillOval(startX+10, startY+85, 20, 20);
        g.setFill(Color.BLACK); g.fillText("Trees",startX+45, startY+100);

        g.setFill(Color.web("#4682B4")); g.fillOval(startX+10, startY+115, 30, 16);
        g.setFill(Color.BLACK); g.fillText("Water", startX+45, startY+130);
    }
}
