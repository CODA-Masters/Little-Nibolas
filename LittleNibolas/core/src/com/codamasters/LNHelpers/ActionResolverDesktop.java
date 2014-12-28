package com.codamasters.LNHelpers;
 
public class ActionResolverDesktop implements ActionResolver {
  @Override
  public void showOrLoadInterstital() {
    System.out.println("showOrLoadInterstital()");
  }

@Override
public void submitScore(String id, int score) {
	System.out.println("submitScore()");
	
}

@Override
public void displayLeaderboard(String id) {
	System.out.println("displayLeaderboard()");
	
}

@Override
public void unlockAchievement(String id) {
	System.out.println("unlockAchievement()");
	
}

@Override
public void displayAchievements() {
	System.out.println("displayAchievement()");
	
}

}