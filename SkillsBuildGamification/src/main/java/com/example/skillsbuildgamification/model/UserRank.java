package com.example.skillsbuildgamification.model;

public enum UserRank {
  BRONZE("images/ranks/bronze.png", 0, 1),
  SILVER("images/ranks/silver.png", 2, 3),
  GOLD("images/ranks/gold.jpg", 4, 5),
  PLATINUM("images/ranks/platinum.png", 6, 7),
  DIAMOND("images/ranks/diamond.jpg", 8, 9),
  CHAMPION("images/ranks/champion.png", 10, 11),
  MEGA_CHAMPION("images/ranks/megachampion.png", 12, 13),
  LEGEND("images/ranks/legend.png", 14, 15);

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
