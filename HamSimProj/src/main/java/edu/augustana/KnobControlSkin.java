package edu.augustana;

import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class KnobControlSkin extends SkinBase<KnobControl> {
    private ImageView knobImageView;
    private Rotate rotate;

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

        // Add interaction (rotation)
        addRotationBehavior();

        // Add the knob image to the skin
        getChildren().add(knobImageView);
    }

    private double lastAngle = 0; // To store the last angle
    private boolean dragging = false; // To indicate if we are dragging

    private void addRotationBehavior() {
        double centerX = knobImageView.getFitWidth() / 2;
        double centerY = knobImageView.getFitHeight() / 2;

        knobImageView.setOnMousePressed(event -> {
            dragging = true;
            lastAngle = Math.toDegrees(Math.atan2(event.getY() - centerY, event.getX() - centerX));
        });

        knobImageView.setOnMouseDragged(event -> {
            if (dragging) {
                double currentAngle = Math.toDegrees(Math.atan2(event.getY() - centerY, event.getX() - centerX));
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

        // Add mouse wheel event handling
        knobImageView.setOnScroll(event -> {
            // Determine the amount of scroll
            double deltaY = event.getDeltaY();

            // Rotate the knob based on the scroll amount
            double angleChange = deltaY > 0 ? 10 : -10; // Adjust the rotation speed (10 degrees per scroll)

            // Update the rotation
            rotate.setAngle((rotate.getAngle() + angleChange + 360) % 360); // Normalize to 0-360

            // Update the control's value property based on the rotation
            double normalizedValue = (rotate.getAngle() / 360) * 100; // Example normalization
            getSkinnable().setValue(normalizedValue);

            // Consume the event to prevent further handling
            event.consume();
        });
    }
}