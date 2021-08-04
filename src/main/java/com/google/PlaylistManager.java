package com.google;

import java.util.*;
import java.util.stream.Collectors;

public class PlaylistManager {

    private final Map<String, VideoPlaylist> playlists;
    private final VideoLibrary videoLibrary;

    public PlaylistManager(VideoLibrary videoLibrary) {
        this.playlists = new HashMap<>();
        this.videoLibrary = videoLibrary;
    }

    public boolean playlistExists(String name) {
        return playlists.containsKey(name.toLowerCase());
    }

    public VideoPlaylist getPlaylist(String name) {
        return playlists.get(name.toLowerCase());
    }

    public String createPlaylist(String name) {
        if (playlistExists(name)) {
            return "Cannot create playlist: A playlist with the same name already exists";
        }
        playlists.put(name.toLowerCase(), new VideoPlaylist(name));
        return "Successfully created new playlist: " + name;
    }

    public String addToPlayList(String playlistName, String videoId) {
        if (!playlistExists(playlistName)) {
            return "Cannot add video to " + playlistName + ": Playlist does not exist";
        }

        if (videoLibrary.getVideo(videoId) == null) {
            return "Cannot add video to " + playlistName + ": Video does not exist";
        }
        Video video = videoLibrary.getVideo(videoId);
        VideoPlaylist videoPlaylist = getPlaylist(playlistName);

        if (videoPlaylist.containsVideo(videoId)) {
            return "Cannot add video to " + playlistName + ": Video already added";
        }

        videoPlaylist.addVideo(videoId);
        return "Added video to " + playlistName + ": " + video.getTitle();
    }

    public List<String> playListNames() {
        List<String> playlistNames = playlists.values().stream()
                .map(VideoPlaylist::getName)
                .collect(Collectors.toList());
        Collections.sort(playlistNames);
        return playlistNames;
    }

    public void deletePlaylist(String playlistName) {
        playlists.remove(playlistName);
    }
}
