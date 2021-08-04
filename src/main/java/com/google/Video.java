package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video implements Comparable<Video> {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged;
  private String flagReason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.flagged = false;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(title);
    str.append(" (");
    str.append(videoId);
    str.append(") [");
    boolean first = true;
    for (String tag : tags) {
      if (!first) {
        str.append(" ");
      }
      first = false;
      str.append(tag);
    }
    str.append("]");
    if (isFlagged()) {
      str.append(" - FLAGGED (reason: ");
      str.append(getFlagReason());
      str.append(")");
    }
    return str.toString();
  }

  @Override
  public int compareTo(Video o) {
    return getTitle().compareTo(o.getTitle());
  }

  public boolean isFlagged() {
    return flagged;
  }

  public void flag(String flagReason) {
    flagged = true;
    this.flagReason = flagReason;
  }

  public String getFlagReason() {
    return flagReason;
  }

  public void removeFlag() {
    flagged = false;
    flagReason = null;
  }
}
