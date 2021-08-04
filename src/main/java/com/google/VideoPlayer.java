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
        List<Video> videos = videoLibrary.getVideos();
        Collections.sort(videos);

        StringBuilder str = new StringBuilder();

        str.append("Here's a list of all available videos:");
        for (Video video : videos) {
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
        if (video.isFlagged()) {
            System.out.println("Cannot play video: Video is currently flagged (reason: " + video.getFlagReason() + ")");
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
        List<Video> videos = videoLibrary.getVideos().stream()
                .filter(video -> !video.isFlagged())
                .collect(Collectors.toList());
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
        if (!playlistManager.playlistExists(playlistName)) {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }

        if (videoLibrary.getVideo(videoId) == null) {
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
            return;
        }
        Video video = videoLibrary.getVideo(videoId);
        if (video.isFlagged()) {
            System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: "
                    + video.getFlagReason() + ")");
            return;
        }
        VideoPlaylist videoPlaylist = playlistManager.getPlaylist(playlistName);

        if (videoPlaylist.containsVideo(videoId)) {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
        }

        playlistManager.addToPlayList(playlistName, videoId);
        System.out.println("Added video to " + playlistName + ": " + video.getTitle());
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
        List<Video> videos = videoPlaylist.getVideosIds().stream()
                .map(videoLibrary::getVideo)
                .collect(Collectors.toList());
        String str;
        for (Video video : videos) {
            System.out.println(video);
        }
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
                .filter(video -> !video.isFlagged())
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
                .filter(video -> !video.isFlagged())
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
        flagVideo(videoId, "Not supplied");
    }

    public void flagVideo(String videoId, String reason) {
        Video video = videoLibrary.getVideo(videoId);
        if (video == null) {
            System.out.println("Cannot flag video: Video does not exist");
            return;
        }
        if (video.isFlagged()) {
            System.out.println("Cannot flag video: Video is already flagged");
            return;
        }
        video.flag(reason);
        if (currentVideo == video) {
            stopVideo();
        }
        System.out.println("Successfully flagged video: " + video.getTitle()
                + " (reason: " + video.getFlagReason() + ")");
    }

    public void allowVideo(String videoId) {
        Video video = videoLibrary.getVideo(videoId);
        if (video == null) {
            System.out.println("Cannot remove flag from video: Video does not exist");
            return;
        }
        if (!video.isFlagged()) {
            System.out.println("Cannot remove flag from video: Video is not flagged");
            return;
        }
        video.removeFlag();

        System.out.println("Successfully removed flag from video: " + video.getTitle()
                + " (reason: " + video.getFlagReason() + ")");
    }
}