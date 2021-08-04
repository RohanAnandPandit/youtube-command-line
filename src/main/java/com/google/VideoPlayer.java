package com.google;

import java.util.*;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final PlaylistManager playlistManager;
  private Video currentVideo;
  private boolean videoPaused;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlistManager = new PlaylistManager(videoLibrary);
    this.currentVideo = null;
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
      System.out.println("Stopping video: " + currentVideo.getTitle());
    }
    currentVideo = video;
    videoPaused = false;
    System.out.println("Playing video: " + currentVideo.getTitle());
  }

  public void stopVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot stop video: No video is currently playing");
      return;
    }
    System.out.println("Stopping video: " + currentVideo.getTitle());
    currentVideo = null;
  }

  public void playRandomVideo() {
    List<Video> videos = videoLibrary.getVideos();
    if (videos.isEmpty()) {
      System.out.println("No videos available");
      return;
    }
    Random random = new Random();
    Video randomVideo = videos.get(random.nextInt(videos.size()));
    playVideo(randomVideo.getVideoId());
  }

  public void pauseVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot pause video: No video is currently playing");
      return;
    }
    if (videoPaused) {
      System.out.println("Video already paused: " + currentVideo.getTitle());
      return;
    }
    videoPaused = true;
    System.out.println("Pausing video: " + currentVideo.getTitle());
  }

  public void continueVideo() {
    if (currentVideo == null) {
      System.out.println("Cannot continue video: No video is currently playing");
      return;
    }
    if (!videoPaused) {
      System.out.println("Cannot continue video: Video is not paused");
      return;
    }
    videoPaused = false;
    System.out.println("Continuing video: " + currentVideo.getTitle());
  }

  public void showPlaying() {
    if (currentVideo == null) {
      System.out.println("No video is currently playing");
      return;
    }
    StringBuilder str = new StringBuilder();
    str.append("Currently playing: ");
    str.append(currentVideo);
    if (videoPaused) {
      str.append(" - PAUSED");
    }
    System.out.println(str);
  }

  public void createPlaylist(String playlistName) {
    System.out.println(playlistManager.createPlaylist(playlistName));
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    System.out.println(playlistManager.addToPlayList(playlistName, videoId));
  }

  public void showAllPlaylists() {
    List<String> playlistNames = playlistManager.playListNames();
    if (playlistNames.isEmpty()) {
      System.out.println("No playlists exist yet");
      return;
    }
    System.out.println("Showing all playlists:");
    for (String playlistName : playlistNames) {
      System.out.println(" " + playlistName);
    }
  }

  public void showPlaylist(String playlistName) {
    if (!playlistManager.playlistExists(playlistName)) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    System.out.println("Showing playlist: " + playlistName);
    VideoPlaylist videoPlaylist = playlistManager.getPlaylist(playlistName);
    if (videoPlaylist.getVideosIds().isEmpty()) {
      System.out.println(" No videos here yet");
      return;
    }
    List<String> videos = videoPlaylist.getVideosIds().stream()
            .map(videoLibrary::getVideo)
            .map(Video::toString).collect(Collectors.toList());
    videos.forEach(name -> System.out.println(" " + name));
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if (!playlistManager.playlistExists(playlistName)) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
      return;
    }
    if (videoLibrary.getVideo(videoId) == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;
    }
    VideoPlaylist videoPlaylist = playlistManager.getPlaylist(playlistName);
    if (!videoPlaylist.containsVideo(videoId)) {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
      return;
    }
    videoPlaylist.removeVideo(videoId);
    System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
  }

  public void clearPlaylist(String playlistName) {
    if (!playlistManager.playlistExists(playlistName)) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    VideoPlaylist videoPlaylist = playlistManager.getPlaylist(playlistName);
    videoPlaylist.removeAllVideos();
    System.out.println("Successfully removed all videos from " + playlistName);
  }

  public void deletePlaylist(String playlistName) {
    if (!playlistManager.playlistExists(playlistName)) {
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
      return;
    }
    playlistManager.deletePlaylist(playlistName);
    System.out.println("Deleted playlist: " + playlistName);
  }

  public void searchVideos(String searchTerm) {
    List<Video> videos = videoLibrary.getVideos().stream()
            .filter(video -> video.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
            .sorted().collect(Collectors.toList());
    if (videos.isEmpty()) {
      System.out.println("No search results for " + searchTerm);
      return;
    }
    System.out.println("Here are the results for " + searchTerm + ":");
    for (int i = 0; i < videos.size(); i++) {
      System.out.println(" " + (i + 1) + ") " + videos.get(i));
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextInt()) {
      int n = scanner.nextInt();
      if (n >= 1 && n <= videos.size()) {
        playVideo(videos.get(n - 1).getVideoId());
      }
    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> videos = videoLibrary.getVideos().stream()
            .filter(video -> video.getTags().contains(videoTag.toLowerCase()))
            .sorted().collect(Collectors.toList());
    if (videos.isEmpty()) {
      System.out.println("No search results for " + videoTag);
      return;
    }
    System.out.println("Here are the results for " + videoTag + ":");
    for (int i = 0; i < videos.size(); i++) {
      System.out.println(" " + (i + 1) + ") " + videos.get(i));
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextInt()) {
      int n = scanner.nextInt();
      if (n >= 1 && n <= videos.size()) {
        playVideo(videos.get(n - 1).getVideoId());
      }
    }
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