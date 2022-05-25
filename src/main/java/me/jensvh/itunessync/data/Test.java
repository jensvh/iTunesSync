package me.jensvh.itunessync.data;

import java.io.File;

import me.jensvh.itunessync.Utils;

public class Test {

    public static void main(String[] args) {
        String path = "C:\\Users\\ikke\\Music\\bjorn";
        File folder = new File(path);
        
        File[] folders = folder.listFiles(Utils.filterSongs);
        System.out.println(folders.length);
    }
    
}
