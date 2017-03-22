package FileWork;

import FileWork.Directory;

import java.io.File;

/**
 * Created by kolinsol on 3/22/17.
 */
class RenameManager {

    private Directory dir = Directory.getDirectoryInstance();

    private String formatFileNameByTemplate(File file) {
        String fileName = file.getName();
        if ((!file.isDirectory()) && fileName.contains(".mp3")) {
            fileName = fileName.replace(".mp3", "");
            fileName = fileName.replace("[www.MP3Fiber.com]", "");
            fileName = fileName.replace("[MP3Fiber.com]", "");
            fileName = fileName.replace("_", " ");
            fileName = fileName.toLowerCase();
            fileName = fileName.replace("produced by", " (p ");
            fileName = fileName.replace("production by", " (p ");
            fileName = fileName.replace(" production ", " (p ");
            fileName = fileName.replace("prod by", " (p ");
            fileName = fileName.replace(" prod ", " (p ");
            fileName = fileName.replace("p by", " (p ");
            fileName = fileName.replace("featuring", " (w ");
            fileName = fileName.replace(" feat ", " (w ");
            fileName = fileName.replace(" ft ", " (w ");
            fileName = fileName.replaceAll("^ +| +$|( )+", "$1");
            if (fileName.contains("(p"))
                fileName = fileName + ")";
            if ((fileName.contains("(w")) && !(fileName.contains("(p")))
                fileName = fileName + ")";
            if ((fileName.contains("(w")) && (fileName.contains("(p")))
                fileName = fileName.replace(" (p", ") (p");
            fileName = fileName + ".mp3";
            }
        return(fileName);
    }

    private void renameFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File childFile: files)
                renameFile(childFile);
        } else if (file.getName().contains(".mp3")) {
            String fileDirName = file.getAbsolutePath();
            fileDirName = fileDirName.substring(0, fileDirName.lastIndexOf('/'));
            if(!file.renameTo(new File(fileDirName+"/"+formatFileNameByTemplate(file))))
                System.out.println("Error renaming file"+file.getAbsolutePath());
        }
    }


    void recursiveRenameFiles() {
        File[] files = dir.getDirFile().listFiles();
        for (File file: files)
            renameFile(file);
    }
}