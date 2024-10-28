package edu.augustana;

import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.net.URI;

public class KnobControlSkin extends SkinBase<KnobControl> {
    private ImageView knobImageView;
    private Rotate rotate;
    private double lastAngle;
    private boolean dragging;



    public void rotateToPosition(int newPos) {
//        int angle = 0;
//        long tempTime = System.currentTimeMillis();
//        while(angle < (newPos/100)*225){
//            if((System.currentTimeMillis() - tempTime)> 16){
//                tempTime = System.currentTimeMillis();
//                rotate.setAngle((rotate.getAngle() + 1));
//                angle++;
//            }
//
//        }
    }





    public KnobControlSkin(KnobControl control) {
        super(control);


        // Load the knob image
        Image knobImage = new Image(getClass().getResourceAsStream("/assets/knob.png"));
        knobImageView = new ImageView(knobImage);

        // Adjust size and scaling if needed
        knobImageView.setFitWidth(50);
        knobImageView.setFitHeight(50);

        // Set up rotation transform
        rotate = new Rotate(0, knobImageView.getFitWidth() / 2, knobImageView.getFitHeight() / 2);
        knobImageView.getTransforms().add(rotate);

        // Initialize rotation transformation
        rotate = new Rotate(0, knobImageView.getFitWidth() / 2, knobImageView.getFitHeight() / 2);
        knobImageView.getTransforms().add(rotate);

        double centerX = knobImageView.getFitWidth() / 2;
        double centerY = knobImageView.getFitHeight() / 2;
        double maxRotationAngle = 225; // Set maximum rotation limit
        double minRotationAngle = 0;   // Set minimum rotation limit

        knobImageView.setOnMousePressed(event -> {
            dragging = true;
            lastAngle = Math.toDegrees(Math.atan2(event.getY() - centerY, event.getX() - centerX));
        });

        knobImageView.setOnMouseDragged(event -> {
            if (dragging) {


                double currentAngle = Math.toDegrees(Math.atan2(event.getY() - centerY, event.getX() - centerX));
                System.out.println("Current Angle = " + currentAngle);
                double deltaAngle = currentAngle - lastAngle;
                rotate.setAngle((rotate.getAngle() + deltaAngle + 360) % 360); // Normalize to 0-360
                lastAngle = currentAngle;

                // Optionally update the control's value property based on the rotation
                double normalizedValue = (rotate.getAngle() / 360) * 100; // Example normalization

                getSkinnable().setValue(normalizedValue);



            }
        });



        knobImageView.setOnMouseReleased(event -> {
            dragging = false;
        });

        knobImageView.setOnMouseExited(event -> {
            dragging = false;
        });

        // Handle mouse wheel rotation with clamping
        knobImageView.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double angleChange = deltaY > 0 ? 10 : -10;

            // Calculate the new angle with clamping
            double newAngle = rotate.getAngle() + angleChange;
            newAngle = Math.max(minRotationAngle, Math.min(newAngle, maxRotationAngle)); // Clamp between 0 and 225

            rotate.setAngle(newAngle);

            // Normalize to max angle of 225 degrees for 100% value
            double normalizedValue = (rotate.getAngle() / maxRotationAngle) * 100;

            getSkinnable().setValue(normalizedValue);


            event.consume();
        });

        getChildren().add(knobImageView); // Add knobImageView to the skin's children
    }


}