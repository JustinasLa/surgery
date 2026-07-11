package tfmc.justin.managers;

import java.util.*;

// ==============================================
// Manages all player state for ongoing surgeries
// ==============================================
public class SurgeryStateManager {
    
    private final Map<UUID, Set<Integer>> playerClickedSlots = new HashMap<>();
    private final Map<UUID, String> playerDiagnosis = new HashMap<>();
    private final Map<UUID, String> playerPulse = new HashMap<>();
    private final Map<UUID, String> playerStatus = new HashMap<>();
    private final Map<UUID, Double> playerTemperature = new HashMap<>();
    private final Map<UUID, String> playerOperationSite = new HashMap<>();
    private final Map<UUID, Integer> playerIncisions = new HashMap<>();
    private final Map<UUID, String> playerSkillFail = new HashMap<>();
    private final Map<UUID, Boolean> playerBleeding = new HashMap<>();
    private final Map<UUID, Integer> playerBrokenBones = new HashMap<>();
    private final Map<UUID, Integer> playerShatteredBones = new HashMap<>();
    private final Map<UUID, Integer> playerRevealedBrokenBones = new HashMap<>();
    private final Map<UUID, Integer> playerRevealedShatteredBones = new HashMap<>();
    private final Map<UUID, Integer> playerDefibrillatorCountdown = new HashMap<>();
    private final Map<UUID, Boolean> playerCured = new HashMap<>();
    private final Map<UUID, Integer> playerAntibioticsCounter = new HashMap<>();
    private final Map<UUID, Boolean> playerAntisepticProtection = new HashMap<>();
    private final Map<UUID, Boolean> playerSpongeEffect = new HashMap<>();
    private final Map<UUID, Integer> playerMoveCount = new HashMap<>();
    private final Map<UUID, Integer> playerMovesSinceLastSponge = new HashMap<>();
    private final Map<UUID, Boolean> playerWoundsExamined = new HashMap<>();
    private final Map<UUID, Integer> playerUnconsciousTimer = new HashMap<>();
    private final Map<UUID, Boolean> playerHasRisingTemp = new HashMap<>();
    private final Map<UUID, Integer> playerExtremelyWeakCounter = new HashMap<>();
    private final Map<UUID, Integer> playerRedTempCounter = new HashMap<>();
    private final Map<UUID, String> playerPatientName = new HashMap<>();
    
    // ==============================================
    // Getters with default values
    // ==============================================
    public Set<Integer> getClickedSlots(UUID playerId) { return playerClickedSlots.getOrDefault(playerId, new HashSet<>()); }
    public String getDiagnosis(UUID playerId) { return playerDiagnosis.get(playerId); }
    public String getPulse(UUID playerId) { return playerPulse.getOrDefault(playerId, "Strong"); }
    public String getStatus(UUID playerId) { return playerStatus.getOrDefault(playerId, "Awake"); }
    public double getTemperature(UUID playerId) { return playerTemperature.getOrDefault(playerId, 98.6); }
    public String getOperationSite(UUID playerId) { return playerOperationSite.getOrDefault(playerId, "Not sanitized"); }
    public int getIncisions(UUID playerId) { return playerIncisions.getOrDefault(playerId, 0); }
    public String getSkillFail(UUID playerId) { return playerSkillFail.getOrDefault(playerId, ""); }
    public boolean isBleeding(UUID playerId) { return playerBleeding.getOrDefault(playerId, false); }
    public int getBrokenBones(UUID playerId) { return playerBrokenBones.getOrDefault(playerId, 0); }
    public int getShatteredBones(UUID playerId) { return playerShatteredBones.getOrDefault(playerId, 0); }
    public int getRevealedBrokenBones(UUID playerId) { return playerRevealedBrokenBones.getOrDefault(playerId, 0); }
    public int getRevealedShatteredBones(UUID playerId) { return playerRevealedShatteredBones.getOrDefault(playerId, 0); }
    public Integer getDefibrillatorCountdown(UUID playerId) { return playerDefibrillatorCountdown.get(playerId); }
    public boolean isCured(UUID playerId) { return playerCured.getOrDefault(playerId, false); }
    public Integer getAntibioticsCounter(UUID playerId) { return playerAntibioticsCounter.get(playerId); }
    public boolean hasAntisepticProtection(UUID playerId) { return playerAntisepticProtection.getOrDefault(playerId, false); }
    public boolean hasSpongeEffect(UUID playerId) { return playerSpongeEffect.getOrDefault(playerId, false); }
    public int getMoveCount(UUID playerId) { return playerMoveCount.getOrDefault(playerId, 0); }
    public int getMovesSinceLastSponge(UUID playerId) { return playerMovesSinceLastSponge.getOrDefault(playerId, 0); }
    public boolean hasWoundsExamined(UUID playerId) { return playerWoundsExamined.getOrDefault(playerId, false); }
    public Integer getUnconsciousTimer(UUID playerId) { return playerUnconsciousTimer.get(playerId); }
    public boolean hasRisingTemp(UUID playerId) { return playerHasRisingTemp.getOrDefault(playerId, false); }
    public int getExtremelyWeakCounter(UUID playerId) { return playerExtremelyWeakCounter.getOrDefault(playerId, 0); }
    public int getRedTempCounter(UUID playerId) { return playerRedTempCounter.getOrDefault(playerId, 0); }
    public String getPatientName(UUID playerId) { return playerPatientName.getOrDefault(playerId, "Unknown"); }
    public boolean hasDiagnosis(UUID playerId) { return playerDiagnosis.containsKey(playerId); }
    public boolean hasPulse(UUID playerId) { return playerPulse.containsKey(playerId); }
    
