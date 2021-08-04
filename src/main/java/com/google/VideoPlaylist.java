package com.google;

import java.util.*;

/** A class used to represent a Playlist */
class VideoPlaylist {

    private final String name;
    private final Set<String> videos;


    VideoPlaylist(String name) {
        this.name = name;
        this.videos = new LinkedHashSet<>();
    }

    public boolean containsVideo(String videoId) {
        return videos.contains(videoId);
    }

    public void addVideo(String videoId) {
        videos.add(videoId);
    }

    public void removeVideo(String videoId) {
        videos.remove(videoId);
    }
    public Set<String> getVideosIds() {
        return videos;
    }

    public String getName() {
        return name;
    }

    public void removeAllVideos() {
        videos.clear();
    }
}
