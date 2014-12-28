package com.codamasters.LNHelpers;

public interface ActionResolver {
   public void showOrLoadInterstital();
   public void submitScore(String id, int score);
   public void displayLeaderboard(String id);
   public void unlockAchievement(String id);
   public void displayAchievements();
}