    // ==============================================
    // Setters
    // ==============================================
    public void addClickedSlot(UUID playerId, int slot) { playerClickedSlots.computeIfAbsent(playerId, k -> new HashSet<>()).add(slot); }
    public void setDiagnosis(UUID playerId, String diagnosis) { playerDiagnosis.put(playerId, diagnosis); }
    public void setPulse(UUID playerId, String pulse) { playerPulse.put(playerId, pulse); }
    public void setStatus(UUID playerId, String status) { playerStatus.put(playerId, status); }
    public void setTemperature(UUID playerId, double temp) { playerTemperature.put(playerId, temp); }
    public void setOperationSite(UUID playerId, String site) { playerOperationSite.put(playerId, site); }
    public void setIncisions(UUID playerId, int incisions) { playerIncisions.put(playerId, incisions); }
    public void setSkillFail(UUID playerId, String message) { playerSkillFail.put(playerId, message); }
    public void setBleeding(UUID playerId, boolean bleeding) { playerBleeding.put(playerId, bleeding); }
    public void setBrokenBones(UUID playerId, int count) { playerBrokenBones.put(playerId, count); }
    public void setShatteredBones(UUID playerId, int count) { playerShatteredBones.put(playerId, count); }
    public void setRevealedBrokenBones(UUID playerId, int count) { playerRevealedBrokenBones.put(playerId, count); }
    public void setRevealedShatteredBones(UUID playerId, int count) { playerRevealedShatteredBones.put(playerId, count); }
    public void setDefibrillatorCountdown(UUID playerId, int countdown) { playerDefibrillatorCountdown.put(playerId, countdown); }
    public void setCured(UUID playerId, boolean cured) { playerCured.put(playerId, cured); }
    public void setAntibioticsCounter(UUID playerId, int count) { playerAntibioticsCounter.put(playerId, count); }
    public void setAntisepticProtection(UUID playerId, boolean protected_) { playerAntisepticProtection.put(playerId, protected_); }
    public void setSpongeEffect(UUID playerId, boolean effect) { playerSpongeEffect.put(playerId, effect); }
    public void setMoveCount(UUID playerId, int count) { playerMoveCount.put(playerId, count); }
    public void setMovesSinceLastSponge(UUID playerId, int count) { playerMovesSinceLastSponge.put(playerId, count); }
    public void setWoundsExamined(UUID playerId, boolean examined) { playerWoundsExamined.put(playerId, examined); }
    public void setUnconsciousTimer(UUID playerId, int timer) { playerUnconsciousTimer.put(playerId, timer); }
    public void setHasRisingTemp(UUID playerId, boolean risingTemp) { playerHasRisingTemp.put(playerId, risingTemp); }
    public void setExtremelyWeakCounter(UUID playerId, int count) { playerExtremelyWeakCounter.put(playerId, count); }
    public void setRedTempCounter(UUID playerId, int count) { playerRedTempCounter.put(playerId, count); }
    public void setPatientName(UUID playerId, String name) { playerPatientName.put(playerId, name); }
    public void removeDefibrillatorCountdown(UUID playerId) { playerDefibrillatorCountdown.remove(playerId); }
    public void removeAntibioticsCounter(UUID playerId) { playerAntibioticsCounter.remove(playerId); }
    public void removeUnconsciousTimer(UUID playerId) { playerUnconsciousTimer.remove(playerId); }
    
    // ==============================================
    // Player Data Cleanup
    // ==============================================
    public void cleanup(UUID playerId) {
        playerPatientName.remove(playerId);
        playerClickedSlots.remove(playerId);
        playerDiagnosis.remove(playerId);
        playerPulse.remove(playerId);
        playerStatus.remove(playerId);
        playerTemperature.remove(playerId);
        playerOperationSite.remove(playerId);
        playerIncisions.remove(playerId);
        playerSkillFail.remove(playerId);
        playerBleeding.remove(playerId);
        playerBrokenBones.remove(playerId);
        playerShatteredBones.remove(playerId);
        playerRevealedBrokenBones.remove(playerId);
        playerRevealedShatteredBones.remove(playerId);
        playerDefibrillatorCountdown.remove(playerId);
        playerCured.remove(playerId);
        playerAntibioticsCounter.remove(playerId);
        playerAntisepticProtection.remove(playerId);
        playerSpongeEffect.remove(playerId);
        playerMoveCount.remove(playerId);
        playerMovesSinceLastSponge.remove(playerId);
        playerWoundsExamined.remove(playerId);
        playerUnconsciousTimer.remove(playerId);
        playerHasRisingTemp.remove(playerId);
        playerExtremelyWeakCounter.remove(playerId);
        playerRedTempCounter.remove(playerId);
    }
}
