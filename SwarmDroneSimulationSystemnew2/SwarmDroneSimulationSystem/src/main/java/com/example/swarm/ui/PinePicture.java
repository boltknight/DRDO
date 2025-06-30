package com.example.swarm.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class PinePicture extends Group {
	
	public PinePicture() {
		this(100, 220, 150, 80, 4, 18, 30); 
/*
 *         drawPineTree(root, 100, 220, 150, 80, 4, 18, 30);  // Small tree
        drawPineTree(root, 200, 250, 200, 120, 5, 24, 40); // Medium tree
        drawPineTree(root, 320, 280, 250, 160, 6, 30, 50); // Large tree
 * */
	}
	
	public PinePicture(double centerX, double baseY, double totalHeight, double totalWidth,
               int sections, double trunkWidth, double trunkHeight) {
		drawPineTree(centerX, baseY, totalHeight, totalWidth, sections, trunkWidth, trunkHeight);
	}

	
    /**
     * Draws a pine tree on the provided Group.
     * 
     * @param group The JavaFX Group to add the tree shapes to.
     * @param centerX The x-coordinate of the tree's center.
     * @param baseY The y-coordinate of the base of the trunk.
     * @param totalHeight The total height of the tree (foliage + trunk).
     * @param totalWidth The width of the bottom foliage section.
     * @param sections Number of triangular foliage sections.
     * @param trunkWidth Width of the trunk.
     * @param trunkHeight Height of the trunk.
    */
	
   private void drawPineTree(double centerX, double baseY, double totalHeight, double totalWidth,
                                   int sections, double trunkWidth, double trunkHeight) {
       double foliageHeight = totalHeight - trunkHeight;
       double sectionHeight = foliageHeight / sections;

       // Draw foliage sections (triangles)
       for (int i = 0; i < sections; i++) {
           double width = totalWidth * (1 - 0.4 * i / (sections - 1)); // narrower at the top
           double topY = baseY - trunkHeight - foliageHeight + i * sectionHeight;
           double bottomY = topY + sectionHeight;

           Polygon triangle = new Polygon();
           triangle.getPoints().addAll(
               centerX, topY, // Top point
               centerX - width / 2, bottomY, // Bottom left
               centerX + width / 2, bottomY  // Bottom right
           );
           triangle.setFill(Color.FORESTGREEN);
           getChildren().add(triangle);
       }

       // Draw trunk
       Rectangle trunk = new Rectangle(centerX - trunkWidth / 2, baseY - trunkHeight, trunkWidth, trunkHeight);
       trunk.setFill(Color.SADDLEBROWN);
       getChildren().add(trunk);
   }
   
}
