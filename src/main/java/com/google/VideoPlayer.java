package com.google;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    currentVideo = null;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<String> videos = videoLibrary.getVideos().stream()
            .map(Video::toString)
            .collect(Collectors.toList());
    Collections.sort(videos);

    StringBuilder str = new StringBuilder();

    str.append("Here's a list of all available videos:");
    for (String video : videos) {
      str.append("\n");
      str.append(" ");
      str.append(video);
    }

    System.out.println(str);
  }

  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
      return;
    }
    if (currentVideo != null) {
      System.out.println("Stopping video:" + currentVideo.getTitle());
    }
    currentVideo = video;
    System.out.println("Playing video: " + currentVideo.getTitle());
  }

  public void stopVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
      return;
    }
    System.out.println("Stopping video: " + currentVideo.getTitle());
  }

  public void playRandomVideo() {
    List<Video> videos = videoLibrary.getVideos();
    Random random = new Random();
    Video randomVideo = videos.get(random.nextInt(videos.size()));
    playVideo(randomVideo.getVideoId());
  }

  public void pauseVideo() {
    System.out.println("pauseVideo needs implementation");
  }

  public void continueVideo() {
    System.out.println("continueVideo needs implementation");
  }

  public void showPlaying() {
    System.out.println("showPlaying needs implementation");
  }

  public void createPlaylist(String playlistName) {
    System.out.println("createPlaylist needs implementation");
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println("addVideoToPlaylist needs implementation");
  }

  public void showAllPlaylists() {
    System.out.println("showAllPlaylists needs implementation");
  }

  public void showPlaylist(String playlistName) {
    System.out.println("showPlaylist needs implementation");
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("removeFromPlaylist needs implementation");
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}