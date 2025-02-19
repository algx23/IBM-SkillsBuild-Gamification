package com.example.skillsbuildgamification.model;

public enum UserRank {
  BRONZE("images/ranks/bronze.png", 0, 10),
  SILVER("images/ranks/silver.jpg", 20, 30),
  GOLD("images/ranks/gold.jpg", 40, 50),
  PLATINUM("images/ranks/platinum.png", 60, 70),
  DIAMOND("images/ranks/diamond.jpg", 80, 90),
  CHAMPION("images/ranks/champion.jpg", 100, 110),
  MEGA_CHAMPION("images/ranks/megachampion.png", 120, 130),
  LEGEND("images/ranks/legend.png", 140, 999);

  private final int minVal;
  private final int maxVal;
  private final String url;

  UserRank(String url, int minVal, int maxVal) {
    this.url = url;
    this.minVal = minVal;
    this.maxVal = maxVal;
  }

  public int getMinValue() {
    return this.minVal;
  }

  public int getMaxValue() {
    return this.maxVal;
  }

  public String getUrl() {
    return this.url;
  }
}
