package tfmc.justin.managers;

import org.bukkit.Material;

// ==============================================
// Constants for the surgery system
// ==============================================
public class SurgeryConstants {
    
    // List of possible patient statuses (with corresponding colors)
    public static final String[] PATIENT_STATUSES = {
        "Awake",         // YELLOW_CONCRETE
        "Unconscious",   // LIME_CONCRETE
        "Heart Stopped", // RED_CONCRETE
        "Coming to"      // ORANGE_CONCRETE
    };
    
    // List of possible operation site statuses (with corresponding colors)
    public static final String[] OPERATION_SITE_STATUSES = {
        "Clean",         // LIME_CONCRETE
        "Not sanitized", // YELLOW_CONCRETE
        "Unclean",       // ORANGE_CONCRETE
        "Unsanitary"     // RED_CONCRETE
    };
    
    // List of possible pulse statuses (with corresponding colors)
    public static final String[] PULSE_STATUSES = {
        "Strong",        // LIME_CONCRETE
        "Steady",        // YELLOW_CONCRETE
        "Weak",          // ORANGE_CONCRETE
        "Extremely Weak" // RED_CONCRETE
    };
    
    // ==============================================
    // Gets the material color for pulse status
    // ==============================================
    public static Material getPulseColor(String pulseStatus) {
        return switch (pulseStatus) {
            case "Strong" -> Material.LIME_CONCRETE;
            case "Steady" -> Material.YELLOW_CONCRETE;
            case "Weak" -> Material.ORANGE_CONCRETE;
            case "Extremely Weak" -> Material.RED_CONCRETE;
            default -> Material.GRAY_CONCRETE;
        };
    }
    
    // ==============================================
    // Gets the material color for patient status
    // ==============================================
    public static Material getStatusColor(String status) {
        return switch (status) {
            case "Awake" -> Material.YELLOW_CONCRETE;
            case "Unconscious" -> Material.LIME_CONCRETE;
            case "Heart Stopped" -> Material.RED_CONCRETE;
            case "Coming to" -> Material.ORANGE_CONCRETE;
            default -> Material.GRAY_CONCRETE;
        };
    }
    
    // ==============================================
    // Gets the material color for temperature (Fahrenheit)
    // ==============================================
    public static Material getTemperatureColor(double tempF) {
        if (tempF <= 100) {
            return Material.LIME_CONCRETE;
        } else if (tempF <= 104) {
            return Material.YELLOW_CONCRETE;
        } else if (tempF <= 106) {
            return Material.ORANGE_CONCRETE;
        } else {
            return Material.RED_CONCRETE;
        }
    }
    
    // ==============================================
    // Gets the material color for operation site status
    // ==============================================
    public static Material getOperationSiteColor(String status) {
        return switch (status) {
            case "Clean" -> Material.LIME_CONCRETE;
            case "Not sanitized" -> Material.YELLOW_CONCRETE;
            case "Unclean" -> Material.ORANGE_CONCRETE;
            case "Unsanitary" -> Material.RED_CONCRETE;
            default -> Material.GRAY_CONCRETE;
        };
    }
    
    // ==============================================
    // Gets the material color for incision count
    // ==============================================
    public static Material getIncisionColor(int count) {
        return count == 0 ? Material.LIME_CONCRETE : Material.YELLOW_CONCRETE;
    }
    
    // ==============================================
    // Formats temperature for display (Fahrenheit and Celsius)
    // ==============================================
    public static String formatTemperature(double tempF) {
        double tempC = (tempF - 32) * 5 / 9;
        return String.format("%.1f\u00b0F / %.1f\u00b0C", tempF, tempC);
    }
    
    // ==============================================
    // Improves pulse to next better status
    // ==============================================
    public static String improvePulse(String currentPulse) {
        return switch (currentPulse) {
            case "Extremely Weak" -> "Weak";
            case "Weak" -> "Steady";
            case "Steady" -> "Strong";
            default -> "Strong";
        };
    }
    
    // ==============================================
    // Worsens pulse to next worse status
    // ==============================================
    public static String worsenPulse(String currentPulse) {
        return switch (currentPulse) {
            case "Strong" -> "Steady";
            case "Steady" -> "Weak";
            case "Weak" -> "Extremely Weak";
            default -> "Extremely Weak";
        };
    }
}
