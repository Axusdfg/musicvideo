package com.example.bean;

public class Music {
   private String song;
   private String singer;
   private String songPath;

    public Music() {
    }

    public Music(String name, String singer, String path) {
        this.song = name;
        this.singer = singer;
        this.songPath = path;
    }

    public String getSong() { return song;
    }
    public void setSong(String name) {this.song = name;
    }
    public String getSinger() { return singer;
    }
    public void setSinger(String singer) { this.singer = singer;
    }
    public String getSongPath() { return songPath;
    }
    public void setSongPath(String path) { this.songPath = path;
    }

}
