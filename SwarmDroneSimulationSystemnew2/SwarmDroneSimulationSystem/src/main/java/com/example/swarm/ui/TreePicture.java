package com.example.swarm.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class TreePicture extends Group {
	
	public TreePicture() {
		this(100, 250, 20, 60, 80, 70);
		/*
   // Draw trees of different sizes and positions
        drawTree(root, 100, 250, 20, 60, 80, 70, Color.FORESTGREEN);   // Small tree
        drawTree(root, 220, 280, 28, 90, 120, 100, Color.GREEN);        // Medium tree
        drawTree(root, 350, 300, 36, 120, 160, 130, Color.DARKGREEN);   // Large
		 */
	}

	public TreePicture(double centerX, double baseY,
            double trunkWidth, double trunkHeight,
            double canopyWidth, double canopyHeight) {
		drawTree(centerX, baseY, trunkWidth, trunkHeight, canopyWidth, canopyHeight, Color.DARKGREEN);
	}

    /**
     * Draws a generic broadleaf tree.
     *
     * @param group         The JavaFX Group to add the tree to.
     * @param centerX       X coordinate of the tree's center.
     * @param baseY         Y coordinate of the base of the trunk.
     * @param trunkWidth    Width of the trunk.
     * @param trunkHeight   Height of the trunk.
     * @param canopyWidth   Width of the canopy (ellipse).
     * @param canopyHeight  Height of the canopy (ellipse).
     * @param canopyColor   Color of the canopy.
     */
    private void drawTree(double centerX, double baseY,
                               double trunkWidth, double trunkHeight,
                               double canopyWidth, double canopyHeight,
                               Color canopyColor) {
        // Draw trunk
        Rectangle trunk = new Rectangle(centerX - trunkWidth / 2, baseY - trunkHeight, trunkWidth, trunkHeight);
        trunk.setFill(Color.SADDLEBROWN);
        getChildren().add(trunk);

        // Draw canopy (main ellipse)
        Ellipse canopy = new Ellipse(centerX, baseY - trunkHeight, canopyWidth / 2, canopyHeight / 2);
        canopy.setFill(canopyColor);
        getChildren().add(canopy);

        // Optional: Add more ellipses for a "cloudy" canopy
        Ellipse canopy2 = new Ellipse(centerX - canopyWidth / 4, baseY - trunkHeight - canopyHeight / 4, canopyWidth / 3, canopyHeight / 3);
        canopy2.setFill(canopyColor.darker());
        getChildren().add(canopy2);

        Ellipse canopy3 = new Ellipse(centerX + canopyWidth / 4, baseY - trunkHeight - canopyHeight / 4, canopyWidth / 3, canopyHeight / 3);
        canopy3.setFill(canopyColor.brighter());
        getChildren().add(canopy3);
    }
    
}
