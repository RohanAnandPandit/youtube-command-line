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

    public void addToPlayList(String playlistName, String videoId) {
        VideoPlaylist videoPlaylist = getPlaylist(playlistName);
        videoPlaylist.addVideo(videoId);
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
