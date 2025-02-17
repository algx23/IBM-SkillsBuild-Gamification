package com.example.skillsbuildgamification.model;

public enum UserRank {
  // TODO: ADD IMAGES TO RANKS AND DISPLAY ON JSP
  BRONZE(
      "\\static\\images\\bronze.png",
      0, 1),
  SILVER("", 2, 3),
  GOLD("", 4, 5),
  PLATINUM("", 6, 7),
  DIAMOND("", 8, 9),
  CHAMPION("", 10, 11),
  GRAND_CHAMPION("", 12, 13),
  SUPERSONIC_LEGEND("", 14, 15);

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
